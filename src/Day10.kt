import com.microsoft.z3.Context
import com.microsoft.z3.Status
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
        return input.sumOf { it ->
            val tokens = it.split(" ")
            val target = tokens.first().substringAfter("[").substringBefore("]").replace(".", "0").replace("#", "1").reversed().toInt(2)
            val buttons = tokens.drop(1).dropLast(1).map { t -> t.substringAfter("(").substringBefore(")").split(",") }

            combinations(buttons).filterNot { it.isEmpty() }.filter { c ->
                c.flatten().map { s -> 2.toDouble().pow(s.toInt()).toInt() }.reduce { acc, n -> acc.xor(n) } == target
            }.minOf { it.size }
        }
    }

    fun part2(input: List<String>): Int {
        fun String.dropBrackets() = this.replace(Regex("[\\[\\](){}]"), "")

        return input.map { it.split(" ").filterNot { it.isEmpty() }.drop(1) }.sumOf { line ->
            val target = line.last().dropBrackets().split(',').map(String::toInt)
            val buttons = line.dropLast(1).map {
                it.dropBrackets().split(',').map(String::toInt)
            }
            Context().use { ctx ->
                val opt = ctx.mkOptimize()
                val vars = buttons.indices.map { ctx.mkIntConst("b$it") }
                vars.forEach { opt.Add(ctx.mkGe(it, ctx.mkInt(0))) }
                target.indices.forEach { i ->
                    val terms = buttons.withIndex().filter { i in it.value }.map { vars[it.index] }
                    if (terms.isNotEmpty()) {
                        val sum = if (terms.size == 1) terms[0]
                        else ctx.mkAdd(*terms.toTypedArray())
                        opt.Add(ctx.mkEq(sum, ctx.mkInt(target[i])))
                    } else if (target[i] != 0) error("error")
                }
                opt.MkMinimize(ctx.mkAdd(*vars.toTypedArray()))
                if (opt.Check() == Status.SATISFIABLE) {
                    vars.sumOf { opt.model.evaluate(it, false).toString().toInt() }
                } else 0
            }
        }
    }

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
