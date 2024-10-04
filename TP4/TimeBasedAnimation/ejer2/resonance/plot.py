import json
import sys
import matplotlib.pyplot as plt

if len(sys.argv) != 2:
    print("Usage: python plot.py <config>")
    sys.exit(1)

config = None
with open(sys.argv[1], 'r') as f:
    config = json.load(f)
    config = config['plot']

x = []
y = []
max_x = 0
max_y = 0

input_file = config['inputFile']
with open(input_file, 'r') as f:
    data = json.load(f)
    keys = data.keys()
    keys_sorted = sorted(keys, key=lambda key: float(key))
    for key in keys_sorted:
        value = data[key]
        x.append(float(key))
        y.append(value)
        if value > max_y:
            max_y = value
            max_x = float(key)

print(f'y: {y}')
plt.plot(x, y, 'o')
plt.xlabel('w $(rad/s)$')
plt.ylabel('Amplitud $(m)$')
plt.plot(max_x, max_y, 'ro')
plt.ticklabel_format(axis='y', style='sci', scilimits=(0,0), useMathText=True)
plt.xticks(x)
plt.show()

