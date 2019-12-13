package day11

import day11.HullColor.BLACK
import day11.HullColor.WHITE
import java.lang.IllegalArgumentException

class Painter(var posX: Int, var posY: Int) {
    val hull = mutableMapOf<Pair<Int,Int>, HullColor>()
    var facing: Pair<Int, Int> = Pair(0, -1)

    fun rotateAndMove(direction: Int) {
        if (direction > 1 || direction < 0) throw IllegalArgumentException()
        val xDir = if (facing.first == 0) (1 - 2 * direction) * facing.second else 0
        val yDir = if (facing.second == 0) (1 - 2 * direction) * -facing.first else 0
        facing = Pair(xDir, yDir)
        move()
    }

    private fun move() {
        posX += facing.first
        posY += facing.second
    }

    fun getColor() = when (hull.getOrDefault(Pair(posX, posY), BLACK)) {
        BLACK -> 0
        WHITE -> 1
    }

    fun paint(colorCode: Int) {
        val color = when (colorCode) {
            0 -> BLACK
            1 -> WHITE
            else -> throw IllegalArgumentException()
        }
        hull[Pair(posX, posY)] = color
    }

    fun printHull() {
        val xOffset = hull.keys.map { it.first }.min()!!
        val yOffset = hull.keys.map { it.second }.min()!!

        val xSize = hull.keys.map { it.first }.max()!! - xOffset
        val ySize = hull.keys.map { it.second }.max()!! - yOffset

        val printable = Array(ySize+1) { y -> Array(xSize) { x -> hull.getOrDefault(Pair(x+xOffset, y+yOffset), BLACK) } }

        val builder = StringBuilder()
        for (row in printable) {
            for (cell in row) {
                builder.append(
                    if (cell == BLACK) " "
                    else "█"
                )
            }
            builder.append("\n")
        }
        println(builder.toString())
    }

}

fun main() {
    val robot = Painter(0, 0)
    assert(robot.facing == Pair(0, -1))
    robot.rotateAndMove(1)
    assert(robot.facing == Pair(1, 0))
    robot.rotateAndMove(1)
    assert(robot.facing == Pair(0, 1))
    robot.rotateAndMove(1)
    assert(robot.facing == Pair(-1, 0))
    robot.rotateAndMove(1)
    assert(robot.facing == Pair(0, -1))
    robot.rotateAndMove(0)
    assert(robot.facing == Pair(-1, 0))
    robot.rotateAndMove(0)
    assert(robot.facing == Pair(0, 1))
    robot.rotateAndMove(0)
    assert(robot.facing == Pair(1, 0))
    robot.rotateAndMove(0)
    assert(robot.facing == Pair(0, -1))
}

/*
 *  UP: 0, -1
 *  RIGHT: 1, 0
 *  DOWN: 0, 1
 *  LEFT: -1, 0
 */
