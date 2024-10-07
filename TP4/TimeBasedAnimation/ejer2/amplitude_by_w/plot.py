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
fontsize = 20

plt.figure(figsize=(10, 7))
plt.plot(x, y, 'o', markersize=5)
plt.xlabel('Tiempo $(s)$', fontsize=fontsize)
plt.ylabel('Amplitud $(m)$', fontsize=fontsize)
plt.xticks(fontsize=fontsize)
plt.yticks(fontsize=fontsize)
plt.plot(max_x, max_y, 'ro', markersize=10)
plt.axhline(y=max_y, color='r', linestyle='--')
plt.show()

