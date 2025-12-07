import utils.Point
import utils.matrixAsChars
import utils.println
import utils.readInput

fun main() {
    fun part1(input: List<String>): Int {
        val matrix = input.matrixAsChars()
        val start = matrix.keys.first { matrix[it] == 'S' }

        var beams = mutableSetOf(start)
        var current = Point(0, start.y)
        var splitCount = 0

        while (current in matrix) {
            current += Point(0, 1)

            val splitters = beams.filter { matrix[it] == '^' }
            splitCount += splitters.size

            beams.removeAll(splitters.toSet())

            splitters.forEach { p ->
                beams.add(Point(p.x - 1, p.y))
                beams.add(Point(p.x + 1, p.y))
            }

            beams = beams.map { it + Point(0, 1) }.toMutableSet()
        }

        return splitCount
    }

    fun part2(input: List<String>): Long {
        val matrix = input.matrixAsChars()
        val start = matrix.entries.first { it.value == 'S' }.key

        var counts: Map<Point, Long> = mapOf(start to 1L)

        while (counts.keys.any { it in matrix }) {
            val afterSplit = mutableMapOf<Point, Long>()
            for ((pos, cnt) in counts) {
                when (matrix[pos]) {
                    '^' -> {
                        val left = Point(pos.x - 1, pos.y)
                        val right = Point(pos.x + 1, pos.y)
                        afterSplit[left] = afterSplit.getOrDefault(left, 0L) + cnt
                        afterSplit[right] = afterSplit.getOrDefault(right, 0L) + cnt
                    }
                    else -> {
                        afterSplit[pos] = afterSplit.getOrDefault(pos, 0L) + cnt
                    }
                }
            }

            val moved = mutableMapOf<Point, Long>()
            for ((pos, cnt) in afterSplit) {
                val down = pos + Point(0, 1)
                moved[down] = moved.getOrDefault(down, 0L) + cnt
            }

            counts = moved
        }

        return counts.values.sum()
    }

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
