import java.io.File

fun main() {
    part1()
    part2()
}

var width = 0
var field: MutableList<List<Char>> = mutableListOf()

private fun part2() {
//    val coordNumbers = coordNumbers(readFile())
//    field.mapIndexed { y, line ->
//        line.mapIndexedNotNull { x, char ->
//            if (char == '*') Point(x, y) else null
//        }
//    }.flatten()
//        .filter { it.neighbours(). }
}

private fun part1() = coordNumbers(readFile())
    .filter { coordNumber ->
        val neighbours = coordNumber.points.map { it.neighbours() }.flatten().toSet()
        neighbours.any {
            val char = field[it.y][it.x]
            !char.isDigit() && char != '.'
        }
    }
    .sumOf { it.number.toInt() }
    .let { println("part 1: $it") }

private fun coordNumbers(lines: List<String>): List<CoordNumber> = lines
    .map {
        width = it.length - 1
        field.add(it.split("").filter { it.isNotEmpty() }.map { it.first() })
        it
    }
    .map { it.split("") }
    .mapIndexed { index, strings -> parseLine(strings, index) }
    .flatten()


private fun parseLine(line: List<String>, y: Int): List<CoordNumber> = line
    .filter { it.isNotEmpty() }
    .map { it.first() }
    .foldIndexed(mutableListOf(CoordNumber(mutableListOf(), ""))) { x, acc, char ->
        if (char.isDigit()) {
            acc.last().points.add(Point(x, y))
            acc.last().number += char
        } else {
            acc.add(CoordNumber(mutableListOf(), ""))
        }
        acc
    }.filter { it.points.isNotEmpty() }

private data class Point(val x: Int, val y: Int) {
    fun neighbours(): List<Point> {
        return listOf(x - 1, x, x + 1).map { x ->
            listOf(y - 1, y, y + 1).mapNotNull { y ->
                if (x >= 0 && y >= 0 && x <= width && y <= width) {
                    Point(x, y)
                } else {
                    null
                }
            }
        }.flatten()
    }
}

private data class CoordNumber(val points: MutableList<Point>, var number: String)

private fun readFile() = File("src/main/resources/03.txt").readLines()
