import os
import json
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

font_size = 16


def load_json_files(directory_path):
    data = []
    for filename in os.listdir(directory_path):
        if filename.endswith(".json"):
            file_path = os.path.join(directory_path, filename)
            with open(file_path, 'r') as file:
                json_data = json.load(file)
                nj_value = json_data["params"]["Nj"]
                value = 100 - json_data["events"][-1]["redPlayer"]["pos"]["x"]

                if nj_value is not None:
                    data.append({"Nj": nj_value, "Value": value})
    return pd.DataFrame(data)


def calculate_avg_std(df):
    grouped_df = df.groupby("Nj").agg(Avg_Value=("Value", "mean"), Std_Value=("Value", "std")).reset_index()
    grouped_df["Std_Error"] = grouped_df["Std_Value"] # / np.sqrt(100) # sigma / sqrt(n)
    return grouped_df


def plot_avg_std(df):
    plt.figure(figsize=(10, 6))

    # Esto es para que no se me vaya para arriba de 100 o abajo de 0
    lower_error = np.minimum(df["Std_Error"], df["Avg_Value"])
    upper_error = np.minimum(df["Std_Error"], 100 - df["Avg_Value"])

    # Set asymmetric error bars for each point
    yerr = [lower_error, upper_error]

    # Plot with error bars
    plt.errorbar(df["Nj"], df["Avg_Value"], yerr=yerr, fmt='o',
                 ecolor='salmon', capsize=5, linestyle='-', color='skyblue')

    plt.xlabel("Nj", fontsize=font_size)
    plt.ylabel("<|x - 100|>", fontsize=font_size)
    plt.xticks(df["Nj"], fontsize=font_size)
    plt.yticks(fontsize=font_size)
    plt.tight_layout()
    plt.show()


# Main execution
directory_path = "../../RugbySimulation/output/ap_56_bp_1_5/2024-11-04_12-37-41"
df = load_json_files(directory_path)
grouped_df = calculate_avg_std(df)
plot_avg_std(grouped_df)