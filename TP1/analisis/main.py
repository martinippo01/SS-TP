# This is a sample Python script.
import json
import sys


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    if len(sys.argv) < 2:
        print("results file missing")
        exit(1)

    with open(f'{sys.argv[1]}', 'r') as results_file:
        results = json.load(results_file)
        particles = results['particles']
        neighbours = results['neighbours']
        l = results['l']
        m = results['m']
        n = results['n']
        time = results['time']





# See PyCharm help at https://www.jetbrains.com/help/pycharm/
