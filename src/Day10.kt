import utils.println
import utils.readInput
import kotlin.math.pow

fun main() {
    fun <T> combinations(list: List<T>): List<List<T>> {
        val result = mutableListOf<List<T>>()

        fun backtrack(start: Int, current: MutableList<T>) {
            result.add(current.toList())

            for (i in start until list.size) {
                current.add(list[i])
                backtrack(i + 1, current)
                current.removeAt(current.size - 1)
            }
        }

        backtrack(0, mutableListOf())
        return result
    }

    fun part1(input: List<String>): Int {
        return input.map { it ->
            val tokens = it.split(" ")
            val target = tokens.first().substringAfter("[").substringBefore("]").replace(".", "0").replace("#", "1").reversed().toInt(2)
            val buttons = tokens.drop(1).dropLast(1).map { t -> t.substringAfter("(").substringBefore(")").split(",") }

            combinations(buttons).filterNot { it.isEmpty() }.filter { c ->
                c.flatten().map { s -> 2.toDouble().pow(s.toInt()).toInt() }.reduce { acc, n -> acc.xor(n) } == target
            }.minOf { it.size }
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.map { it ->
            val tokens = it.split(" ")
            val target = tokens.first().substringAfter("[").substringBefore("]").replace(".", "0").replace("#", "1").reversed().toInt(2)
            val buttons = tokens.drop(1).dropLast(1).map { t -> t.substringAfter("(").substringBefore(")").split(",") }
            val jolt = tokens.last().substringAfter("{").substringBefore("}").split(",")

            0
        }.sum()
    }

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
