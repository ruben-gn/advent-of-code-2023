private val lines = readLines("09")
    .map { it.split(" ").map { char -> char.toInt() } }

fun main() {
    timed { part1() }
    timed { part2() }
}

private fun part1() = lines.map(::getAllDifferences)
    .sumOf { it.fold(0) { acc, list -> acc + list.last() }.toInt() }
    .let { println("part 1: $it") }

private fun part2() = lines.map { it.reversed() }.map(::getAllDifferences)
    .sumOf { it.fold(0) { acc, list -> acc + list.last() }.toInt() }
    .let { println("part 2: $it") }

private fun getAllDifferences(input: List<Int>): MutableList<List<Int>> {
    val differences = mutableListOf(input)
    while (differences.last().any { it != 0 }) {
        differences.add(differences.last().differenceWithNext())
    }
    return differences
}

private fun List<Int>.differenceWithNext() = this.zipWithNext().map { pair -> pair.second - pair.first }