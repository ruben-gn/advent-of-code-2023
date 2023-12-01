import java.io.File

fun main() {
    calculate("part 1", "src/main/resources/01.txt") { it }
    calculate("part 2", "src/main/resources/01.txt") { it.replaceWordsWithNumbers() }
}

private fun calculate(part: String, file: String, function: (String) -> String) {
    val input = File(file).readText()
    val result = input.split("\n")
        .map { function(it) }
        .map { line -> "${line.first { it.isDigit() }}${line.last { it.isDigit() }}" }
        .sumOf { it.toInt() }

    println("$part: $result")
}

private fun String.replaceWordsWithNumbers(): String = this
    .replace("one", "one1one")
    .replace("two", "two2two")
    .replace("three", "three3three")
    .replace("four", "four4four")
    .replace("five", "five5five")
    .replace("six", "six6six")
    .replace("seven", "seven7seven")
    .replace("eight", "eight8eight")
    .replace("nine", "nine9nine")
