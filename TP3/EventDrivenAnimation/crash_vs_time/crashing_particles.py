import json
from matplotlib import pyplot as plt
import os

# Para el 1.3a poner en True, para el 1.3b poner en False
each_particle_bounces_once = False

# Specify the parent directory path
parent_directory = '../../EventDrivenSimulation/output/para1punto3_05'
# Si output tiene directorios dentro que a su vez tienen los distintos directorios con timestamp, poner True
multiple_directories = False


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
            cum_amount.append(current_amount)
            t.append(crash["tc"])
        print(len(cum_amount))
        plt.plot(t, cum_amount)


def go_one_directory_deeper(parent, func):
    for data_directory in os.listdir(parent):
        data_path = os.path.join(parent, data_directory)

        if os.path.isdir(data_path):
            retrieve_data(data_path, func)


def retrieve_data(data_path, func):
    specific_file = 'dynamic.json'
    specific_file_path = os.path.join(data_path, specific_file)

    # Check if the specific file exists and perform some action
    if os.path.isfile(specific_file_path):
        func(specific_file_path)


for item in os.listdir(parent_directory):
    item_path = os.path.join(parent_directory, item)
    print(item)
    if os.path.isdir(item_path):
        if multiple_directories:
            go_one_directory_deeper(item_path, add_plot)
        else:
            retrieve_data(item_path, add_plot)

plt.show()
