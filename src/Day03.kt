import utils.println
import utils.readInput
import kotlin.collections.filter
import kotlin.text.indexOf

fun main() {
    fun solve(input: List<String>, batteries: Int): Long {
        return input.sumOf { line ->
            line.fold(mutableListOf<Char>() to (line.length - batteries)) { (stack, toRemove), digit ->
                var remaining = toRemove
                while (stack.isNotEmpty() && stack.last() < digit && remaining > 0) {
                    stack.removeLast()
                    remaining--
                }
                stack.add(digit)
                stack to remaining
            }.let { (stack, remaining) ->
                stack.dropLast(remaining).joinToString("").toLong()
            }
        }
    }

    val input = readInput("Day03")
    solve(input, 2).println()
    solve(input, 12).println()
}