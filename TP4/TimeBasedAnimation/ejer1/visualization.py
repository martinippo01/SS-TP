import json
import matplotlib.pyplot as plt
import math

# Load the data from the dynamic.json file
file_path = '../../TimeBasedSimulation/outputs/decoupled/gear5/2024-10-03_23-02-45/dynamic.json'  # Update this to the actual path of your file
with open(file_path, 'r') as f:
    data = json.load(f)

steps = data['steps']
# Extract time and y-position values
time_values = [step['time'] for step in steps]
y_values = [step['positions'][0]['y'] for step in steps]

# Create the plot
plt.figure(figsize=(8, 6))
plt.plot(time_values, y_values, marker='o', linestyle='-')
plt.title('Y Position Over Time')
plt.xlabel('Time (s)')
plt.ylabel('Y Position')
plt.grid(True)

A = 0.01 # debería ser 1
gamma = 100
mass = 70
tf = 5
k = 10000


def analytical_solution(time):
    return (A * math.exp(-(gamma * time / (2 * mass)))
            *
            math.cos(math.pow(k / mass - gamma * gamma / (4 * mass * mass), 1 / 2) * time))


dt = steps[0]['time']
y_real_values = [analytical_solution(t) for t in time_values]

plt.plot(time_values, y_real_values, marker='x', linestyle='-', color='red')

# Display the plot
plt.show()
