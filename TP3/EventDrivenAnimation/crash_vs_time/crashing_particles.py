import json
from matplotlib import pyplot as plt
import os


def add_plot(dynamic_file):
    with open(dynamic_file, 'r') as file:
        dynamic_data = json.load(file)
        events = dynamic_data["events"]
        obstacle_crashes = (crash for crash in events if crash["event"] == "OBSTACLE")
        already_crashed = {}
        current_amount = 0
        cum_amount = []
        t = []

        cum_amount.append(0)
        t.append(0)

        for crash in obstacle_crashes:
            p_id = crash["particlesCrashed"][0]["id"]
            if p_id not in already_crashed:
                already_crashed[p_id] = True
                current_amount += 1
                cum_amount.append(current_amount)
                t.append(crash["tc"])

        plt.plot(t, cum_amount)



# Specify the parent directory path
parent_directory = '../../EventDrivenSimulation/output'

# Loop through all files and directories
for item in os.listdir(parent_directory):
    item_path = os.path.join(parent_directory, item)

    # Check if the item is a directory
    if os.path.isdir(item_path):
        # Define the specific file you're looking for in the subdirectory
        specific_file = 'dynamic.json'
        specific_file_path = os.path.join(item_path, specific_file)

        # Check if the specific file exists and perform some action
        if os.path.isfile(specific_file_path):
            add_plot(specific_file_path)


plt.show()
