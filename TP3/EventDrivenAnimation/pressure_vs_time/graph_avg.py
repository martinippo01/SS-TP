import json
import os
from matplotlib import pyplot as plt

# Initialize lists to store data
velocities = []
avg_walls = []
avg_obstacles = []
std_walls = []
std_obstacles = []

# Path to the folder containing the JSON files
folder_path = './avg_pressure/'

# Read each JSON file in the folder
for filename in os.listdir(folder_path):
    if filename.endswith('.json'):
        with open(os.path.join(folder_path, filename), 'r') as json_file:
            data = json.load(json_file)

            # Extract data from JSON
            velocities.append(data['velocity'] ** 2)
            avg_walls.append(data['avg_wall'])
            avg_obstacles.append(data['avg_obstacle'])
            std_walls.append(data['std_wall'])
            std_obstacles.append(data['std_obs'])

# Plot the graph
plt.figure(figsize=(10, 7))
plt.xlabel('Temperatura (U.A.)', fontsize=14)
plt.ylabel('Presion promedio ()', fontsize=14)
plt.xticks(fontsize=12)
plt.yticks(fontsize=12)

# Plot avg_wall with error bars (std_wall)
plt.errorbar(velocities, avg_walls, yerr=std_walls, fmt='o', color='blue', label='Pared')

# Plot avg_obstacle with error bars (std_obs)
plt.errorbar(velocities, avg_obstacles, yerr=std_obstacles, fmt='o', color='green', label='Obstáculo')

# Add legend
plt.legend(fontsize=12, loc='upper left')

# Add margins to the y-axis for better spacing
plt.margins(y=0.1)

# Show the plot
plt.show()
