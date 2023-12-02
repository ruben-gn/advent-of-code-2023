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
    .sumOf(::productOfMaximumShown)
    .let { println("part 2: $it") }

private fun productOfMaximumShown(game: Game) =
    colors.productOf { game.max(it) }

private fun Collection<Color>.productOf(function: (Color) -> Int) =
    this.map(function).reduce { acc, i -> acc * i }

private fun Game.hasEnoughOfEachColor() =
    this.all { round -> round.hasEnoughOfEachColor() }

private fun Round.hasEnoughOfEachColor() =
    colors.all { color -> this.hasEnough(color) }

private fun Round.hasEnough(color: Color) =
    (this[color] ?: 0) <= color.needs

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

private val colors = Color.entries.toTypedArray().toList()

enum class Color(val needs: Int) {
    Red(12), Green(13), Blue(14);

    companion object {
        fun of(color: String) = when (color.trim()) {
            "red" -> Red
            "green" -> Green
            "blue" -> Blue
            else -> throw IllegalArgumentException("Unknown color: $color")
        }
    }
}
