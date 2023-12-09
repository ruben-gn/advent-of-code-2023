private val lines = readLines("08").filter { it.isNotEmpty() }
private val nodes = lines.drop(1).associate {
    with(it.split(" = ")) {
        this[0] to extractNodes()
    }
}
val route = generateSequence(lines[0].split("").notEmpty()) { it }.flatten()

fun main() {
    timed { part1() }
    timed { part2() }
}

private fun part1() {
    val steps = calculateStepsFromTo("AAA") { it == "ZZZ"}
    println("part 1: $steps steps")
}

private fun part2() {
    val steps = nodes.keys.filter { it.endsWith("A") }.toSet().map { calculateStepsFromTo(it) { it.endsWith("Z") } }
    val result = findLCMOfListOfNumbers(steps.map { it.toLong() })
    println("part 2: $result steps")
}

private fun calculateStepsFromTo(start: String, endCondition: (String) -> Boolean): Int {
    var node = start
    var steps = 0
    var nextSteps = route
    while (!endCondition(node)) {
        steps += 1
        node = nodes[node]!!.take(nextSteps.first())
        nextSteps = nextSteps.drop(1)
    }
    return steps
}

private fun Pair<String, String>.take(side: String) = when (side) {
    "L" -> this.first
    "R" -> this.second
    else -> throw IllegalArgumentException("Invalid side: $side")
}

private fun List<String>.notEmpty() = this.filter { it != "" }

private fun List<String>.extractNodes() =
    with(
        this[1]
            .replace("(", "")
            .replace(")", "")
            .split(", ")
    ) { this[0] to this[1] }

// LCM code shamelessly copy-pasted from baeldung
fun findLCMOfListOfNumbers(numbers: List<Long>): Long {
    var result = numbers[0]
    for (i in 1 until numbers.size) {
        result = findLCM(result, numbers[i])
    }
    return result
}

fun findLCM(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}
