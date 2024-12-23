import json
from typing import Union

from fastapi import FastAPI
from pydantic import BaseModel

import emergency

# uvicorn main:app --reload

class EmergencyRequest(BaseModel):
    text: str
    lat: float
    lon: float
    navercount: int | None = 3

# Class Project6 생성
conf = {}
file_path = 'conf.json'
with open(file_path, 'r', encoding='utf-8') as file:
    content = file.read()
    conf = json.loads(content)

project6 = emergency.Project0606(conf)

app = FastAPI()

@app.get("/")
def read_root():
    return {"Hello": "World"}

@app.post("/recommandHospital")
def hospital_by_module(req: EmergencyRequest):
    conf['naver_count'] = req.navercount
    conf['haversine_count'] = req.navercount * 2
    result = project6.proc_pipeline2(req.text, (req.lat, req.lon))
    
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