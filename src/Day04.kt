import utils.Point
import utils.matrixAsChars
import utils.println
import utils.readInput

fun main() {
    fun part1(input: List<String>): Int {
        val matrix = input.matrixAsChars()
        return matrix.count { (position, char) ->
            char == '@' && position.getAdjacent().count { matrix[it] == '@' } < 4
        }
    }

    fun part2(input: List<String>): Int {
        val matrix = input.matrixAsChars()
        val removed = mutableSetOf<Point>()

        generateSequence {
            matrix.keys.filter {
                it !in removed && matrix[it] == '@' &&
                it.getAdjacent().count { adj -> matrix[adj] == '@' && adj !in removed } < 4
            }.takeIf { it.isNotEmpty() }
        }.forEach { removed += it }

        return removed.size
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
