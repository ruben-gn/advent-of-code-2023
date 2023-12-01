import java.io.File

fun main() {
    calculate("part 1", "src/main/resources/01.txt") { it }
    calculate("part 2", "src/main/resources/01.txt", ::addNumbersForWords)
}

private fun calculate(part: String, file: String, transform: (String) -> String) = File(file).readLines()
    .map { transform(it) }
    .map(::filterDigits)
    .map(::firstAndLast)
    .map(String::toInt)
    .let { println("$part: ${it.sum()}") }

private fun filterDigits(line: String) = line.filter(Char::isDigit)

private fun firstAndLast(line: String) = "${line.first()}${line.last()}"

private fun addNumbersForWords(line: String): String = line
    .replace("one", "one1one")
    .replace("two", "two2two")
    .replace("three", "three3three")
    .replace("four", "four4four")
    .replace("five", "five5five")
    .replace("six", "six6six")
    .replace("seven", "seven7seven")
    .replace("eight", "eight8eight")
    .replace("nine", "nine9nine")
