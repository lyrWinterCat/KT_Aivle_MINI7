
import os
import requests
import xml.etree.ElementTree as ET
import numpy as np
import pandas as pd
# import matplotlib.pyplot as plt
# import seaborn as sns
import openai
import json
import torch
from openai import OpenAI
from transformers import AutoTokenizer, AutoModelForSequenceClassification, Trainer, TrainingArguments, EarlyStoppingCallback
# from datasets import load_dataset, Dataset
# from sklearn.model_selection import train_test_split
# from sklearn.metrics import *
from haversine import haversine

class Project0606:
    def __init__(self, conf):
        self.conf = conf

        # openai api_key Load 작업
        openai.api_key = self.conf['api']
        os.environ['OPENAI_API_KEY'] = openai.api_key

        # audio_to_text에서 사용할 client 생성
        self.client_stt = OpenAI()
        self.client_summary = OpenAI()

        # model, tokenizer 불러오기
        self.device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
        self.model = AutoModelForSequenceClassification.from_pretrained(self.conf['model_directory']).to(self.device)
        self.tokenizer = AutoTokenizer.from_pretrained(self.conf['model_directory'])

        # duty_list 불러오기
        self.duty_df = pd.read_csv(self.conf['duty_path'])
        
        # summary_system_role 읽기
        with open(self.conf['summary_system_role_path'], 'r', encoding='utf-8') as file:
            content = file.read()
            self.conf['summary_system_role'] = content

        with open(self.conf['summary_emergency_path'], 'r', encoding='utf-8') as file:
            content = file.read()
            self.conf['summary_emergency'] = content
            
        with open(self.conf['summary_not_emergency_path'], 'r', encoding='utf-8') as file:
            content = file.read()
            self.conf['summary_not_emergency'] = content

    def audio_to_text(self, path):
        # 오디오 파일을 읽어서, 위스퍼를 사용한 변환
        with open(path, 'rb') as audio_file:
            transcript = self.client_stt.audio.transcriptions.create(
                file=audio_file,
                model='whisper-1',
                language='ko',
                response_format='text'
            )

        # 결과 반환
        return transcript

    # type에는 다음과 같이 넣으면 된다.
    # summary_system_role, summary_emergency, summary_not_emergency
    def text_summary(self, input_txt, type='summary_system_role'):
        # 입력데이터를 GPT-3.5-turbo에 전달하고 답변 받아오기
        response = self.client_summary.chat.completions.create(
            model="gpt-3.5-turbo",
            messages=[
                {
                    "role": "system",
                    "content": self.conf[type]
                },
                {
                    "role": "user",
                    "content": input_txt
                }
            ]
        )

        # 응답 받기
        answer = response.choices[0].message.content

        # 응답형식을 정리하고 return
        return answer

    def predict(self, text):
        # 입력 문장 토크나이징
        inputs = self.tokenizer(text, return_tensors="pt", truncation=True, padding=True)
        inputs = {key: value.to(self.device) for key, value in inputs.items()}  # 각 텐서를 GPU로 이동

        # 모델 예측
        with torch.no_grad():
            outputs = self.model(**inputs)

        # 로짓을 소프트맥스로 변환하여 확률 계산
        logits = outputs.logits
        probabilities = logits.softmax(dim=1)

        # 가장 높은 확률을 가진 클래스 선택
        pred = torch.argmax(probabilities, dim=-1).item()

        return pred, probabilities

    def calc_haversine(self, current_xy):
        dist_series = self.duty_df[['wgs84lat', 'wgs84lon']].apply(
            lambda x: haversine(current_xy, (x['wgs84lat'], x['wgs84lon']), unit='km'), axis=1)
        return self.duty_df.loc[dist_series.sort_values()[:self.conf['haversine_count']].index]

    def get_naver_map_dist(self, current_xy, dest_lat, dest_lng):
        url = "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving"
        headers = {
            "X-NCP-APIGW-API-KEY-ID": self.conf['naver_client_id'],
            "X-NCP-APIGW-API-KEY": self.conf['naver_client_secret'],
        }
        params = {
            "start": f"{current_xy[1]},{current_xy[0]}",  # 출발지 (경도, 위도)
            "goal": f"{dest_lng},{dest_lat}",  # 목적지 (경도, 위도)
            "option": "trafast"  # 실시간 빠른 길 옵션
        }

        # 요청하고, 답변 받아오기
        response = requests.get(url, headers=headers, params=params)
        if response.status_code != 200:
            return False

        response = response.json()
        # 현재위치와 동일 (즉 병원이 수m 이내)
        dist = 0
        if response['code'] == 0:
            dist = response['route']['trafast'][0]['summary']['distance']  # m(미터)
        return dist
    
    def recommend_hospital(self, current_xy):
        result_dist = self.calc_haversine(current_xy)
        result_dist['road_distance'] = result_dist.apply(
            lambda x: self.get_naver_map_dist(current_xy, x['wgs84lat'], x['wgs84lon']),
            axis=1
        )
        return result_dist.sort_values(by='road_distance')[:self.conf['naver_count']]

    def proc_pipeline(self, file_paths, current_xy_list, progress=True):
        if isinstance(file_paths, list) is False:
            print('filenames should be a list of file name.')
            return False

        result = []
        result_duty = {}
        for idx, path in enumerate(file_paths):
            if progress is True:
                print(f"({idx + 1}/{len(file_paths)}) {path}")
            file_name = os.path.basename(path)
            result_stt = self.audio_to_text(path)
            result_summary = self.text_summary(result_stt)
            result_predict = self.predict(result_summary)
            result.append({
                'idx': idx,
                'file_name': file_name,
                'transcript': result_stt,
                'summary': result_summary,
                'predict': result_predict
            })

            result_duty[idx] = self.recommend_hospital(current_xy_list[idx])

        return pd.DataFrame(result), result_duty
    
    def proc_pipeline2(self, request_txt, current_xy):
        result_summary = self.text_summary(request_txt)
        result_predict = self.predict(result_summary)
        result_predict = list(result_predict)
        result_predict[1] = result_predict[1].numpy().tolist()[0]
        print(result_predict)
        result_duty = self.recommend_hospital(current_xy)
        result = {
            'transcript': request_txt,
            'summary': result_summary,
            'predict': result_predict,
            'duty': result_duty.to_dict(orient="records")
        }
        
        return result


