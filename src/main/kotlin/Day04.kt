import kotlin.time.measureTimedValue

fun main() {
    val input = readLines("04")
    repeat(10) { part1(input) }// warmup for time to be more precies

    val (result1, time1) = measureTimedValue { part1(input) }
    val (result2, time2) = measureTimedValue { part2(input) }
    println("part 1: $result1 in $time1")
    println("part 2: $result2 in $time2")
}

private fun part1(input: List<String>) = input
    .parseCards()
    .map(::winningNumbers)
    .sumOf { it.score() }

private fun part2(input: List<String>): Int {
    val ticketsWithNumber = input
        .parseCards()
        .map(::winningNumbers)
        .mapIndexed { index, winningNumbers -> TicketInfo(id = index, nrWinningNumbers = winningNumbers) }

    for ((id, amount, nrWinningNumbers) in ticketsWithNumber) {
        repeat(nrWinningNumbers) { ticketsWithNumber[id + it + 1].amount += amount }
    }

    return ticketsWithNumber.sumOf { it.amount }
}

data class TicketInfo(
    val id: Int,
    var amount: Int = 1,
    val nrWinningNumbers: Int
)

typealias Ticket = Pair<List<String>, List<String>>

private fun List<String>.parseCards(): List<Ticket> = this
    .map { line -> line.split(":").last() }
    .map { line -> line.split("|") }
    .map { line -> line.first().numbers() to line.last().numbers() }

fun winningNumbers(ticket: Ticket): Int = ticket.second
    .count { it in ticket.first }

private fun Int.score(): Int = when (this) {
    0 -> 0
    1 -> 1
    else -> (this - 1).score() * 2
}

private fun String.numbers(): List<String> = this.split(" ").filterNot { it.isEmpty() }
