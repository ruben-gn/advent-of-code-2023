private val lines = readLines("10")
    .map { it.split("").notEmpty().toMutableList() }

fun main() {
    timed { part1() }
    timed { part2() }
}

private fun part1() {
    val path = findPath()

    val result = path.size / 2
//    lines.map { line ->
//        line.map {
//            it
//                .replace("7", "┐")
//                .replace("F", "┌")
//                .replace("-", "─")
//                .replace("|", "│")
//                .replace("L", "└")
//                .replace("J", "┘")
//        }
//    }.map { it.joinToString("") }.map(::pr)
    println("part 1: $result")
}

private fun part2() {
    val path = findPath()

    var flip = false
    var count = 0
    lines.indices.forEach { y ->
        lines[y].indices.forEach { x ->
            with(Cell(x, y)) {
                if (this in path && this.connected().toList().any { it == Cell(x, y + 1) })
                    flip = flip.not()
                else if (this !in path && flip)
                    count++
            }
        }
    }
    println("part 2: $count")
}

private fun findPath(): List<Cell> {
    val start = lines.indices.firstNotNullOf { y ->
        when (val x = lines[y].indexOf("S")) {
            -1 -> null
            else -> x to y
        }
    }

    val s = Cell(start.first, start.second)
    val path = mutableListOf(s)
    var next = s.connected().first
    while (next != s) {
        path.add(next)
        next = if (next.connected().first in path && next.connected().second in path) s
        else if (next.connected().first in path) next.connected().second
        else next.connected().first
    }

    return path.toList()
}

private data class Cell(val x: Int, val y: Int) {
    fun connected(): Pair<Cell, Cell> = when (lines[y][x]) {
        "S" -> findConnectedForStart(x, y)
        "-" -> Cell(x - 1, y) to Cell(x + 1, y)
        "7" -> Cell(x - 1, y) to Cell(x, y + 1)
        "|" -> Cell(x, y - 1) to Cell(x, y + 1)
        "J" -> Cell(x - 1, y) to Cell(x, y - 1)
        "L" -> Cell(x + 1, y) to Cell(x, y - 1)
        "F" -> Cell(x + 1, y) to Cell(x, y + 1)
        else -> throw RuntimeException("Invalid Cell value")
    }

    private fun findConnectedForStart(x: Int, y: Int): Pair<Cell, Cell> {
        val cells = mutableListOf<Cell>()
        if (x > 0 && lines[y][x - 1] in listOf("-", "L", "F")) cells.add(Cell(x - 1, y))
        if (x < lines[y].size - 1 && lines[y][x + 1] in listOf("-", "J", "7")) cells.add(Cell(x + 1, y))
        if (y > 0 && lines[y - 1][x] in listOf("|", "7", "F")) cells.add(Cell(x, y - 1))
        if (y < lines.size - 1 && lines[y + 1][x] in listOf("|", "J", "L")) cells.add(Cell(x, y + 1))
        return cells[0] to cells[1]
    }
}
