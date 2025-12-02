import utils.println
import utils.readInput
import kotlin.collections.filter

fun main() {
    fun part1(input: List<String>): Long {
        return input.first().split(",").flatMap { range ->
            val (start, end) = range.split("-").map { it.toLong() }
            (start..end).filter {
                val str = it.toString()
                val half = str.length / 2
                str.take(half) == str.drop(half)
            }
        }.sum()
    }

    fun part2(input: List<String>): Long {
        return input.first().split(",").flatMap { range ->
            val (start, end) = range.split("-").map { it.toLong() }
            (start..end).filter {
                val str = it.toString()
                val half = str.length / 2
                (1..half).any { n -> str.chunked(n).toSet().size == 1 }
            }
        }.sum()
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

