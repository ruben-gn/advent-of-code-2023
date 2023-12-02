import java.io.File

fun main() {
    part1()
    part2()
}

fun part1() = File("src/main/resources/02.txt").readLines().map { it.split(":") }
    .associate { it[0].key() to it[1].value() }
    .filter { (_, value) ->
        value.all {
            (it["red"] ?: 0) <= 12 && (it["green"] ?: 0) <= 13 && (it["blue"] ?: 0) <= 14
        }
    }
    .map { (key, _) -> key }
    .sum()
    .let { println("part 1: $it") }

fun part2() = File("src/main/resources/02.txt").readLines().map { it.split(":") }
    .map { it[1].value() }
    .sumOf { game ->
        game.maxOf { it["red"] ?: 0 } * game.maxOf { it["green"] ?: 0 } * game.maxOf { it["blue"] ?: 0 }
    }
    .let { println("part 2: $it") }

private fun String.value(): List<Map<String, Int>> = split(";")
    .map { round ->
        round.split(",")
            .map { it.trim().split(" ") }
            .associate { it[1].trim() to it[0].trim().toInt() }
    }

private fun String.key(): Int = split(" ")[1].toInt()