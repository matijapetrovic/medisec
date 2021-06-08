from datetime import datetime
import requests
import random

API_PORT = 8481
API_HOST = "localhost"
BASE_URL = "http://{0}:{1}/api".format(API_HOST, API_PORT)

surgeries = ["Cataract", "Low back", "Heart", "Spine", "Tonsil", "Jaw", "Arm muslce"]

class MedicalRecordGenerator:
    def __init__(self):
        self.age = None
        self.patient_id = None
        self.time = None
        self.heart_beat = None
        self.average_heard_beat = None
        self.blood_pressure = None
        self.average_blood_pressure = None
        self.body_temperature = None
        self.vaccinated = None
        self.surgery = None
        self.data = {}

    def generate_blood_pressure(self):
        systolic = random.randint(110, 145)
        diastolic = random.randint(65, 95)
        return str(systolic) + "/" + str(diastolic)

    def set_attributes(self):
        self.age = random.randint(25, 90)
        self.time = get_current_timestamp()
        self.heart_beat = random.randint(75, 110)
        self.average_heard_beat = random.randint(75, 110)
        self.blood_pressure = self.generate_blood_pressure()
        self.average_blood_pressure = self.generate_blood_pressure()
        self.body_temperature = random.uniform(36, 38)
        self.vaccinated = bool(random.getrandbits(1))
        self.surgery = random.choice(surgeries)

    def create_data(self, patient_id):
        self.data["age"] = self.age
        self.data["patientId"] = patient_id
        self.data["time"] = self.time
        self.data["heartBeat"] = self.heart_beat
        self.data["averageHeardBeat"] = self.average_heard_beat
        self.data["bloodPressure"] = self.blood_pressure
        self.data["averageBloodPressure"] = self.average_blood_pressure
        self.data["bodyTemperature"] = self.body_temperature
        self.data["vaccinated"] = self.vaccinated
        self.data["surgery"] = self.surgery

    def get_data(self, patient_id):
        self.set_attributes()
        self.create_data(patient_id)
        return self.data

def get_current_timestamp():
    now = datetime.now()
    return now.strftime("%d/%m/%Y %H:%M:%S")

def send_reqeust(resource, data):
    headers = {'Content-type': 'application/json'}
    path = BASE_URL + "/" + resource

    try:
        #requests.post('https://localhost:8081/api/receive', data=message, cert=(API_CRT, API_KEY))
        requests.post(path, json=data, headers=headers)
    except Exception as ex:
            print(ex)

def run():
    medical_record = MedicalRecordGenerator()
    #for patient_id in range(10):
    send_reqeust("medical-record", medical_record.get_data(1))

if __name__ == '__main__':
    run()
