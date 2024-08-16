import json
import glob
import sys
import time
import statistics
import matplotlib.pyplot as plt


def calculate_mean_and_std_by_m_and_n(config):
    results_by_n_and_m = {}
    mean_and_std_config = config['meanAndStdConfig']
    inputResultsDir = mean_and_std_config['inputResultsDir']
    print(inputResultsDir)

    for results_file in glob.glob(inputResultsDir + '**/*.json', recursive=True):
        with open(results_file, 'r') as results:
            results_json = json.load(results)
            m = results_json['m']
            time_in_ns = results_json['time']
            n = results_json['n']

            if n not in results_by_n_and_m:
                results_by_n_and_m[n] = {}
            if m not in results_by_n_and_m[n]:
                results_by_n_and_m[n][m] = []
            time_in_ms = time_in_ns / 1 * (10 ** (-6))
            results_by_n_and_m[n][m].append(time_in_ms)

    output_by_n_and_m = {}
    for n, m_values in results_by_n_and_m.items():
        output_by_n_and_m[n] = {}
        for m, times_in_ms in m_values.items():
            mean = statistics.mean(times_in_ms)
            real_mean = round(mean, 6)
            std = statistics.stdev(times_in_ms) if len(times_in_ms) > 1 else 0
            real_std = round(std, 6)
            output_by_n_and_m[n][m] = {'mean': real_mean, 'std': real_std}

    output = {
        "resultsByNAndM": output_by_n_and_m,
        "unit": 'ms'
    }
    outputFileName = mean_and_std_config['outputFileName']
    gmt = time.gmtime()
    timestamp = time.strftime('%Y%m%d-%H%M%S', gmt)
    output_file_full_name = outputFileName + timestamp + '.json'
    with open(output_file_full_name, 'w+') as outputFile:
        json.dump(output, outputFile)

    print(f"Mean and std data has been saved in {output_file_full_name} file")


def plot_results_by_m_and_n(config):
    plot_config = config['plotConfig']

    input_file = plot_config['inputFile']

    plt.figure(figsize=(10, 5))
    plt.xlabel("M (unidades)")

    with open(input_file, 'r') as inputFile:
        input_json = json.load(inputFile)

        unit = input_json['unit']
        plt.ylabel(f"Tiempo promedio de ejecución ({unit})")

        results_by_m_and_n = input_json['resultsByNAndM']
        int_n_values = list(map(int, results_by_m_and_n.keys()))
        ordered_n_values = sorted(int_n_values)

        for n in ordered_n_values:
            m_values = results_by_m_and_n[str(n)]
            x = []
            y = []
            yerr = []
            int_m_values = list(map(int, m_values.keys()))
            ordered_m_values = sorted(int_m_values)
            for m in ordered_m_values:
                results = m_values[str(m)]
                x.append(m)
                y.append(results['mean'])
                yerr.append(results['std'])
            plt.errorbar(x, y, yerr=yerr, marker='o', label=f'{n/20**2} p/u²')

        plt.yscale('log')
        plt.legend()
        plt.show()


if __name__ == '__main__':
    if len(sys.argv) < 2:
        print(f'Usage: {sys.argv[0]} <config file>')
        exit(1)

    with open(sys.argv[1], 'r') as config_file:
        config = json.load(config_file)
        run = config['run']
        if run == 'mean_and_std':
            calculate_mean_and_std_by_m_and_n(config)
        elif run == 'plot':
            plot_results_by_m_and_n(config)
        else:
            print("invalid run method")
            exit(1)
