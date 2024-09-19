import numpy as np
import json
import math

# Load the JSON data
with open('../input/n200t5CIRCULAR/v3/dynamic.json', 'r') as file:
    dynamic_data = json.load(file)

with open('../input/n200t5CIRCULAR/v3/static.json', 'r') as file:
    static_data = json.load(file)

isCircular = static_data['inputData']['planeType'] != "BOX"
L = static_data['inputData']['L']
TOLERANCE = L / 500
DT = static_data['inputData']['maxTime'] / 20
M = static_data['inputData']['m']
OBSTACLE_R = static_data['inputData']['obstacles'][0]['radius']
v = static_data['inputData']['v0']
particle_r = static_data['inputData']['r']


def get_vn_obstacle(particles_crashed):
    hyp = (particles_crashed['position']['x'] ** 2 + particles_crashed['position']['y'] ** 2) ** 0.5
    cos = particles_crashed['position']['x'] / hyp
    sin = particles_crashed['position']['y'] / hyp
    return abs(particles_crashed['velocity']['vX'] * cos + particles_crashed['velocity']['vY'] * sin)


def get_vn_wall(particles_crashed):
    if abs(particles_crashed['position']['x'] - particle_r) < TOLERANCE or abs(particles_crashed['position']['x'] + particle_r - L) < TOLERANCE:
        return abs((-1) * particles_crashed['velocity']['vX'])
    elif abs(particles_crashed['position']['y'] - particle_r) < TOLERANCE or abs(particles_crashed['position']['y'] + particle_r - L) < TOLERANCE:
        return abs((-1) * particles_crashed['velocity']['vY'])
    print("failed")

def get_vn_wall_circular(particles_crashed):
    x = particles_crashed['position']['x']
    y = particles_crashed['position']['y']
    vX = particles_crashed['velocity']['vX']
    vY = particles_crashed['velocity']['vY']
    hyp = (particles_crashed['position']['x'] ** 2 + particles_crashed['position']['y'] ** 2) ** 0.5
    cos = particles_crashed['position']['x'] / hyp #n1
    sin = particles_crashed['position']['y'] / hyp #n2
    return abs((((sin ** 2 - cos ** 2) * vX - (2*sin*cos) * vY) * cos + (-(2 * sin * cos) * vX + (cos**2-sin**2) * vY) * sin))
    # distance = (x ** 2 + y ** 2) ** 0.5
    # nx = x / distance
    # ny = y / distance
    # vn = (vX * nx + vY * ny)
    # return abs(vn)


velocities_n_wall = []
velocities_n_obstacle = []

pressure_wall_by_dt = []
pressure_obstacle_by_dt = []
time = 0

for i, event in enumerate(dynamic_data['events']):
    tc = event['tc']
    if tc > time + DT:
        print("(" + str(time) + " - " + str(tc) + ") \n\t wall: " + str(len(velocities_n_wall)) + "\n\t obs: " + str(len(velocities_n_obstacle)))
        time += DT
        # Append pressure with corresponding time (tc)
        pressure_wall_by_dt.append({"pressure": sum(velocities_n_wall), "tc": time})
        pressure_obstacle_by_dt.append({"pressure": sum(velocities_n_obstacle), "tc": time})
        velocities_n_wall = []
        velocities_n_obstacle = []
    if event['event'] == "OBSTACLE":
        velocities_n_obstacle.append(
            (2 * M * get_vn_obstacle(event['particlesCrashed'][0]))
            /
            (DT * 2 * math.pi * OBSTACLE_R)
        )
    if event['event'] == "WALL":
        if isCircular:
            velocities_n_wall.append(
                (2 * M * get_vn_wall_circular(event['particlesCrashed'][0]))
                /
                (DT * 2 * math.pi * (L/2))
            )
        else:
            velocities_n_wall.append(
                (2 * M * get_vn_wall(event['particlesCrashed'][0]))
                /
                (DT * 4 * L)
            )

# Create the output dictionary
output_data = {
    "pressure_wall_by_dt": pressure_wall_by_dt,
    "pressure_obstacle_by_dt": pressure_obstacle_by_dt
}

# calculate the average of the values in pressure_wall_by_dt
sum = 0
for i in pressure_wall_by_dt:
    sum += i['pressure']
avg_wall = sum / len(pressure_wall_by_dt)

sum = 0
for i in pressure_obstacle_by_dt:
    sum += i['pressure']
avg_obs = sum / len(pressure_obstacle_by_dt)

# calculate the std for the values in pressure_wall_by_dt and pressure_obstacle_by_dt using numpy
std_wall = np.std([i['pressure'] for i in pressure_wall_by_dt])
std_obs = np.std([i['pressure'] for i in pressure_obstacle_by_dt])

# Write the data to a JSON file
with open('./pressure_data.json', 'w') as outfile:
    json.dump(output_data, outfile, indent=4)

print("For velocity: " + str(v) + "m/s the avg pressures are \n\t - obstacle: \t" + str(avg_obs) + "\n\t - wall: \t\t" + str(avg_wall))

with open('./avg_pressure/avg_pressure_' + str(v) + '.json', 'w') as outfile:
    json.dump({
        "avg_wall": avg_wall,
        "avg_obstacle": avg_obs,
        "std_wall": std_wall,
        "std_obs": std_obs,
        "velocity": v},
        outfile, indent=4)
