package day1

import java.io.File
import kotlin.math.floor
import kotlin.math.roundToInt

fun getFuelRequired(moduleMass: Int): Int {
    val fuelRequired = (floor(moduleMass.toDouble() / 3) - 2).roundToInt()
    if (fuelRequired < 1) return 0
    return fuelRequired + getFuelRequired(fuelRequired)
}

fun main() {
    val input: List<String> = File("C:\\Users\\plass2.EAD\\IdeaProjects\\aoc2019\\src\\day1\\input").bufferedReader().readLines()
    val sum = input.parallelStream()
        .mapToInt { it.toInt() }
        .map { getFuelRequired(it) }
        .sum()
    print(sum)
}
