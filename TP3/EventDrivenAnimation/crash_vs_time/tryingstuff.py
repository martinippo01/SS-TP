import json
import os
import numpy
import matplotlib.pyplot as plt


data = {}

parent_directory = '../../EventDrivenSimulation/output'


def calculate_min_error(tc_values, amount_values, c_min, c_max, c_step):
    min_error = -1
    best_c = -1
    cs = numpy.arange(c_min, c_max, c_step).tolist()
    all_errors = []
    for c in cs:
        error = 0
        for x, y in zip(tc_values, amount_values):
            f = c * x
            error += (y - f) ** 2
        if error < min_error or min_error < 0:
            min_error = error
            best_c = c
        all_errors.append(error)
    return [cs, all_errors, best_c, min_error]


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


# Solo usar con una carpeta en output a la vez lol

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
    for sub_key, spec_run_data in dir_data.items():
        datos_del_error = calculate_min_error(spec_run_data['tc'], spec_run_data['amount'], 1, 700, 1)


# Plot y as a function of x
plt.figure(figsize=(8, 6))
plt.plot(datos_del_error[0], datos_del_error[1], marker='o', linestyle='-', color='b')

print(datos_del_error[2])

# Add labels and title
plt.xlabel('x values')
plt.ylabel('y values')
plt.title('Plot of y as a function of x')
plt.plot(datos_del_error[2], datos_del_error[3], marker='o', color='red', markersize=10, label=f'Highlighted Point ({datos_del_error[2]}, {datos_del_error[3]})')

# Display the plot
plt.tight_layout()
plt.show()
