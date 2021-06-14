from requests.exceptions import SSLError
import certifi
import requests

API_PORT = 8481
API_HOST = "localhost"
BASE_URL = "https://{0}:{1}/api".format(API_HOST, API_PORT)

API_CRT = "device1.cer"
API_KEY = "device1.key"
ROOT_CRT = "rootBongcloudCA.pem"
secret = "attack"


def send_reqeust(resource, data):
    headers = {'Content-type': 'application/json'}
    path = BASE_URL + "/" + resource
    try:
        requests.post(path, json=data, headers=headers, verify=ROOT_CRT, cert=(API_CRT, API_KEY))
    except SSLError as ex:
        cafile = certifi.where()
        with open('rootBongcloudCA.pem', 'rb') as infile:
            customca = infile.read()
        with open(cafile, 'ab') as outfile:
            outfile.write(customca)
        print(ex)
        print(certifi.where())