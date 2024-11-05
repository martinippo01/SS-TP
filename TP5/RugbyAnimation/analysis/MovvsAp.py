import os
import json
import matplotlib.pyplot as plt
from collections import defaultdict

# Define the directory containing the JSON files
directory_path = "../../RugbySimulation/output/2024-11-04_22-53-50"

# Dictionary to store the total events and TRY events per Nj
data_by_ap = defaultdict(lambda: {"amount_runs": 0, "distance": 0})

# Iterate over all JSON files in the directory
for filename in os.listdir(directory_path):
    if filename.endswith(".json"):
        file_path = os.path.join(directory_path, filename)

        # Load JSON data
        with open(file_path, "r") as file:
            data = json.load(file)

        # Extract Nj from params and initialize if not present
        ap = data["params"]["Ap"]

        # Count TRY events and total events
        dist_run = 100 - data["events"][-1]["redPlayer"]["pos"]["x"]

        # Accumulate counts for this Nj value
        data_by_ap[ap]["distance"] += dist_run
        data_by_ap[ap]["amount_runs"] += 1

# Prepare data for plotting
ap_values = sorted(data_by_ap.keys())
try_percentages = [
    (data_by_ap[ap]["distance"] / data_by_ap[ap]["amount_runs"])
    for ap in ap_values
]

# Find the index of the maximum TRY percentage
max_index = try_percentages.index(max(try_percentages))

font_size = 20

# Plot TRY percentage vs Ap
plt.figure(figsize=(12, 8))
plt.plot(ap_values, try_percentages, marker='o', linestyle='-', color='b')
plt.xlabel("Ap", fontsize=font_size)
plt.ylabel("Promedio Distancia Recorrida en X (m)", fontsize=font_size)
plt.xticks(fontsize=font_size)
plt.yticks(fontsize=font_size)
ax = plt.gca()

plt.show()
