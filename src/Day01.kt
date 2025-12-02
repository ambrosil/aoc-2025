import utils.println
import utils.readInput

fun main() {
    fun part1(input: List<String>): Int {
        var start = 50
        return input.count { line ->
            val dir = line.first()
            val n = line.drop(1).toInt()

            start = when (dir) {
                'L' -> (start - n).mod(100)
                'R' -> (start + n).mod(100)
                else -> start
            }

            start == 0
        }
    }

    fun part2(input: List<String>): Int {
        var start = 50
        var counter = 0

        input.forEach { line ->
            val dir = line.first()
            val n = line.drop(1).toInt()

            counter += when (dir) {
                'L' -> (start - 1).floorDiv(100) - (start - n - 1).floorDiv(100)
                'R' -> (start + n) / 100
                else -> 0
            }

            start = when (dir) {
                'L' -> (start - n).mod(100)
                'R' -> (start + n).mod(100)
                else -> start
            }
        }

        return counter
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
