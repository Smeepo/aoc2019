package day3

import java.io.File
import kotlin.math.abs

fun main(){
    val lines = File("C:\\Users\\plass2.EAD\\IdeaProjects\\aoc2019\\src\\day3\\input").bufferedReader().readLines()
    val line1 = lines[0]
    val line2 = lines[1]

    val path1 = line1.split(",").map { Pair(it.substring(0, 1), it.substring(1).toInt()) }
    val path2 = line2.split(",").map { Pair(it.substring(0, 1), it.substring(1).toInt()) }

    var curX = 0
    var curY = 0

    val circuitBoard = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()

    var path1Steps = 0

    path1.forEach {
        val (xDir, yDir) = when (it.first) {
            "R" -> Pair(1, 0)
            "L" -> Pair(-1, 0)
            "U" -> Pair(0, 1)
            "D" -> Pair(0, -1)
            else -> throw IllegalStateException()
        }

        val vertical = xDir == 0

        val targetX = curX + it.second * xDir
        val targetY = curY + it.second * yDir

        val segment = if (vertical) {
            if (targetY > curY) curY+1..targetY
            else curY-1 downTo targetY
        } else {
            if (targetX > curX) curX+1..targetX
            else curX-1 downTo targetX
        }

        for (i in segment) {
            val point = if (vertical) Pair(curX, i) else Pair(i, curY)
            if (circuitBoard.contains(point)) ++path1Steps
            else circuitBoard[point] = Pair(1, ++path1Steps)
        }
        curX = targetX
        curY = targetY
    }

    val end = Pair(0, 0)
    curX = 0
    curY = 0

    var path2Steps = 0

    path2.forEach {
        val (xDir, yDir) = when(it.first){
            "R" -> Pair(1, 0)
            "L" -> Pair(-1, 0)
            "U" -> Pair(0, 1)
            "D" -> Pair(0, -1)
            else -> throw IllegalStateException()
        }

        val vertical = xDir == 0

        val targetX = curX + it.second * xDir
        val targetY = curY + it.second * yDir

        val segment = if (vertical) {
            if (targetY > curY) curY+1..targetY
            else curY-1 downTo targetY
        } else {
            if (targetX > curX) curX+1..targetX
            else curX-1 downTo targetX
        }

        for (i in segment) {
            val point = if (vertical) Pair(curX, i) else Pair(i, curY)
            if (circuitBoard.contains(point)){
                val existingPoint = circuitBoard[point]!!
                if (existingPoint.first == 2) ++path2Steps
                else circuitBoard[point] = Pair(existingPoint.first or 2, circuitBoard[point]!!.second + ++path2Steps)
            }
            else circuitBoard[point] = Pair(2, ++path2Steps)
        }
        curX = targetX
        curY = targetY
    }

    circuitBoard.remove(Pair(0,0))

    val intersections = circuitBoard.filter { it.value.first == 3 }.map { Pair(it.key, it.value.second) }

    println("end: $end")
    println("intersections: $intersections")

//    val closest = intersections.minBy { abs(it.first - end.first) + abs(it.second - end.second) }
//    println("closest: $closest")
//    println("distance: ${abs(closest!!.first - end.first) + abs(closest.second - end.second)}")

    val leastSteps = intersections.minBy { it.second }!!.second
    println(leastSteps)

}
