import json
import sys

from matplotlib import pyplot as plt


def plot_slope(plt, x, D):
    y = [2 * D * xi for xi in x]
    plt.plot(x, y, linewidth=4, color='red', label=f'$<z^2>$')

x_w = []
y_w = []
x_o = []
yerr = []

y_o = []
font_size = None
fig_size = None
time = {}
slope = False

with open("./graph_format.json", 'r') as json_file:
    config = json.load(json_file)['graph']
    input_file = config['input_file']
    font_size = int(config['fontSize'])
    # fig_size = list(map(int, config['figSize']))
    time = config['time']
    slope = config['slope']

    with open(input_file, 'r') as analysis_file:
        analysis_json = json.load(analysis_file)

        # tc_max = float(time['max'])
        # tc_min = float(time['min'])
        #
        # tc_keys = list(map(lambda key: (key, float(key)), analysis_json.keys()))
        # tc_keys_sorted = sorted(tc_keys, key=lambda key: key[1])
        # tc_keys_sorted = list(filter(lambda key: tc_min <= key[1] <= tc_max, tc_keys_sorted))

        for p_w in analysis_json['pressure_wall_by_dt']:
            x_w.append(p_w['tc'])
            y_w.append(p_w['pressure'])

        for p_o in analysis_json['pressure_obstacle_by_dt']:
            x_o.append(p_o['tc'])
            y_o.append(p_o['pressure'])

plt.figure(figsize=(10, 7))
plt.xlabel('$?$', fontsize=font_size)
plt.ylabel('$?$', fontsize=font_size)
plt.xticks(fontsize=font_size)
plt.yticks(fontsize=font_size)
plt.ticklabel_format(axis='y', style='sci', scilimits=(0,0), useMathText=True)
ax = plt.gca()
ax.yaxis.get_offset_text().set_fontsize(font_size)

plt.plot(x_w, y_w, format("o-"))
plt.plot(x_o, y_o, format("o-"))
plt.show()

