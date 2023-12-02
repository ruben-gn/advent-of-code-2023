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
    .sumOf { it.max(Color.Red) * it.max(Color.Green) * it.max(Color.Blue) }
    .let { println("part 2: $it") }

private fun Game.hasEnoughOfEachColor() = this.all {
    it.hasEnough(Color.Red) && it.hasEnough(Color.Green) && it.hasEnough(Color.Blue)
}

private fun Round.hasEnough(color: Color) =
    (this[color] ?: 0) <= neededByColor[color]!!

private val neededByColor = mapOf(
    Color.Red to 12,
    Color.Green to 13,
    Color.Blue to 14
)

private fun Game.max(color: Color) =
    this.maxOf { it[color] ?: 0 }

private fun parseFromFile() = File("src/main/resources/02.txt").readLines().map { it.split(":") }
    .associate { it[0].gameId() to it[1].parseRounds() }

private fun String.parseRounds(): Game = split(";")
    .map(::parseRound)

private fun parseRound(round: String) = round.split(",")
    .map { it.trim().split(" ") }
    .associate { Color.of(it[1]) to it[0].toInt() }

private fun String.gameId(): Int = split(" ")[1].toInt()

typealias Round = Map<Color, Int>
typealias Game = List<Round>

enum class Color {
    Red, Green, Blue;

    companion object {
        fun of(color: String) = when (color.trim()) {
            "red" -> Red
            "green" -> Green
            "blue" -> Blue
            else -> throw IllegalArgumentException("Unknown color: $color")
        }

    }
}
