import utils.println
import utils.readInput

fun main() {

    fun parseRowNumbers(lines: List<String>) =
        lines.map { it.trim().split(Regex("\\s+")).map(String::toLong) }

    fun List<Long>.applyOp(op: String): Long =
        when (op) {
            "+" -> this.sum()
            "*" -> this.fold(1L) { acc, v -> acc * v }
            else -> error("Invalid operation: $op")
        }

    fun part1(input: List<String>): Long {
        val numbers = parseRowNumbers(input.dropLast(1))
        val ops = input.last().split(Regex("\\s+"))

        return numbers.first().indices.sumOf { col ->
            numbers.map { it[col] }.applyOp(ops[col])
        }
    }

    fun part2(input: List<String>): Long {
        val rows = input.dropLast(1)
        val opRow = input.last()

        val width = input.maxOf { it.length }
        val height = rows.size

        val paddedRows = rows.map { it.padEnd(width, ' ') }
        val paddedOps = opRow.padEnd(width, ' ')

        val colValues = (0 until width).map { c ->
            buildString {
                for (r in 0 until height) append(paddedRows[r][c])
            }.trim()
        }

        val results = mutableListOf<Long>()
        var i = width - 1

        while (i >= 0) {
            if (colValues[i].isBlank()) { i--; continue }

            val block = buildList {
                var j = i
                while (j >= 0 && colValues[j].isNotBlank()) {
                    add(j)
                    j--
                }
                i = j
            }

            val nums = block.map { colValues[it].toLong() }
            val op = block.map { paddedOps[it] }.first { it != ' ' }

            results += nums.applyOp(op.toString())
        }

        return results.sum()
    }

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
