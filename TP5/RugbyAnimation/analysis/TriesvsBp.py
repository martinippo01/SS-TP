import os
import json
import matplotlib.pyplot as plt
from collections import defaultdict

# Define the directory containing the JSON files
directory_path = "../../RugbySimulation/output/MuchosBp"

# Dictionary to store the total events and TRY events per Nj
data_by_bp = defaultdict(lambda: {"total_events": 0, "try_events": 0})

# Iterate over all JSON files in the directory
for filename in os.listdir(directory_path):
    if filename.endswith(".json"):
        file_path = os.path.join(directory_path, filename)

        # Load JSON data
        with open(file_path, "r") as file:
            data = json.load(file)

        # Extract Nj from params and initialize if not present
        bp = data["params"]["Bp"]

        # Count TRY events and total events
        try_count = sum(1 for event in data["events"] if event["endEventType"] == "TRY")
        total_events = len(data["events"])

        # Accumulate counts for this Nj value
        data_by_bp[bp]["try_events"] += try_count
        data_by_bp[bp]["total_events"] += total_events

# Prepare data for plotting
bp_values = sorted(data_by_bp.keys())
try_percentages = [
    (data_by_bp[bp]["try_events"] / data_by_bp[bp]["total_events"]) * 100
    for bp in bp_values
]

# Find the index of the maximum TRY percentage
max_index = try_percentages.index(max(try_percentages))

font_size = 20

# Plot TRY percentage vs Bp
plt.figure(figsize=(12, 8))
plt.plot(bp_values, try_percentages, marker='o', linestyle='-', color='b')
plt.xlabel("Bp (m)", fontsize=font_size)
plt.ylabel("Fracción de tries", fontsize=font_size)
plt.xticks(fontsize=font_size)
plt.yticks(fontsize=font_size)
ax = plt.gca()

plt.show()
