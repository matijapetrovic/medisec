from state_machine import *

def run(iterations=10):
    context = Context(iterations)
    context.run()


if __name__ == '__main__':
    run(iterations=3)

