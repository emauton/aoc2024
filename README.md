# Advent of Code 2024

## Using the code

Each day is written up in a separate namespace, e.g. `aoc2024.day1`.

There are general utilities used across days in `aoc2024.util`.

See the "scaffolding" in `aoc2024.core` that allows us to run each day's script, for example:

    lein run day1 arg0 arg1 arg2

## Resources

Input files etc. can be stashed in `resources/` and imported using one of the utility functions, e.g.

    (aoc2024.util/slurp-resource "input.txt")
