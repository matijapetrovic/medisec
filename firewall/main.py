from datetime import datetime
import requests
import random

API_PORT = 8481
API_HOST = "localhost"
BASE_URL = "http://{0}:{1}/api".format(API_HOST, API_PORT)

API_CRT = "firewall.crt"
API_KEY = "firewall.key"
API_CER = "firewall.cer"
API_P7B = "firewall.p7b"
API_PEM = "firewall.pem"
ROOT_CRT = "root.cer"
secret = "attack"

http_methods = ["GET", "POST", "PUT", "DELETE", "PATCH"]
http_paths = ["/csr", "/certificates", "/certificate-requests", "/templates"]
status_codes = ["200", "400"]

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
    def set_data(self):
        self.data["sourceIp"] = self.generate_random_ip()
        self.data["destIp"] = self.generate_random_ip()
        self.data["path"] = self.generete_random_path()
        self.data["protocol"] = "HTTP/1.1"
        self.data["status"] = self.get_random_status_code()
        self.data["time"] =self.get_current_timestamp()
        self.data["packetSize"] = str(random.randint(1024, 2048))
        #return self.create_http_log(source_ip, dest_ip, path, protocol, status)

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
        #return self.create_http_log(source_ip, dest_ip, path, protocol, status)

    def fail_login(self, username):
        self.data["sourceIp"] =  self.generate_random_ip()
        self.data["destIp"] = self.generate_random_ip()
        self.data["path"] = "POST /login?username=" + username + "&password=bad_password"
        self.data["protocol"] = "HTTP/1.1"
        self.data["status"] = "400"
        self.data["time"] = self.get_current_timestamp()
        self.data["packetSize"] = str(random.randint(1024, 2048))
        #return self.create_http_log(source_ip, dest_ip, path, protocol, status)

    def call_login_method(self, method):
        if method == "login":
            return self.login("username")
        elif method == "fail_login":
            return self.fail_login("bad_username")

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
        #requests.post(path, json=data, headers=headers, verify=ROOT_CRT, cert=(API_CER, API_KEY))
        requests.post(path, json=data, headers=headers)
    except Exception as ex:
            print(ex)

def run(num_requests=10):
    log_generators = [LoginLogGenerator(), RandomLogGenerator()]
    for i in range(num_requests):
        log_generator = random.choice(log_generators)
        log_generator.set_data()
        print(log_generator.get_data())
        send_reqeust("firewall-log", log_generator.get_data())


if __name__ == '__main__':
    run(num_requests=5)
