import json
import sys

from matplotlib import pyplot as plt


def plot_slope(plt, x, D):
    y = [2 * D * xi for xi in x]
    plt.plot(x, y, linewidth=4, color='red', label=f'$<z^2>$')


if len(sys.argv) != 2:
    print("Usage: python analysis.py <json>")
    sys.exit(1)

x = []
y = []
yerr = []
font_size = None
fig_size = None
time = {}
slope = {}

with open(sys.argv[1], 'r') as json_file:
    config = json.load(json_file)['graph']
    input_file = config['inputFile']
    font_size = int(config['fontSize'])
    fig_size = list(map(int, config['figSize']))
    time = config['time']
    slope = config['slope']

    with open(input_file, 'r') as analysis_file:
        analysis_json = json.load(analysis_file)

        tc_max = float(time['max'])
        tc_min = float(time['min'])

        tc_keys = list(map(lambda key: (key, float(key)), analysis_json.keys()))
        tc_keys_sorted = sorted(tc_keys, key=lambda key: key[1])
        tc_keys_sorted = list(filter(lambda key: tc_min <= key[1] <= tc_max, tc_keys_sorted))

        for tc_str, tc in tc_keys_sorted:
            x.append(tc)
            mean_and_std = analysis_json[tc_str]
            y.append(mean_and_std['mean'])
            yerr.append(mean_and_std['std'])

plt.figure(figsize=(10, 7))
plt.xlabel('$t\ (s)$', fontsize=font_size)
plt.ylabel('$z\ (m^2)$', fontsize=font_size)
plt.xticks(fontsize=font_size)
plt.yticks(fontsize=font_size)
plt.ticklabel_format(axis='y', style='sci', scilimits=(0,0), useMathText=True)
ax = plt.gca()
ax.yaxis.get_offset_text().set_fontsize(font_size)
plt.errorbar(x, y, yerr=yerr, fmt='o', capsize=5, capthick=2)

if slope['show']:
    plot_slope(plt, x, float(slope['D']))
    plt.legend(fontsize=font_size)

plt.show()
