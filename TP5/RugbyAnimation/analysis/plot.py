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
texts = []

with open(input_file, 'r') as f:
    data = json.load(f)
    for ap in data:
        for bp in data[ap]:
            aps.append(ap)
            bps.append(bp)

    aps_sorted = sorted(set(aps))
    bps_sorted = sorted(set(bps), reverse=True)

    for bp in bps_sorted:
        try_counts_row = []
        texts_row = []
        for ap in aps_sorted:
            run = data[ap][bp]
            tries = run["tries"]
            times = run["times"]
            try_counts_row.append(tries)
            texts_row.append(tries)
        try_counts.append(try_counts_row)
        texts.append(texts_row)

fig, ax = plt.subplots(figsize=(14, 10))
im = ax.imshow(try_counts)

font_size = config["fontSize"]

ax.set_xticks(np.arange(len(aps_sorted)), labels=aps_sorted, fontsize=font_size)
ax.set_yticks(np.arange(len(bps_sorted)), labels=bps_sorted, fontsize=font_size)

plt.setp(ax.get_xticklabels(), rotation=45, ha="right", rotation_mode="anchor")

for i in range(len(bps_sorted)):
    for j in range(len(aps_sorted)):
        value = float(try_counts[i][j])  # Ensure the value is a float for consistent handling
        text = ax.text(
            j, i,
            f"{value:.0f}" if value.is_integer() else f"{value:.1f}",
            ha="center", va="center", color="w", fontsize=font_size
        )


plt.xlabel("Ap", fontsize=font_size)
plt.ylabel("Bp", fontsize=font_size)
fig.tight_layout()
plt.show()
