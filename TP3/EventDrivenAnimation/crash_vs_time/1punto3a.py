import json
import os
import numpy as np

# ESTO ES PARA CONSEGUIR EL MEAN Y STD DEL 1.3a

AMOUNT_PARTICLES = 285
output_file_tc_name = "tc_first_to_reach_300.json"
output_file_mean_and_std_name = "mean_and_std.json"
parent_directory = '../../EventDrivenSimulation/output'

tc_data = {}


def get_tc_last_hit(dynamic_file, for_data_key):
    with open(dynamic_file, 'r') as file:
        dynamic_data = json.load(file)
        events = dynamic_data["events"]
        obstacle_crashes = (crash for crash in events if crash["event"] == "OBSTACLE")
        already_crashed = {}
        current_amount = 0

        for crash in obstacle_crashes:
            p_id = crash["particlesCrashed"][0]["id"]
            if p_id in already_crashed:
                continue
            already_crashed[p_id] = True
            current_amount += 1
            if current_amount == AMOUNT_PARTICLES:
                tc_data[for_data_key].append(crash["tc"])
                return

    print("No llego a todas las particulas Item: " + for_data_key)


for item in os.listdir(parent_directory):
    item_path = os.path.join(parent_directory, item)
    if os.path.isdir(item_path):
        tc_data[item] = []
        print(item)
        for data_directory in os.listdir(item_path):
            data_path = os.path.join(item_path, data_directory)

            if os.path.isdir(data_path):
                dyn_path = os.path.join(data_path, 'dynamic.json')

                if os.path.isfile(dyn_path):
                    get_tc_last_hit(dyn_path, item)

mean_and_std = {}

for key, array in tc_data.items():
    mean = np.mean(array)
    st_dev = np.std(array)
    mean_and_std[key] = {'mean': mean, 'std': st_dev}

with open(output_file_tc_name, 'w+') as outputFile:
    json.dump(tc_data, outputFile)

with open(output_file_mean_and_std_name, 'w+') as outputFile:
    json.dump(mean_and_std, outputFile)

