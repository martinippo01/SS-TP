import json
import glob


def process_json(dir_path, output_name_file):
    # List to store the extracted data
    output_data = []

    # Use glob to match all JSON files in the directory
    for file_name in glob.glob(dir_path + '*.json'):
        with open(file_name, 'r') as file:
            data = json.load(file)
            # Extract M and time, and store in the output list
            output_data.append({"M": data["M"], "time": data["time"]})

    # Save the output data to a new JSON file
    with open(output_name_file, 'w') as output_file:
        json.dump(output_data, output_file, indent=4)

    print("Data has been combined and saved to output.json")


if __name__ == '__main__':
    dir_path = './output_data/'
    output_file_name = 'output.json'
    print("Start preprocessing")
    process_json(dir_path, output_file_name)
    print("Finished")
