import java.io.File

fun main() {
    part1()
    part2()
}

fun part1() = parseFromFile()
    .filter { (_, value) -> value.hasEnoughOfEachColor() }
    .keys
    .sum()
    .let { println("part 1: $it") }

fun part2() = parseFromFile().values
    .sumOf { it.max("red") * it.max("green") * it.max("blue") }
    .let { println("part 2: $it") }

private fun List<Map<String, Int>>.hasEnoughOfEachColor() = this.all {
    it.hasEnough("red") && it.hasEnough("green") && it.hasEnough("blue")
}

private fun Map<String, Int>.hasEnough(color: String) =
    (this[color] ?: 0) <= colors[color]!!

private val colors = mapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14
)

private fun List<Map<String, Int>>.max(color: String) =
    this.maxOf { it[color] ?: 0 }

private fun parseFromFile() = File("src/main/resources/02.txt").readLines().map { it.split(":") }
    .associate { it[0].key() to it[1].value() }

private fun String.value(): List<Map<String, Int>> = split(";")
    .map { round ->
        round.split(",")
            .map { it.trim().split(" ") }
            .associate { it[1].trim() to it[0].trim().toInt() }
    }

private fun String.key(): Int = split(" ")[1].toInt()