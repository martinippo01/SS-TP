import statistics
import sys
import glob
import json
import time

from matplotlib import pyplot as plt


def size_scalar_analysis(output, observation_step) -> float or None:
    live_cells_for_step = output['liveCellsForStep']
    if len(live_cells_for_step) <= observation_step:
        return None
    live_cells_for_observable_step = live_cells_for_step[observation_step]
    return len(live_cells_for_observable_step)


mean_and_std_by_scalar_analysis = {
    'size': size_scalar_analysis
}


def mean_and_std_fn(config):
    output_file_prefix = config['outputFilePrefix']
    input_directory = config['inputDirectory']
    scalar_analysis = config['scalarAnalysis']
    decimal_precision = int(config['decimalPrecision'])
    observation_step = int(config['observationStep'])

    scalar_analysis_fn = mean_and_std_by_scalar_analysis[scalar_analysis]
    scalar_analysis_by_input = {}

    if scalar_analysis_fn is None:
        print("Invalid scalar analysis")
        sys.exit(1)

    for output in glob.glob(input_directory + '**/*.json', recursive=True):
        with open(output, 'r') as output_file:
            output_json = json.load(output_file)
            input = output_json['initialLiveCellsProportion']
            input = float(input) * 100
            scalar_value = scalar_analysis_fn(output_json, observation_step)

            if scalar_value is None:
                print(f"Output file {output} does not have data for observation step {observation_step}")
                continue

            if input not in scalar_analysis_by_input:
                scalar_analysis_by_input[input] = []

            scalar_analysis_by_input[input].append(scalar_value)

    scalar_mean_and_std_by_input = {}

    for input, scalar_values in scalar_analysis_by_input.items():
        mean = statistics.mean(scalar_values)
        real_mean = round(mean, decimal_precision)
        std = statistics.stdev(scalar_values) if len(scalar_values) > 1 else 0
        real_std = round(std, decimal_precision)

        scalar_mean_and_std_by_input[input] = {'mean': real_mean, 'std': real_std}

    gmt = time.gmtime()
    timestamp = time.strftime('%Y-%m-%d_%H-%M-%S', gmt)
    output_file_full_name = f'{output_file_prefix}_{timestamp}.json'

    with open(output_file_full_name, 'w+') as outputFile:
        output_json = {
            'resultsByInput': scalar_mean_and_std_by_input,
            'scalarAnalysis': scalar_analysis,
            'observationStep': observation_step
        }
        json.dump(output_json, outputFile)

    print(f"Mean and std data has been saved in {output_file_full_name} file")


ylabel_by_scalar_analysis = {
    'size': lambda observation_step: f'Tamaño de celulas vivas tras {observation_step} pasos (unidades)',
}


def plot_results(config):
    input_file = config['inputFile']

    scalar_analysis = None
    observation_step = None
    x = []
    y = []
    yerr = []

    with open(input_file, 'r') as input:
        input_json = json.load(input)
        scalar_analysis = input_json['scalarAnalysis']
        observation_step = int(input_json['observationStep'])

    ylabel_fn = ylabel_by_scalar_analysis[scalar_analysis]
    if ylabel_fn is None:
        print("Invalid scalar analysis")
        sys.exit(1)

    with open(input_file, 'r') as input:
        input_json = json.load(input)
        results_by_input = input_json['resultsByInput']

        results_by_input_keys = list(map(float, results_by_input.keys()))
        results_by_input_keys_sorted = sorted(results_by_input_keys)

        for input in results_by_input_keys_sorted:
            x.append(input)
            y.append(results_by_input[str(input)]['mean'])
            yerr.append(results_by_input[str(input)]['std'])

    plt.figure()
    plt.xlabel("Porcentaje de células vivas respecto al dominio inicial (%)")
    plt.ylabel(ylabel_fn(observation_step))
    plt.errorbar(x, y, yerr=yerr, fmt='o-', capsize=5, capthick=2)
    plt.show()


if __name__ == '__main__':
    if len(sys.argv) < 2:
        print("Usage: python input_analysis.py <config_file>")
        sys.exit(1)

    with open(sys.argv[1], 'r') as config_file:
        config = json.load(config_file)
        run = config['run']
        if run == 'mean_and_std':
            mean_and_std_fn(config['meanStdAnalysis'])
        elif run == 'plot':
            plot_results(config['plotConfig'])
        else:
            print("Invalid run type")
            sys.exit(1)
