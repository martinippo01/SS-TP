import json
import sys
import matplotlib.pyplot as plt

if len(sys.argv) != 2:
    print("Usage: python analysis.py <config>")
    sys.exit(1)

# Read the configuration file
config = None
with open(sys.argv[1], 'r') as f:
    config = json.load(f)
    config = config['plot']

x = []
max_x = 0
y = []
max_y = 0

# Read the data file
input_file = config['inputFile']
with open(input_file, 'r') as f:
    input_json = json.load(f)
    amplitude_by_time = input_json
    for time, amplitude in amplitude_by_time.items():
        time = float(time)
        time = round(time, 2)
        x.append(time)
        y.append(amplitude)
        if amplitude > max_y:
            max_y = amplitude
            max_x = time

# Plot the data
plt.plot(x, y, 'o-')
plt.xlabel('Tiempo $(s)$')
plt.ylabel('Amplitud $(m)$')
plt.plot(max_x, max_y, 'ro')
plt.show()

