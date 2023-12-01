import java.io.File

fun main() {
    calculate("part 1", "src/main/resources/01.txt") { it }
    calculate("part 2", "src/main/resources/01.txt") { it.addNumbersForWords() }
}

private fun calculate(part: String, file: String, transform: (String) -> String) {
    val input = File(file).readLines()
    val result = input
        .map { transform(it) }
        .map { line -> line.mapNotNull { it.digitToIntOrNull() } }
        .map { line -> "${line.first()}${line.last()}" }
        .sumOf { it.toInt() }

    println("$part: $result")
}

private fun String.addNumbersForWords(): String = this
    .replace("one", "one1one")
    .replace("two", "two2two")
    .replace("three", "three3three")
    .replace("four", "four4four")
    .replace("five", "five5five")
    .replace("six", "six6six")
    .replace("seven", "seven7seven")
    .replace("eight", "eight8eight")
    .replace("nine", "nine9nine")
