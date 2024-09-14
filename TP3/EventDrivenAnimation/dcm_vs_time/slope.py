import json
import sys

import numpy
from matplotlib import pyplot as plt


# Function to adjust: y = 2 * c * x
def calculate_error(f_x, f_y, c):
    error = 0
    for x, y in zip(f_x, f_y):
        f = 2 * c * x
        error += (y - f) ** 2
    return error


if len(sys.argv) != 2:
    print("Usage: python slope.py <json>")
    sys.exit(1)

f_y = []
f_x = []
c_values = []
c_format_multiplier = 1
font_size = None
fig_size = None

with open(sys.argv[1], "r") as config_file:
    config = json.load(config_file)['slope']
    input_file = config['inputFile']
    max_time = float(config['maxTime'])
    font_size = int(config['fontSize'])
    fig_size = list(map(int, config['figSize']))
    c_config = config['c']
    c_max = float(c_config['max'])
    c_min = float(c_config['min'])
    c_step = float(c_config['step'])
    c_format_multiplier = float(c_config['formatMultiplier'])
    c_values = numpy.arange(c_min, c_max, c_step).tolist()

    with open(input_file, "r") as analysis_file:
        analysis_json = json.load(analysis_file)

        tc_keys = list(map(lambda key: (key, float(key)), analysis_json.keys()))
        tc_keys_sorted = sorted(tc_keys, key=lambda key: key[1])

        for tc_str, tc in tc_keys_sorted:
            if tc <= max_time:
                f_x.append(tc)
                f_y.append(analysis_json[tc_str]['mean'])


graph_x = []
graph_y = []

for c in c_values:
    error = calculate_error(f_x, f_y, c)
    graph_x.append(c)
    graph_y.append(error)

min_error = min(graph_y)
min_error_index = graph_y.index(min_error)
best_c = graph_x[min_error_index]

plt.figure(figsize=tuple(fig_size))
plt.xlabel("$D\ (m^2/s)$", fontsize=font_size)
plt.ylabel("Error", fontsize=font_size)
plt.xticks(fontsize=font_size)
plt.yticks(fontsize=font_size)
plt.plot(graph_x, graph_y, marker='o')
plt.ticklabel_format(axis='y', style='sci', scilimits=(0,0), useMathText=True)
plt.ticklabel_format(axis='x', style='sci', scilimits=(0,0), useMathText=True)
ax = plt.gca()
ax.yaxis.get_offset_text().set_fontsize(font_size)
ax.xaxis.get_offset_text().set_fontsize(font_size)
plt.plot(best_c, min_error, 'ro')
ax.annotate("{:.2f}".format(best_c*c_format_multiplier), (best_c, min_error), textcoords="offset points", xytext=(0, 10), ha='center')
plt.show()

