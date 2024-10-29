import json
import sys

import numpy as np
from matplotlib import pyplot as plt

if len(sys.argv) != 2:
    print("Usage: python plot.py <config>")
    sys.exit(1)

with open(sys.argv[1]) as f:
    config = json.load(f)
    config = config["plot"]

input_file = config["inputFile"]

aps = []
bps = []
try_counts = []

with open(input_file, 'r') as f:
    data = json.load(f)
    for ap in data:
        for bp in data[ap]:
            aps.append(ap)
            bps.append(bp)

    aps_sorted = sorted(set(aps))
    bps_sorted = sorted(set(bps), reverse=True)

    for bp in bps_sorted:
        row = []
        for ap in aps_sorted:
            try_count = data[ap][bp]
            row.append(try_count)
        try_counts.append(row)

fig, ax = plt.subplots()
im = ax.imshow(try_counts)

font_size = config["fontSize"]

ax.set_xticks(np.arange(len(aps_sorted)), labels=aps_sorted, fontsize=font_size)
ax.set_yticks(np.arange(len(bps_sorted)), labels=bps_sorted, fontsize=font_size)

plt.setp(ax.get_xticklabels(), rotation=45, ha="right", rotation_mode="anchor")

for i in range(len(bps_sorted)):
    for j in range(len(aps_sorted)):
        text = ax.text(j, i, try_counts[i][j], ha="center", va="center", color="w", fontsize=font_size)

plt.xlabel("Ap", fontsize=font_size)
plt.ylabel("Bp", fontsize=font_size)
fig.tight_layout()
plt.show()
