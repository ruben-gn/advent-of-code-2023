private val lines = readLines("07")

fun main() {
    timed { part1() }
}

private fun part1() = lines.asSequence().map { it.split(" ") }
    .map { Hand(it[0].split("").filter { card -> card.isNotEmpty() }, it[1].toLong()) }
    .sortedWith(compareBy({ it.cards.rank() }, { it.number() }))
    .mapIndexed { index, hand -> hand.bid * (index + 1) }
    .sum()
    .let { println("part 1: $it") }

private fun List<String>.rank(): Int =
    types.first { it.predicate(this) }.strength

private data class Hand(
    val cards: List<String>,
    val bid: Long
) {
    fun number() = cards.map { cardValues[it] ?: 0 }.joinToString("").toLong()
}

private data class Type(
    val predicate: (List<String>) -> Boolean,
    val strength: Int
)

private val fiveOfAKind = Type(
    predicate = { hand -> hand.groupBy { it }.any { it.value.size == 5 } },
    strength = 7
)
private val fourOfAKind = Type(
    predicate = { hand -> hand.groupBy { it }.any { it.value.size == 4 } },
    strength = 6
)
private val fullHouse = Type(
    predicate = { hand -> with(hand.groupBy { it }) { any { it.value.size == 3 } && any { it.value.size == 2 } } },
    strength = 5
)
private val threeOfAKind = Type(
    predicate = { hand -> hand.groupBy { it }.any { it.value.size == 3 } },
    strength = 4
)
private val twoPairs = Type(
    predicate = { hand -> hand.groupBy { it }.count { it.value.size == 2 } == 2 },
    strength = 3
)
private val onePair = Type(
    predicate = { hand -> hand.groupBy { it }.any { it.value.size == 2 } },
    strength = 2
)
private val highCard = Type(
    predicate = { true },
    strength = 1
)

private val types = listOf(fiveOfAKind, fourOfAKind, fullHouse, threeOfAKind, twoPairs, onePair, highCard)

private val cardValues = mapOf(
    "2" to 11,
    "3" to 12,
    "4" to 13,
    "5" to 14,
    "6" to 15,
    "7" to 16,
    "8" to 17,
    "9" to 18,
    "T" to 19,
    "J" to 20,
    "Q" to 21,
    "K" to 22,
    "A" to 23
)
