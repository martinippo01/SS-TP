# This script allows to run the java program n times

# Arguments:
# $1: Number of times you want to run the program
# $2: Program input file

# Usage: ./runner.sh <times> <input_file>

times=$1

if [ -z "$times" ]
then
    echo "Please provide the number of times you want to run the program"
    exit 1
fi

if ! [[ "$times" =~ ^[0-9]+$ ]]
then
    echo "Please provide a valid number"
    exit 1
fi

if [ "$times" -lt 1 ]
then
    echo "Please provide a number greater than 0"
    exit 1
fi

input_file=$2

if [ -z "$input_file" ]
then
    echo "Please provide the input file"
    exit 1
fi

if [ ! -f "$input_file" ]
then
    echo "The input file does not exist"
    exit 1
fi

for i in $(seq 1 "$times")
do
    MAVEN_OPTS='-Dinput='$input_file'' mvn exec:java &> /dev/null
    echo "[$i/$times] Done"
done
