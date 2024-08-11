import json
import sys

def 

if __name__ == '__main__':
    if len(sys.argv) < 2:
        print("results file missing")
        exit(1)

    with open(f'{sys.argv[1]}', 'r') as results_file:
        results = json.load(results_file)
