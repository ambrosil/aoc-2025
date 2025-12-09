import utils.Point
import utils.Rectangle
import utils.println
import utils.readInput
import kotlin.math.abs

fun main() {

    data class Punto2D(val x: Double, val y: Double)

    fun area(a: Punto2D, b: Punto2D): Double {
        val width = abs(a.x - b.x) + 1
        val height = abs(a.y - b.y) + 1
        return width * height
    }

    fun part1(input: List<String>): Long {
        val points = input.map { line ->
            val (x, y) = line.split(",")
            Punto2D(x.toDouble(), y.toDouble())
        }

        return points.maxOf { p1 ->
            points.maxOf { p2 ->
                area(p1, p2)
            }
        }.toLong()
    }

    fun part2(input: List<String>): Long {
        val points = input.map(Point::of)
        val perimetro = (points + points.first())
            .zipWithNext()
            .map { (a, b) -> Rectangle.of(a, b) }

        val rectangles = points.flatMapIndexed { index, left ->
            points.drop(index + 1).map { right ->
                Rectangle.of(left, right)
            }
        }.sortedByDescending { it.area }

        return rectangles.first { rect ->
            val inner = rect.inner()
            perimetro.none { line -> line.overlaps(inner) }
        }.area
    }

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
