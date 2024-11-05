import os
import json
import matplotlib.pyplot as plt
from collections import defaultdict

# Define the directory containing the JSON files
directory_path = "/path/to/your/json/files"

# Dictionary to store the total events and TRY events per vRojoMax
data_by_vRojoMax = defaultdict(lambda: {"total_events": 0, "try_events": 0})

# Iterate over all JSON files in the directory
for filename in os.listdir(directory_path):
    if filename.endswith(".json"):
        file_path = os.path.join(directory_path, filename)

        # Load JSON data
        with open(file_path, "r") as file:
            data = json.load(file)

        # Extract vRojoMax from params
        vRojoMax = data["params"]["vRojoMax"]

        # Count TRY events and total events
        try_count = int(data["events"][-1]["endEventType"] == "TRY")

        # Accumulate counts for this vRojoMax value
        data_by_vRojoMax[vRojoMax]["try_events"] += try_count
        data_by_vRojoMax[vRojoMax]["total_events"] += 1

# Prepare data for plotting
vRojoMax_values = sorted(data_by_vRojoMax.keys())
try_percentages = [
    (data_by_vRojoMax[v]["try_events"] / data_by_vRojoMax[v]["total_events"]) * 100
    for v in vRojoMax_values
]

# Plot TRY percentage vs vRojoMax
plt.figure(figsize=(10, 6))
plt.plot(vRojoMax_values, try_percentages, marker='o', linestyle='-', color='b')
plt.xlabel("vRojoMax")
plt.ylabel("Percentage of TRY Events")
plt.title("Percentage of TRY Events vs vRojoMax")
plt.show()
