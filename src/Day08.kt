import utils.DisjointSet
import utils.Point3D
import utils.println
import utils.readInput
import java.util.PriorityQueue

fun main() {

    fun allPairs(points: List<Point3D>): PriorityQueue<PointPair> =
        PriorityQueue<PointPair>().apply {
            points.forEachIndexed { i, a ->
                for (b in points.drop(i + 1)) {
                    add(PointPair(a, b))
                }
            }
        }

    fun part1(input: List<String>): Int {
        val points = input.map(Point3D::of)
        val pq = allPairs(points)

        val ds = DisjointSet<Point3D>().apply { addAll(points) }

        repeat(1000) {
            pq.poll()?.let { (a, b) -> ds.union(a, b) }
        }

        return ds.setSizes()
            .sortedDescending()
            .take(3)
            .reduce(Int::times)
    }

    fun part2(input: List<String>): Long {
        val points = input.map(Point3D::of)
        val pq = allPairs(points)
        val ds = DisjointSet<Point3D>().apply { addAll(points) }

        return generateSequence(pq.poll()) { (a, b) ->
            ds.union(a, b)
            pq.poll().takeIf { ds.countSets() > 1 }
        }.last().let { (a, b) -> a.x.toLong() * b.x.toLong() }
    }

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}

private data class PointPair(val a: Point3D, val b: Point3D) : Comparable<PointPair> {
    private val distance: Double = a.distance(b)
    override fun compareTo(other: PointPair): Int =
        distance.compareTo(other.distance)
}
