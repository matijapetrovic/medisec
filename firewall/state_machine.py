from time import sleep
from log_generator import *
from request_sender import *

LOG_PATH = "sim/"
LOG_FILES = ["sim1.log", "sim2.log", "sim3.log"]

class State(ABC):
    def __init__(self):
        self.log_generator_ = LogGenerator()
        self.data = {}

    def set_generator_data(self):
        self.data = self.log_generator_.set_data().get_data()

    @abstractmethod
    def set_data(self):
        return NotImplemented

    def data_to_string(self):
        str_data = ""
        for value in self.data.values():
            str_data += str(value) + " "
        return str_data

    def write(self, path):
        f = open(path, "a")
        f.write(self.data_to_string() + "\n")
        f.close()
        send_reqeust("service-log", self.data)
        sleep(3)

    def run(self, context):
        print(self)
        self.set_data()
        self.write(context.path)
        context.generate_next_state()
        context.state.run(context)


class Context:
    def __init__(self, iterations):
        self.state = NormalState()
        self.iterations = iterations - 1
        self.path = LOG_PATH + "sim3.log"
        #self.path = LOG_PATH + random.choice(LOG_FILES)

    def run(self):
        self.state.run(self)

    @property
    def state(self):
        return self._state

    @state.setter
    def state(self, value):
        self._state = value

    def generate_next_state(self):
        if not self.iterations:
            quit()
        self.iterations -= 1
        state_name = random.choice(log_states)
        self.state = self.state_machine(state_name)

    def state_machine(self, state_name):
        if state_name == "NORMAL":
            return NormalState()
        elif state_name == "BRUTE_FORCE_ATTACK":
            return BruteForceState()
        elif state_name == "DOS_ATTACK":
            return DosAttactState()
        elif state_name == "MALICIOUS_IP":
            return MaliciousIpState()
        elif state_name == "INACTIVE_ACCOUNT":
            return InactiveAccountState()
        elif state_name == "VALID_LOGIN":
            return ValidLoginState()
        elif state_name == "FAILED_LOGIN":
            return FailedLoginState()
        else:
            return ErrorState()


class NormalState(State):
    def set_data(self):
        self.set_generator_data()
        self.data["status"] = 200
        self.data["type"] = "NORMAL"

class BruteForceState(State):
    def set_data(self):
        self.set_generator_data()
        self.data["type"] = "BRUTE_FORCE_ATTACK"

class DosAttactState(State):
    def set_data(self):
        self.set_generator_data()
        self.data["status"] = 400
        self.data["type"] = "DOS_ATTACK"

class MaliciousIpState(State):
    def set_data(self):
        self.set_generator_data()
        self.data["sourceIp"] = random.choice(malicious_ips)
        self.data["type"] = "MALICIOUS_IP"

class InactiveAccountState(State):
    def set_data(self):
        self.set_generator_data()
        self.data["path"] = "POST /login?username=" + str(random.choice(inactive_accounts)) + "&password=ok_password"
        self.data["type"] = "INACTIVE_ACCOUNT"

class ErrorState(State):
    def set_data(self):
        self.set_generator_data()
        self.data["type"] = "ERROR"

class ValidLoginState(State):
    def set_data(self):
        self.set_generator_data()
        self.data["path"] = "POST /login?username=" + "username" + str(random.randint(1, 5)) + "&password=ok_password"
        self.data["status"] = 200
        self.data["type"] = "VALID_LOGIN"

class FailedLoginState(State):
    def set_data(self):
        self.set_generator_data()
        self.data["path"] = "POST /login?username=" + "username" + str(random.randint(1, 5)) + "&password=ok_password"
        self.data["status"] = 400
        self.data["type"] = "FAILED_LOGIN"
