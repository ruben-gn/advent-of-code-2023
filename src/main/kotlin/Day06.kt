private val lines = readLines("06").map { it.split(":").last() }

fun main() {
    timed { part1() }
    timed { part2() }
}

private fun part1() = lines
    .map { line -> line.split(" ").filterNot { it.isEmpty() } }
    .let { it[0].map(String::toLong).zip(it[1].map(String::toLong)) }
    .result()
    .let { println("part 1: $it") }

private fun part2() = lines
    .map { it.replace(" ", "") }
    .zipWithNext { a, b -> a.toLong() to b.toLong() }
    .result()
    .let { println("part 2: $it") }

private fun List<Pair<Long, Long>>.result() = this
    .map { (time, record) -> (1..time).map { heldDown -> distance(time, heldDown) to record } }
    .map { it.filter { (distance, record) -> distance > record } }
    .map { it.count() }
    .reduce(Int::times)

private fun distance(time: Long, heldDown: Long) = (time - heldDown) * (time - (time - heldDown))