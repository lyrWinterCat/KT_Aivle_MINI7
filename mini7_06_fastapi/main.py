from typing import Union

from fastapi import FastAPI
from pydantic import BaseModel

import emergency

# uvicorn main:app --reload

class EmergencyRequest(BaseModel):
    request: str
    latitude: float
    longitude: float
    hospital_cnt: int | None = 3


# Class Project6 생성
conf = {
    'api_file_path': './api_key.txt',
    'model_directory': './fine_tuned_bert',
    'duty_path': './dutylist.csv',
    'naver_client_id': '',
    'naver_client_secret': '',
    'haversine_count': 5,
    'naver_count': 3,
}
conf['summary_system_role'] = '''
당신은 응급상황 통화 녹음 내역에서 핵심을 요약하는 어시스턴트입니다.
    또한 응급상황에 대한 지식이 해박한 베테랑 응급구조사입니다.

    다음의 요구사항을 반드시 지켜주세요.
    1. 녹음 내역의 핵심을 100자 이내로 요약하세요.
    2. 요약 내용의 어휘는 초등학생이 사용할 법한 어휘를 사용해서 요약하세요.
    3. 키워드로 증상의 핵심만 알려주세요.
    4. 요약시 증상이 얼마나 심각한지 상태도 함께 요약하세요. (예: 경미한 상처, 심각한 복통 등)
    5. 요약시 환자가 설명하지 않은 내용은 추가하지 마십시오.
    6. 응답은 다음의 형식처럼 작성하세요.

    {"summary": \"텍스트 요약\"}

    제대로 요약하지 못하면 무고한 시민이 죽습니다.
    결과가 유용하면 1000 달러 팁을 드리겠습니다.
'''
project6 = emergency.Project0606(conf)

app = FastAPI()

@app.get("/")
def read_root():
    return {"Hello": "World"}

@app.post("/hospital_by_module")
def hospital_by_module(req: EmergencyRequest):
    conf['naver_count'] = req.hospital_cnt
    conf['haversine_count'] = req.hospital_cnt * 2
    result = project6.proc_pipeline2(req.request, (req.latitude, req.longitude))
    return result

@app.get("/hospital_by_module")
def get_hospital_by_module(request: str, latitude: float, longitude: float, hospital_cnt: int = 3):
    conf['naver_count'] = hospital_cnt
    conf['haversine_count'] = hospital_cnt * 2
    result = project6.proc_pipeline2(request, (latitude, longitude))
    response = []
    for data in result['duty']:
        dutyTel = data['dutyTel'].split(',')
        response.append({
            'hospitalName': data['duty_name'],
            'address': data['duty_addr'],
            'emergencyMedicalInstitutionType': data['dutyEmclsName'],
            'phoneNumber1': dutyTel[0] if len(dutyTel) > 0 else '',
            'phoneNumber3': dutyTel[1] if len(dutyTel) > 1 else '',
            'latitude': data['wgs84lat'],
            'longitude': data['wgs84lon'],
            'distance': data['road_distance'] / 1000.0
        })
    
    return response

# duty_name,duty_addr,wgs84lon,wgs84lat,dutyTel,dutyEmclsName