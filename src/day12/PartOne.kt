package day12

import java.io.File

fun main() {
    val input = File("C:\\Users\\plass2.EAD\\IdeaProjects\\aoc2019\\src\\day12\\input")
        .bufferedReader().readLines()

    val moons = input.map {
        val split = it.split(Regex("[^0-9.-]")).filter { it.isNotEmpty() }.map { it.toInt() }
        Moon(split[0], split[1], split[2])
    }

    println(moons)

    val pairs = setOf(
        Pair(moons[0], moons[1]),
        Pair(moons[0], moons[2]),
        Pair(moons[0], moons[3]),
        Pair(moons[1], moons[2]),
        Pair(moons[1], moons[3]),
        Pair(moons[2], moons[3])
    )

    repeat(1000) {
        pairs.forEach {
            it.first.applyGravity(it.second)
        }
        moons.forEach {
            it.step()
        }
        println("After ${it+1} steps:")
        moons.forEach {
            println(it.toString())
        }
    }

    val total = moons.sumBy { it.getKineticEnergy() * it.getPotentialEnergy() }
    println(total)
}
