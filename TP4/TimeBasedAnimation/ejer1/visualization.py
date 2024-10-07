import json
import matplotlib.pyplot as plt
import math

# Load the data from the dynamic.json file
file_paths = ['../../TimeBasedSimulation/outputs/damped/beeman/2beeman.json',
              '../../TimeBasedSimulation/outputs/damped/gear5/2gear5.json',
              '../../TimeBasedSimulation/outputs/damped/verlet/2verlet.json']

names = ['Beeman', 'Gear Five', 'Verlet']
colors = ['blue', 'green', 'red']
markers = ['o', 's', '^']

focus = True

plt.figure(figsize=(8, 6))

for i in range(0, len(file_paths)):
    with open(file_paths[i], 'r') as f:
        data = json.load(f)

    steps = data['steps']
    # Extract time and y-position values
    time_values = [step['time'] for step in steps]
    y_values = [step['positions'][0]['y'] for step in steps]

    plt.plot(time_values, y_values, marker=markers[i], color=colors[i], label=names[i])

plt.xlabel('Tiempo (s)')
plt.ylabel('Posición (m)')
if focus:
    plt.xlim(4.86, 4.95)
    plt.ylim(-0.02, -0.0065)

A = 1  # debería ser 1
gamma = 100
mass = 70
tf = 5
k = 10000


def analytical_solution(time):
    return (A * math.exp(-(gamma * time / (2 * mass)))
            *
            math.cos(math.pow(k / mass - gamma * gamma / (4 * mass * mass), 1 / 2) * time))


y_real_values = [analytical_solution(t) for t in time_values]

plt.plot(time_values, y_real_values, marker='x', linestyle='--', color='orange', label='Solución Analítica')
plt.legend()
# Display the plot
plt.show()
