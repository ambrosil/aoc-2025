import utils.parseTwoLists
import utils.readInput

fun main() {

    fun List<LongRange>.merge(): List<LongRange> =
        sortedBy { it.first }.fold(mutableListOf()) { acc, r ->
            when {
                acc.isEmpty() -> acc.add(r)
                r.first <= acc.last().last + 1 ->
                    acc[acc.lastIndex] = acc.last().first..maxOf(acc.last().last, r.last)
                else -> acc.add(r)
            }
            acc
        }

    fun String.toRange(): LongRange =
        split("-").let { (a, b) -> a.toLong()..b.toLong() }

    fun part1(input: List<String>): Int {
        val (rangeLines, values) = input.parseTwoLists()
        val ranges = rangeLines.map { it.toRange() }
        return values.map { it.toLong() }.count { v -> ranges.any { v in it } }
    }

    fun part2(input: List<String>): Long {
        val (rangeLines, _) = input.parseTwoLists()
        val ranges = rangeLines.map { it.toRange() }
        return ranges.merge().sumOf { it.last - it.first + 1 }
    }

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
