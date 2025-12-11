import utils.println
import utils.readInput

fun main() {

    fun Map<String, List<String>>.dfs(
        source: String,
        target: String,
        memory: MutableMap<String, Long> = mutableMapOf()
    ): Long =
        if (source == target) 1L
        else memory.getOrPut(source) {
            this[source]?.sumOf { next ->
                dfs(next, target, memory)
            } ?: 0
        }

    fun parse(input: List<String>): Map<String, List<String>> = input.associate {
        it.substringBefore(":") to it.substringAfter(" ").split(" ")
    }

    fun part1(input: List<String>) = parse(input).dfs("you", "out")

    fun part2(input: List<String>) = parse(input).run {
        (dfs("svr", "dac") * dfs("dac", "fft") * dfs("fft", "out")) +
                (dfs("svr", "fft") * dfs("fft", "dac") * dfs("dac", "out"))
    }

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}