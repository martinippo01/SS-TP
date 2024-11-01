import os
import json
import pandas as pd
import matplotlib.pyplot as plt


def load_json_files(directory_path):
    data = []
    for filename in os.listdir(directory_path):
        if filename.endswith(".json"):
            file_path = os.path.join(directory_path, filename)
            with open(file_path, 'r') as file:
                json_data = json.load(file)
                nj_value = json_data["params"]["Nj"]

                # Check if the last event's name is "TRY"
                value = 1 if json_data["events"][-1]["name"] == "TRY" else 0

                if nj_value is not None:
                    data.append({"Nj": nj_value, "Value": value})
    return pd.DataFrame(data)


def calculate_avg_std(df):
    # Calculate mean (probability of TRY) and count for each Nj group
    grouped_df = df.groupby("Nj").agg(
        Avg_Value=("Value", "mean"),
        Count=("Value", "size")  # Count of occurrences for each Nj
    ).reset_index()

    # Calculate Bernoulli standard error based on the probability of TRY and count
    grouped_df["Std_Value"] = (grouped_df["Avg_Value"] * (1 - grouped_df["Avg_Value"]) / grouped_df["Count"]) ** 0.5
    return grouped_df


def plot_avg_std(df):
    plt.figure(figsize=(10, 6))

    # Plot with error bars
    plt.errorbar(df["Nj"], df["Avg_Value"], yerr=df["Std_Value"], fmt='o',
                 ecolor='salmon', capsize=5, linestyle='-', color='skyblue')

    plt.xlabel("Nj")
    plt.ylabel("Average TRY Events")
    plt.grid(True, linestyle='--', alpha=0.7)
    plt.tight_layout()
    plt.show()


# Main execution
directory_path = "../../RugbySimulation/output/2024-10-31_21-07-20"
df = load_json_files(directory_path)
grouped_df = calculate_avg_std(df)
plot_avg_std(grouped_df)
