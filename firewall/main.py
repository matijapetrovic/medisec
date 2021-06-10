from datetime import datetime

import certifi
import requests
import random

from requests.exceptions import SSLError

API_PORT = 8481
API_HOST = "localhost"
BASE_URL = "https://{0}:{1}/api".format(API_HOST, API_PORT)

API_CRT = "device1.cer"
API_KEY = "device1.key"
ROOT_CRT = "rootBongcloudCA.pem"
secret = "attack"

malicious_ips = ["103.227.8.154", "198.38.90.126", "160.20.45.145", "134.119.192.123", "103.225.53.235", "122.14.131.208", "122.14.131.208", "160.20.45.87 ",
                 "203.138.203.200", "200.225.202.93", "160.20.45.15", "160.20.45.180", "103.48.37.61", "163.172.198.101", "160.20.45.241", "185.14.249.86 ",
                 "103.7.155.9", "103.7.155.12", "103.7.155.4", "103.7.155.6", "103.7.155.7", "103.7.155.10", "103.7.155.2"]
http_methods = ["GET", "POST", "PUT", "DELETE", "PATCH"]
http_paths = ["/csr", "/certificates", "/certificate-requests", "/templates"]
status_codes = ["200", "400"]
inactive_accounts = ["inactiveuser1", "inactiveuser2", "inactiveuser3"]
active_accounts = ["activeuser1", "activeuser2", "activeuser3"]
log_types = ["INFORMATION", "WARNING", "ERROR"]

class LogGeneratorHelper:
    def get_current_timestamp(self):
        now = datetime.now()
        return now.strftime("%d/%m/%Y %H:%M:%S")

    def generate_random_ip(self):
        random.seed(random.randint(0, 100000))
        return ".".join(str(random.randint(0, 256)) for _ in range(4))

    def generete_random_path(self):
        random.seed(random.randint(0, 100000))
        method = random.choice(http_methods)
        path = random.choice(http_paths)
        return " ".join([method, path])

    def get_random_status_code(self):
        return random.choice(status_codes)


class LogGenerator(LogGeneratorHelper):
    def __init__(self):
        self.data = {}

    def set_data(self):
        pass

    def get_data(self):
        pass

    def create_http_log(self, source_ip, dest_ip, path, protocol, status):
        timestamp = self.get_current_timestamp()
        packet_size = str(random.randint(1024, 2048))
        return " ".join([timestamp, packet_size, source_ip, dest_ip, path, protocol, status])


class RandomLogGenerator(LogGenerator):
    def generate_random_valid_ip(self):
        while True:
            ip = self.generate_random_ip()
            if ip not in malicious_ips:
                return ip

    def get_random_ip_method(self):
        ip_types = ["valid", "malicious"]
        return self.call_generate_ip(random.choice(ip_types))

    def call_generate_ip(self, ip_type):
        if ip_type == "valid":
            return self.generate_random_valid_ip()
        elif ip_type == "malicious":
            return random.choice(malicious_ips)

    def set_data(self):
        self.data["sourceIp"] = self.generate_random_ip()
        self.data["destIp"] = self.get_random_ip_method()
        self.data["path"] = self.generete_random_path()
        self.data["protocol"] = "HTTP/1.1"
        self.data["status"] = self.get_random_status_code()
        self.data["time"] = self.get_current_timestamp()
        self.data["packetSize"] = str(random.randint(1024, 2048))
        self.data["type"] = random.choice(log_types)

    def get_data(self):
        return self.data

class LoginLogGenerator(LogGenerator):
    def login(self, username):
        self.data["sourceIp"] = self.generate_random_ip()
        self.data["destIp"] = self.generate_random_ip()
        self.data["path"] = "POST /login?username=" + username + "&password=ok_password"
        self.data["protocol"] = "HTTP/1.1"
        self.data["status"] = "200"
        self.data["time"] = self.get_current_timestamp()
        self.data["packetSize"] = str(random.randint(1024, 2048))
        self.data["type"] = random.choice(log_types)

    def fail_login(self, username):
        self.data["sourceIp"] =  self.generate_random_ip()
        self.data["destIp"] = self.generate_random_ip()
        self.data["path"] = "POST /login?username=" + username + "&password=bad_password"
        self.data["protocol"] = "HTTP/1.1"
        self.data["status"] = "400"
        self.data["time"] = self.get_current_timestamp()
        self.data["packetSize"] = str(random.randint(1024, 2048))
        self.data["type"] = random.choice(log_types)

    def call_login_method(self, method):
        if method == "login":
            return self.login(random.choice(active_accounts))
        elif method == "fail_login":
            return self.fail_login(random.choice(inactive_accounts))

    def get_random_login_method(self):
        login_methods = ["login", "fail_login"]
        return self.call_login_method(random.choice(login_methods))

    def set_data(self):
        return self.get_random_login_method()

    def get_data(self):
        return self.data


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

def run(num_requests=10):
    log_generators = [LoginLogGenerator(), RandomLogGenerator()]
    for i in range(num_requests):
        log_generator = random.choice(log_generators)
        log_generator.set_data()
        print(log_generator.get_data())
        send_reqeust("firewall-log", log_generator.get_data())


if __name__ == '__main__':
    run(num_requests=3)

