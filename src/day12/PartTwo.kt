package day12

import java.io.File

fun main() {
    val input = File("C:\\Users\\plass2.EAD\\IdeaProjects\\aoc2019\\src\\day12\\input")
        .bufferedReader().readLines()

    val moons = input.map {
        val split = it.split(Regex("[^0-9.-]")).filter { it.isNotEmpty() }.map { it.toInt() }
        Moon(split[0], split[1], split[2])
    }

    val pairs = setOf(
        Pair(moons[0], moons[1]),
        Pair(moons[0], moons[2]),
        Pair(moons[0], moons[3]),
        Pair(moons[1], moons[2]),
        Pair(moons[1], moons[3]),
        Pair(moons[2], moons[3])
    )

    val xValuesSet = mutableSetOf<List<Int>>()

    var counter = 0

    while (true) {
        pairs.forEach {
            it.first.applyXGravity(it.second)
        }
        moons.forEach { it.step() }
        counter++
        val xValues = moons.flatMap { listOf(it.xPos, it.xVel) }
        if (xValuesSet.contains(xValues)) {
            println("x Loop found after ${counter-1} steps, has same values as ${xValuesSet.indexOf(xValues)}")
            break
        }
        xValuesSet.add(xValues)
    }

    val yValuesSet = mutableSetOf<List<Int>>()

    counter = 0

    while (true) {
        pairs.forEach {
            it.first.applyYGravity(it.second)
        }
        moons.forEach { it.step() }
        counter++
        val yValues = moons.flatMap { listOf(it.yPos, it.yVel) }
        if (yValuesSet.contains(yValues)) {
            println("y Loop found after ${counter-1} steps, has same values as ${yValuesSet.indexOf(yValues)}")
            break
        }
        yValuesSet.add(yValues)
    }

    val zValuesSet = mutableSetOf<List<Int>>()

    counter = 0

    while (true) {
        pairs.forEach {
            it.first.applyZGravity(it.second)
        }
        moons.forEach { it.step() }
        counter++
        val zValues = moons.flatMap { listOf(it.zPos, it.zVel) }
        if (zValuesSet.contains(zValues)) {
            println("z Loop found after ${counter-1} steps, has same values as ${zValuesSet.indexOf(zValues)}")
            break
        }
        zValuesSet.add(zValues)
    }
    //Solution is LCM of the three loops
}
