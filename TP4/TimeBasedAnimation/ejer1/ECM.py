import json
import math
import os
import matplotlib.pyplot as plt

# Constants for the analytical solution
A = 1  # should be 1
gamma = 100
mass = 70
tf = 5
k = 10000
font_size=25

# Analytical solution function
def analytical_solution(time):
    return (A * math.exp(-(gamma * time / (2 * mass)))
            *
            math.cos(math.pow(k / mass - gamma * gamma / (4 * mass * mass), 1 / 2) * time))


# Base path to the directory containing subdirectories
base_directory_path = '../../TimeBasedSimulation/outputs/damped/'

ecm_values = {}

# Iterate through each directory under the base directory
for subdir, _, files in os.walk(base_directory_path):
    subdir_name = os.path.basename(subdir)

    # Process each file in the current directory
    for filename in files:
        if subdir_name not in ecm_values:
            ecm_values[subdir_name] = {}

        if filename.endswith('.json'):
            file_path = os.path.join(subdir, filename)

            # Load the data from the JSON file
            with open(file_path, 'r') as f:
                data = json.load(f)

            steps = data['steps']

            # Extract time and y-position values
            time_values = [step['time'] for step in steps]
            y_values = [step['positions'][0]['y'] for step in steps]

            # Calculate the analytical solution for each time step
            y_real_values = [analytical_solution(t) for t in time_values]

            # Compute squared errors and ECM
            errores = [math.pow((y_real_values[i] - y_values[i]), 2) for i in range(len(time_values))]
            ecm = sum(errores) / len(time_values)

            ecm_values[subdir_name][filename] = ecm

            # Print the ECM for the current file along with its subdirectory
            print(f"Directory: {subdir_name}, File: {filename}, ECM: {ecm}")

print(ecm_values)


# Initialize the plot
plt.figure(figsize=(10, 6))

# Define colors and markers for each method
colors = {'beeman': 'blue', 'gear5': 'green', 'verlet': 'red', 'verletAlt': 'purple'}
correct_name = {'beeman': 'Beeman', 'gear5': 'Gear Five', 'verlet': 'Verlet', 'verletAlt': 'Verlet Alt'}
markers = {'beeman': 'o', 'gear5': 's', 'verlet': '^', 'verletAlt': 'x'}

# Define custom x-axis labels based on file naming, reversing the order
x_labels = {'2': r'$10^{-2}$', '3': r'$10^{-3}$', '4': r'$10^{-4}$', '5': r'$10^{-5}$', '6': r'$10^{-6}$'}

# Plot the data for each method
for method, values in ecm_values.items():
    # Extract file names (x-axis) and ECM values (y-axis)
    files = list(values.keys())
    ecm_values_spec = list(values.values())

    # Create custom x-values based on file naming, reversed
    custom_x = [x_labels[file[0]] for file in files[::-1]]  # Reverse the file order for x-axis

    # Reverse the corresponding ECM values to match
    ecm_values_spec = ecm_values_spec[::-1]

    # Plot with lines and dots
    plt.plot(custom_x, ecm_values_spec, marker=markers[method], color=colors[method], label=correct_name[method], linestyle='-',
             markersize=8)

# Set y-axis to logarithmic scale
plt.yscale('log')

# Add labels and title
plt.xlabel('Paso temporal (s)', fontsize=font_size)
plt.ylabel('Error Cuadrático Medio', fontsize=font_size)

# Customize x-ticks
plt.xticks(custom_x, fontsize=font_size)
plt.yticks(fontsize=font_size)

# Add legend
plt.legend(fontsize=font_size)


plt.show()
