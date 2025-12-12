import utils.readInput

fun main() {
    val regex = """(\d+)x(\d+): (.+)""".toRegex()

    val part1 = readInput("Day12").count { line ->
        regex.matchEntire(line)?.destructured?.let { (w, h, uses) ->
            w.toInt() * h.toInt() >= uses.split(" ").sumOf { it.toInt() } * 9
        } ?: false
    }

    println(part1)
}
