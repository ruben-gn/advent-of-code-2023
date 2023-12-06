import kotlin.time.measureTime

private val lines = readLines("05-01")
private val seedToSoil = lines.dropWhile { it != "seed-to-soil map:" }.parse()
private val soilToFertilizer = lines.dropWhile { it != "soil-to-fertilizer map:" }.parse()
private val fertilizerToWater = lines.dropWhile { it != "fertilizer-to-water map:" }.parse()
private val waterToLight = lines.dropWhile { it != "water-to-light map:" }.parse()
private val lightToTemperature = lines.dropWhile { it != "light-to-temperature map:" }.parse()
private val temperatureToHumidity = lines.dropWhile { it != "temperature-to-humidity map:" }.parse()
private val humidityToLocation = lines.dropWhile { it != "humidity-to-location map:" }.parse()
private val maps = listOf(
    seedToSoil,
    soilToFertilizer,
    fertilizerToWater,
    waterToLight,
    lightToTemperature,
    temperatureToHumidity,
    humidityToLocation
)

fun main() {
    part1(lines)
    val time = measureTime { part2(lines) }
    println("Part 2 took: $time")
}

private fun part1(lines: List<String>) {
    val seeds = lines.first().split(":").last().split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
    val result = getMinimumLocation(seeds)
    println("Part 1: $result")
}

private fun part2(lines: List<String>) {
    val seeds = lines.first()
        .split(":").last()
        .split(" ")
        .filter { it.isNotEmpty() }
        .windowed(2, 2)
        .map { it.first().toLong()..<it.first().toLong() + it.last().toLong() }

    println(seeds)

    val result = seeds.map {
        it.minOf { seed -> getMinimumLocation(listOf(seed)) }
    }
    println("part 2: ${result.min()}")
}

private fun getMinimumLocation(seeds: List<Long>): Long {
    return seeds.minOf(::getLoc)
}

private fun getLoc(seed: Long) =
    maps.fold(seed) { acc, map ->
        map.firstOrNull { acc >= it[1] && acc <= (it[1] + it[2]) }?.let { acc + it[0] - it[1] } ?: acc
    }

private fun List<String>.parse() = this
    .takeWhile { it.isNotEmpty() }.drop(1)
    .map { it.split(" ") }
    .map { group -> group.map { it.toLong() } }