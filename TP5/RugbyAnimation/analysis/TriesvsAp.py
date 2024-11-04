import os
import json
import matplotlib.pyplot as plt
from collections import defaultdict

# Define the directory containing the JSON files
directory_path = "../../RugbySimulation/output/2024-11-04_11-54-03"

# Dictionary to store the total events and TRY events per Nj
data_by_ap = defaultdict(lambda: {"total_events": 0, "try_events": 0})

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
        try_count = sum(1 for event in data["events"] if event["endEventType"] == "TRY")
        total_events = len(data["events"])

        # Accumulate counts for this Nj value
        data_by_ap[ap]["try_events"] += try_count
        data_by_ap[ap]["total_events"] += total_events

# Prepare data for plotting
ap_values = sorted(data_by_ap.keys())
try_percentages = [
    (data_by_ap[ap]["try_events"] / data_by_ap[ap]["total_events"]) * 100
    for ap in ap_values
]

# Find the index of the maximum TRY percentage
max_index = try_percentages.index(max(try_percentages))

# Plot TRY percentage vs Ap
plt.figure(figsize=(10, 6))
plt.ylim(0, 100)
plt.plot(ap_values, try_percentages, marker='o', linestyle='-', color='b')
plt.plot(ap_values[max_index], try_percentages[max_index], 'ro',
         label="Max TRY Percentage")  # Highlight max point in red
plt.xlabel("Ap")
plt.ylabel("Percentage of TRY Events")
myxticks = []
for i in range(15, 95):
    if i % 5 == 0 and i != 65:
        myxticks.append(i)
myxticks.append(int(ap_values[max_index]))

plt.xticks(myxticks)
ax = plt.gca()
for tick_label in ax.get_xticklabels():
    print(int(tick_label.get_text()))
    print(ap_values[max_index])
    if int(tick_label.get_text()) == int(ap_values[max_index]):
        tick_label.set_color("red")

plt.show()
