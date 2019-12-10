package day10

import java.awt.geom.Line2D
import java.io.File
import kotlin.math.abs
import kotlin.math.atan2

fun main() {
    val input = File("C:\\Users\\plass2.EAD\\IdeaProjects\\aoc2019\\src\\day10\\input").bufferedReader().readLines()
    val asteroidGrid = Array(input.size) { x-> Array(input.first().length) { y-> input[x][y]} }

    val asteroids = input.withIndex().flatMap { string -> string.value.withIndex().filter { it.value == '#' }.map { Point(it.index, string.index) } }

    val best = asteroids.map { Pair(it, countObservable(it, asteroids)) }.maxBy { it.second }
    println(best)

    val asteroidsToVaporize = asteroids.toMutableList()
    asteroidsToVaporize.remove(best!!.first)

    val vaporized = mutableListOf<Point>()

    while (asteroidsToVaporize.isNotEmpty()) {
        val station = best.first
        val observableAsteroids = getObservables(station, asteroidsToVaporize)
        val verticalLinePoint1 = Point(station.x, station.y)
        val verticalLinePoint2 = Point(station.x, 0)
        val inVaporizationOrder = observableAsteroids.sortedBy { angleBetween2Lines(verticalLinePoint1, verticalLinePoint2, verticalLinePoint1, Point(it.x, it.y)) }
        inVaporizationOrder.forEach {
            asteroidsToVaporize.remove(it)
            vaporized.add(it)
        }
    }

    vaporized.withIndex().forEach(::println)
}

fun pointOnLine(point1: Point, point2: Point, currPoint: Point): Boolean {
    val dxc = currPoint.x - point1.x
    val dyc = currPoint.y - point1.y

    val dxl = point2.x - point1.x
    val dyl = point2.y - point1.y

    val cross = dxc * dyl - dyc * dxl
    if (cross != 0) return false

    return if (abs(dxl) >= abs(dyl))
        if (dxl > 0) point1.x <= currPoint.x && currPoint.x <= point2.x
        else point2.x <= currPoint.x && currPoint.x <= point1.x
    else
        if (dyl > 0) point1.y <= currPoint.y && currPoint.y <= point2.y
        else point2.y <= currPoint.y && currPoint.y <= point1.y
}

fun countObservable(station: Point, asteroids: List<Point>): Int {
    val cleanedAsteroidList = asteroids.toMutableList()
    cleanedAsteroidList.remove(station)

    val observable = cleanedAsteroidList.count { toObserve ->
        val withoutCurrent = cleanedAsteroidList.toList().toMutableList()
        withoutCurrent.remove(toObserve)
        withoutCurrent.none { blocker -> pointOnLine(station, toObserve, blocker) }
    }

    return observable
}

fun getObservables(station: Point, asteroids: List<Point>): List<Point> {
    val cleanedAsteroidList = asteroids.toMutableList()
    cleanedAsteroidList.remove(station)

    val observable = cleanedAsteroidList.filter { toObserve ->
        val withoutCurrent = cleanedAsteroidList.toList().toMutableList()
        withoutCurrent.remove(toObserve)
        withoutCurrent.none { blocker -> pointOnLine(station, toObserve, blocker) }
    }

    return observable
}

fun angleBetween2Lines(line1: Line, line2: Line): Double {

    val angle1 = atan2(
        (line1.y2 - line1.y1).toDouble(),
        (line1.x2 - line1.x1).toDouble()
    )
    val angle2 = atan2(
        (line2.y2 - line2.y1).toDouble(),
        (line2.x2 - line2.x1).toDouble()
    )
    return angle1 - angle2
}


/**
 * Calculate angle between two lines with two given points
 *
 * @param A1 First point first line
 * @param A2 Second point first line
 * @param B1 First point second line
 * @param B2 Second point second line
 * @return Angle between two lines in degrees
 */

fun angleBetween2Lines(A1: Point, A2: Point, B1: Point, B2: Point): Float {
    val angle1 = atan2((A2.y - A1.y).toDouble(), (A1.x - A2.x).toDouble()).toFloat()
    val angle2 = atan2((B2.y - B1.y).toDouble(), (B1.x - B2.x).toDouble()).toFloat()
    var calculatedAngle = Math.toDegrees((angle1 - angle2).toDouble()).toFloat()
    if (calculatedAngle < 0) calculatedAngle += 360f
    return calculatedAngle
}
