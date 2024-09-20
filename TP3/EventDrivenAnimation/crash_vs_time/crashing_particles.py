import json
import numpy as np
from matplotlib import pyplot as plt
import os

# Set to True for 1.3a and False for 1.3b
each_particle_bounces_once = False
show_lineal_regresion = False

# Specify the parent directory path
parent_directory = '../../EventDrivenSimulation/output'
multiple_directories = True

# Initialize figure and axes globally so that everything is plotted on the same figure
fig, ax = plt.subplots()

colors = ['b', 'r', 'g', 'y', 'c', 'm']
my_labels = ['T = 0.25 $U.A.$', 'T = 1 $U.A.$', 'T = 4 $U.A.$', 'T = 9 $U.A.$', 'T = 16 $U.A.$', 'T = 25 $U.A.$']
col_val = 0

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
            if each_particle_bounces_once and p_id in already_crashed:
                continue
            if each_particle_bounces_once:
                already_crashed[p_id] = True
            current_amount += 1
            tc = crash["tc"]
            if tc > 5:
                break
            cum_amount.append(current_amount)
            t.append(tc)

        # Plot on the global axes
        ax.plot(t, cum_amount, c=colors[col_val], label=my_labels[col_val])


def go_one_directory_deeper(parent, func):
    for data_directory in os.listdir(parent):
        data_path = os.path.join(parent, data_directory)

        if os.path.isdir(data_path):
            retrieve_data(data_path, func)


def retrieve_data(data_path, func):
    specific_file = 'dynamic.json'
    specific_file_path = os.path.join(data_path, specific_file)

    if os.path.isfile(specific_file_path):
        func(specific_file_path)


for item in os.listdir(parent_directory):
    item_path = os.path.join(parent_directory, item)
    print(item)
    if os.path.isdir(item_path):
        if multiple_directories:
            go_one_directory_deeper(item_path, add_plot)
            col_val += 1
        else:
            retrieve_data(item_path, add_plot)


# Plot linear regression lines after crash data is plotted
if not each_particle_bounces_once and show_lineal_regresion:
    with open("1punto3b_min_errores.json", "r") as file:
        dynamic_data = json.load(file)

    data = {}

    # Extracting the 'c_value' from the dynamic_data and organizing it
    for key, c_data in dynamic_data.items():
        c_values = [entry["c_value"] for entry in c_data.values()]
        data[key] = c_values

    # Prepare data for plotting
    pendientes = [np.mean(values) for values in data.values()]



    for pend in pendientes:
        ax.axline((0, 0), slope=pend, linestyle='--', label=f'V0 {pend}')


handles, labels = plt.gca().get_legend_handles_labels()
unique_labels = dict(zip(labels, handles))

# Show the legend with unique labels
plt.legend(unique_labels.values(), unique_labels.keys(), fontsize=25)

plt.xlabel('$Tiempo\ (s)$', fontsize=30)
plt.ylabel('Cantidad Acumulativa de Choques', fontsize=30)
plt.tight_layout()

plt.xticks(fontsize=30)
plt.yticks(fontsize=30)

plt.show()
