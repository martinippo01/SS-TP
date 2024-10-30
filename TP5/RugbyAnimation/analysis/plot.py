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
            aps.append(float(ap))
            bps.append(float(bp))

    aps_sorted = sorted(set(aps))
    bps_sorted = sorted(set(bps), reverse=True)

    for bp in bps_sorted:
        row = []
        for ap in aps_sorted:
            ap_str = str(ap)
            bp_str = str(bp)
            try_count = data[ap_str][bp_str]
            row.append(try_count)
        try_counts.append(row)

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
plt.colorbar(im)
plt.show()
