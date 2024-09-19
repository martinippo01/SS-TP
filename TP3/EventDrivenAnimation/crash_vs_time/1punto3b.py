import json
import os
import numpy

# ESTO ES PARA CONSEGUIR EL SLOPE DEL 1.3b


parent_directory = '../../EventDrivenSimulation/output'

data = {}


def calculate_min_error(tc_values, amount_values, c_min, c_max, c_step):
    min_error = -1
    best_c = -1
    for c in numpy.arange(c_min, c_max, c_step).tolist():
        error = 0
        for x, y in zip(tc_values, amount_values):
            f = c * x
            error += (y - f) ** 2
        if error < min_error or min_error < 0:
            min_error = error
            best_c = c
    return {'min_error': min_error, 'c_value': best_c}


def get_hits_with_time(dynamic_file, item_spec_dict, for_data_key, max_sec):
    with (open(dynamic_file, 'r') as file):
        dynamic_data = json.load(file)
        events = dynamic_data["events"]
        obstacle_crashes = (crash for crash in events if crash["event"] == "OBSTACLE")
        current_amount = 0

        values = {'tc': [], 'amount': []}

        for crash in obstacle_crashes:
            current_amount += 1
            tc = crash["tc"]
            values['tc'].append(tc)
            values['amount'].append(current_amount)

            if tc > max_sec:
                break

        item_spec_dict[for_data_key] = values


# data tiene un diccionario el cual tiene <nombreDelDirectorio, otroDiccionario>
# otroDiccionario es <tiradaEnParticular, json con arrays de tc y cum_amount>

for item in os.listdir(parent_directory):
    item_path = os.path.join(parent_directory, item)
    if os.path.isdir(item_path):
        i = 0
        print(item)
        item_dict = {}
        for data_directory in os.listdir(item_path):
            data_path = os.path.join(item_path, data_directory)

            if os.path.isdir(data_path):
                dyn_path = os.path.join(data_path, 'dynamic.json')

                if os.path.isfile(dyn_path):
                    get_hits_with_time(dyn_path, item_dict, item + '_' + str(i), 5)
                    i += 1

        data[item] = item_dict

slope_data = {}

for key, dir_data in data.items():
    dir_slopes = {}
    for sub_key, spec_run_data in dir_data.items():
        dir_slopes[sub_key] = calculate_min_error(spec_run_data['tc'], spec_run_data['amount'], 1, 3000, 1)

    slope_data[key] = dir_slopes

with open("1punto3b_todos_los_datos.json", 'w+') as outputFile:
    json.dump(data, outputFile)

with open("1punto3b_min_errores.json", 'w+') as outputFile:
    json.dump(slope_data, outputFile)



