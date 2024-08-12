import matplotlib.pyplot as plt
import numpy as np
import json
import math


with open('../../results.json') as json_data:
    data = json.load(json_data)
    json_data.close()
    print(data)


plt.style.use('_mpl-gallery')

p = data['particles']

x=[]
y=[]
radius=[]
id=[]
colors=[]
neigh=data['particleNeighbours']
print(neigh)
print(neigh['16'][0])
L=data['L']
M=10

points_whole_ax = L * 0.8 * 72 # confiar

# el segundo valor es rc/L
point_radius = 2 * 1/M / 1.0 * points_whole_ax

for i in range(len(p)):
       x.append(p[i]['point']['x'])
       y.append(p[i]['point']['y'])
       radius.append(p[i]['radius']+5)
       id.append(p[i]['id'])
       if p[i]['id'] == neigh['16'][0]:
           colors.append('red')
       else:
           colors.append('blue')

       id.append(-1)
       x.append(p[i]['point']['x'])
       y.append(p[i]['point']['y'])
       radius.append(10**2 * 3.14)
       colors.append('lightblue')

x.append(0)
y.append(0)
radius.append(point_radius**2)
id.append(-2)
colors.append('black')


# plot


M = 10

plt.figure(figsize=[L,L])
ax = plt.axes([0.1, 0.1, 0.8, 0.8], xlim=(0, L), ylim=(0, L))
# corregir que se vea mejor el grid de fondo del diagrama

ax.scatter(x, y, s=radius, c=colors)

plt.show()



