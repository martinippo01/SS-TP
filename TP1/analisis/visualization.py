import matplotlib.pyplot as plt
import json

import numpy as np

with open('../../._2024-08-14_12-29-12.json') as json_data:
    data = json.load(json_data)
    json_data.close()
    print(data)

p = data['particles']

epsilon = 0.0001
neigh = data['neighbours']
print(neigh)
L = data['l']
M = data['m']
rc = data['rc']
particles_by_neigh_count = {}

points_whole_ax = L * 0.8 * 72  # Cuenta mágica para la cantidad de puntos en la pantalla

s = []

plt.figure(figsize=[L, L])
ax = plt.axes([0.1, 0.1, 0.8, 0.8], xlim=(0, L), ylim=(0, L))


def pacman_radius(ghost_x, ghost_y, ghost_rad, ghost_rc, real_L, particle_info_dict):
    over_top = ghost_y + ghost_rad > real_L
    over_right = ghost_x + ghost_rad > real_L
    over_bottom = ghost_y - ghost_rad < 0
    over_left = ghost_x - ghost_rad < 0

    if over_right and over_top:
        add_ghost_particle(particle_info_dict, ghost_x - L, ghost_y - L, ghost_rad, ghost_rc)

    if over_right:
        add_ghost_particle(particle_info_dict, ghost_x - L, ghost_y, ghost_rad, ghost_rc)

    if over_top:
        add_ghost_particle(particle_info_dict, ghost_x, ghost_y - L, ghost_rad, ghost_rc)

    if over_left and over_bottom:
        add_ghost_particle(particle_info_dict, ghost_x + L, ghost_y + L, ghost_rad, ghost_rc)

    if over_left:
        add_ghost_particle(particle_info_dict, ghost_x + L, ghost_y, ghost_rad, ghost_rc)

    if over_bottom:
        add_ghost_particle(particle_info_dict, ghost_x, ghost_y + L, ghost_rad, ghost_rc)

    if over_right and over_bottom:
        add_ghost_particle(particle_info_dict, ghost_x - L, ghost_y + L, ghost_rad, ghost_rc)

    if over_left and over_top:
        add_ghost_particle(particle_info_dict, ghost_x + L, ghost_y - L, ghost_rad, ghost_rc)

def add_ghost_particle(p_info, ghost_x_val, ghost_y_val, ghost_p_rad, ghost_rc_val):
    p_info['x'].append(ghost_x_val)
    p_info['y'].append(ghost_y_val)
    p_info['radius'].append(ghost_p_rad)
    p_info['s'].append(ghost_rc_val)


for i in range(len(p)):
    pid = p[i]['id']
    neigh_count = len(neigh.get(str(pid), []))
    neigh_count_str = str(neigh_count)
    if neigh_count_str not in particles_by_neigh_count:
        particles_by_neigh_count[neigh_count_str] = {
            'x': [],
            'y': [],
            'radius': [],
            's': []
        }

    x = particles_by_neigh_count[neigh_count_str]['x']
    y = particles_by_neigh_count[neigh_count_str]['y']
    radius = particles_by_neigh_count[neigh_count_str]['radius']
    s = particles_by_neigh_count[neigh_count_str]['s']

    px = p[i]['point']['x']
    py = p[i]['point']['y']
    rad = p[i]['radius']

    x.append(px)
    y.append(py)

    rc_rad = (2 * ((rad + rc) / L) / 1.0 * points_whole_ax) ** 2
    s.append(rc_rad)

    if rad < 0.0 + epsilon:
        rad_in_pixels = 50
    else:
        rad_in_pixels = (2 * (rad / L) / 1.0 * points_whole_ax) ** 2

    radius.append(rad_in_pixels)

    if data['pacman']:
        pacman_radius(px, py, rad_in_pixels, rc_rad, L, particles_by_neigh_count[neigh_count_str])

    ax.annotate(pid, (px, py), xytext=(px - 0.05, py + 0.1))

# Seteamos la cantidad de líneas a mostrar en el grid
ticks = [i * L / (M) for i in range(M)]
ax.set_xticks(ticks)
ax.set_yticks(ticks)

neigh_counts_str = list(particles_by_neigh_count.keys())
neigh_counts = list(map(int, neigh_counts_str))
neigh_counts_sorted = sorted(neigh_counts)
for neigh_count in neigh_counts_sorted:
    particles = particles_by_neigh_count[str(neigh_count)]
    x = particles['x']
    y = particles['y']
    radius = particles['radius']
    print(f'radius: {radius}')
    s = particles['s']
    label = f'{neigh_count} vecino{"s" if neigh_count != 1 else ""}'
    # Particles
    ax.scatter(x, y, s=radius, label=label)
    # rc
    ax.scatter(x, y, s=s, marker='o', facecolors='none', edgecolors='y')
    ax.scatter(x, y, s=s, marker='o', alpha=0.05)

plt.grid(True, which='both')
plt.legend(bbox_to_anchor=(1, 1))

plt.show()
