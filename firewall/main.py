from datetime import datetime
import requests

API_PORT = 8481
API_HOST = "localhost"
BASE_URL = "https://{0}:{1}/api".format(API_HOST, API_PORT)

API_CRT = "firewall.crt"
API_KEY = "firewall.key"
API_CER = "firewall.cer"
API_P7B = "firewall.p7b"
API_PEM = "firewall.pem"
ROOT_CRT = "root.cer"
secret = "attack"

def get_current_timestamp():
    now = datetime.now()
    return now.strftime("%d/%m/%Y %H:%M:%S")

import urllib3

import ssl
import http.client
import json
def send_reqeust(resource, data):
    headers = {'Content-type': 'application/json'}
    path = BASE_URL + "/" + resource

    # Define the client certificate settings for https connection
    context = ssl.SSLContext(ssl.PROTOCOL_SSLv23)
    context.load_cert_chain(certfile=API_PEM, password=secret)

    # Create a connection to submit HTTP requests
    connection = http.client.HTTPSConnection(API_HOST, port=API_PORT, context=context)

    # Use connection to submit a HTTP POST request
    connection.request(method="POST", url=path, headers=headers, body=json.dumps(data))

    # Print the HTTP response from the IOT service endpoint
    response = connection.getresponse()
    print(response.status, response.reason)
    data = response.read()
    print(data)
    # try:
    #     requests.post(path, json=data, headers=headers, verify=ROOT_CRT, cert=(API_CER, API_KEY))
    # except Exception as ex:
    #         print(ex)

def run():
    data = { "message":   "Test message at :{0}".format(get_current_timestamp())}
    send_reqeust("firewall-log", data)


if __name__ == '__main__':
    run()
