from datetime import datetime

import random
from abc import ABC, abstractmethod

malicious_ips = ["103.227.8.154", "198.38.90.126", "160.20.45.145", "134.119.192.123", "103.225.53.235", "122.14.131.208", "122.14.131.208", "160.20.45.87 ",
                 "203.138.203.200", "200.225.202.93", "160.20.45.15", "160.20.45.180", "103.48.37.61", "163.172.198.101", "160.20.45.241", "185.14.249.86 ",
                 "103.7.155.9", "103.7.155.12", "103.7.155.4", "103.7.155.6", "103.7.155.7", "103.7.155.10", "103.7.155.2"]
http_methods = ["GET", "POST", "PUT", "DELETE", "PATCH"]
http_paths = ["/csr", "/certificates", "/certificate-requests", "/templates"]
status_codes = ["200", "400"]
inactive_accounts = ["inactiveuser1", "inactiveuser2", "inactiveuser3"]
active_accounts = ["activeuser1", "activeuser2", "activeuser3"]
log_states = ["NORMAL", "BRUTE_FORCE_ATTACK", "DOS_ATTACK", "MALICIOUS_IP", "INACTIVE_ACCOUNT", "VALID_LOGIN", "FAILED_LOGIN", "ERROR", ]


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


class LogGenerator(ABC, LogGeneratorHelper):
    def __init__(self):
        self.data = {}

    def set_data(self):
        self.data["sourceIp"] = self.generate_random_ip()
        self.data["destIp"] = self.generate_random_ip()
        self.data["path"] = self.generete_random_path()
        self.data["protocol"] = "HTTP/1.1"
        self.data["status"] = random.choice(status_codes)
        self.data["time"] = self.get_current_timestamp()
        self.data["packetSize"] = random.randint(1024, 2048)
        self.data["type"] = random.choice(log_states)
        return self

    def get_data(self):
        return self.data

    def create_http_log(self, source_ip, dest_ip, path, protocol, status):
        timestamp = self.get_current_timestamp()
        packet_size = str(random.randint(1024, 2048))
        return " ".join([timestamp, packet_size, source_ip, dest_ip, path, protocol, status])