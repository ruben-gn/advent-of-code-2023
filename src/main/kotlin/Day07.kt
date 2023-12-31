private val lines = readLines("07")

fun main() {
    timed { calculate(cardValues) { it } }
    timed { calculate(cardValues2) { it.replaceJokers() } }
}

private fun calculate(valueMap: Map<String, Int>, transform: (List<String>) -> List<String>) =
    lines.asSequence().map { it.split(" ") }
        .map { Hand(it[0].split("").filter { card -> card.isNotEmpty() }, it[1].toLong()) }
        .sortedWith(compareBy({transform(it.cards).rank()}, { it.number(valueMap) }))
        .mapIndexed { index, hand -> hand.bid * (index + 1) }.sum()
        .let { println("part 2: $it") }

private fun List<String>.rank(): Int =
    types.first { type -> type.predicate(this) }.strength

private fun List<String>.replaceJokers() = pretendJokerIs(this.findMostCommon())
private fun List<String>.pretendJokerIs(card: String) = this.map { if (it == "J") card else it }
private fun List<String>.findMostCommon(): String = filter { it != "J" }
    .groupingBy { it }.eachCount()
    .maxByOrNull { it.value }?.key ?: first()

private data class Hand(
    val cards: List<String>,
    val bid: Long
) {
    fun number(vals: Map<String, Int>) = cards.map { vals[it] ?: 0 }.joinToString("").toLong()
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
private val cardValues2 = mapOf(
    "2" to 11,
    "3" to 12,
    "4" to 13,
    "5" to 14,
    "6" to 15,
    "7" to 16,
    "8" to 17,
    "9" to 18,
    "T" to 19,
    "J" to 10, // here
    "Q" to 21,
    "K" to 22,
    "A" to 23
)
