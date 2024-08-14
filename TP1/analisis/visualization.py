import matplotlib.pyplot as plt
import json

import numpy as np

with open('../../._2024-08-14_12-29-12.json') as json_data:
    data = json.load(json_data)
    json_data.close()
    print(data)


p = data['particles']

epsilon = 0.0001
x = []
y = []
radius = []
id = []
colors = []
neigh = data['neighbours']
print(neigh)
L = data['l']
M = data['m']
rc = data['rc']

points_whole_ax = L * 0.8 * 72  # Cuenta mágica para la cantidad de puntos en la pantalla

# el segundo valor es rc/L
rc_radius = (2 * (rc / L) / 1.0 * points_whole_ax) ** 2  # Cuenta para calcular bien el size

for i in range(len(p)):
    x.append(p[i]['point']['x'])
    y.append(p[i]['point']['y'])
    pid = p[i]['id']
    id.append(pid)

    rad = p[i]['radius']
    if rad < 0.0 + epsilon:
        radius.append(50)
    else:
        radius.append((2 * (rad / L) / 1.0 * points_whole_ax) ** 2)

    # diferente color de partícula dependiendo de cantidad de vecinos
    if len(neigh.get(str(pid), [])) == 0:
        colors.append('blue')
    elif len(neigh.get(str(pid), [])) == 1:
        colors.append('red')
    elif len(neigh.get(str(pid), [])) == 2:
        colors.append('purple')
    else:
        colors.append('yellow')

plt.figure(figsize=[L, L])
ax = plt.axes([0.1, 0.1, 0.8, 0.8], xlim=(0, L), ylim=(0, L))

# las labels con los ids (también podríamos poner los neighbours)
for i in range(len(p)):
    ax.annotate(p[i]['id'], (x[i], y[i]), xytext=(x[i]-0.05, y[i]+0.1))

# Seteamos la cantidad de líneas a mostrar en el grid
ticks = [i * L / (M) for i in range(M)]
ax.set_xticks(ticks)
ax.set_yticks(ticks)

# partículas
ax.scatter(x, y, s=radius, c=colors)
# rc
plt.scatter(x, y, s=rc_radius, marker='o', facecolors='none', edgecolors='y')
plt.scatter(x, y, s=rc_radius, marker='o', alpha=0.05)

plt.grid(True, which='both')


plt.show()
