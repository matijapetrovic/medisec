from datetime import datetime
import requests

API_PORT = 8081
API_HOST = "localhost"
BASE_URL = "http://{0}:{1}/api".format(API_HOST, API_PORT)

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
    data = { "message":   "Test message at :{0}".format(get_current_timestamp())}
    send_reqeust("firewall-log", data)

if __name__ == '__main__':
    run()
