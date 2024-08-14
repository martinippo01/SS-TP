import json
import glob
import statistics


def process_json(dir_path, output_name_file):
    # Dictionary to store the data for each unique M value
    data_dict = {}

    # Use glob to match all JSON files in the directory
    for file_name in glob.glob(dir_path + '*.json'):
        with open(file_name, 'r') as file:
            data = json.load(file)
            M_value = data["m"]
            time_value = data["time"]

            # Append the time value to the list of times for the corresponding M value
            if M_value not in data_dict:
                data_dict[M_value] = []
            data_dict[M_value].append(time_value)

    # List to store the final processed data
    output_data = []

    # Calculate average and standard deviation for each M value
    for M_value, times in data_dict.items():
        avg_time = statistics.mean(times)
        std_dev = statistics.stdev(times) if len(times) > 1 else 0
        std_dev = int(std_dev)
        output_data.append({"M": M_value, "avg_time": avg_time, "std": std_dev})

    # Save the output data to a new JSON file
    with open(output_name_file, 'w') as output_file:
        json.dump(output_data, output_file, indent=4)

    print(f"Data has been combined and saved to {output_name_file}")


if __name__ == '__main__':
    dir_path = './output_data/'
    output_file_name = 'output.json'
    print("Start preprocessing")
    process_json(dir_path, output_file_name)
    print("Finished")
