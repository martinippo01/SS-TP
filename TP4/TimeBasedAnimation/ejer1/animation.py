import json
import cv2
import numpy as np

# Load JSON data
with open('../input/beeman.json') as f:
    data = json.load(f)

# Video settings
width, height = 640, 480
fps = 10  # Frames per second
out = cv2.VideoWriter('animation.avi', cv2.VideoWriter_fourcc(*'XVID'), fps, (width, height))


# Function to draw a dot at given x, y coordinates
def draw_dot(frame, x, y):
    x = int((x + 1) / 2 * width)  # Scale x to fit the window
    y = int((1 - y) / 2 * height)  # Scale y and invert to match OpenCV's coordinates
    cv2.circle(frame, (x, y), 10, (0, 255, 0), -1)  # Draw a green circle


# Animate over each step in the JSON file
for step in data['steps']:
    frame = np.zeros((height, width, 3), dtype=np.uint8)  # Black background
    pos = step['positions'][0]  # Get the first position for this step
    draw_dot(frame, pos['x'], pos['y'])

    # Display time on the frame
    cv2.putText(frame, f"Time: {step['time']:.1f}s", (10, 30), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 255), 2)

    # Write frame to the video
    out.write(frame)
    cv2.imshow('Animation', frame)

    if cv2.waitKey(int(1000 / fps)) & 0xFF == ord('q'):
        break

out.release()
cv2.destroyAllWindows()
