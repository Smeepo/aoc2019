package day8

import java.io.File
import java.lang.IllegalStateException

fun main(){


    val input = File("C:\\Users\\plass2.EAD\\IdeaProjects\\aoc2019\\src\\day8\\input").bufferedReader().readLine()
    val inputData = input.toCharArray().map { it.toString().toInt() }

    val width = 25
    val height = 6

    val image = Array (inputData.size / (width * height)) { Array(height) { arrayOfNulls<Int>(width)} }

    var layerIndex = 0
    var widthIndex = 0
    var heightIndex = 0

    inputData.forEach {
        image[layerIndex][heightIndex][widthIndex++] = it
        if (widthIndex == width) {
            widthIndex = 0
            heightIndex++
            if (heightIndex == height) {
                heightIndex = 0
                layerIndex++
            }
        }
    }

    val layerFewestZeros = image.minBy { arrayOfArrays -> arrayOfArrays.sumBy { arrayOfInts -> arrayOfInts.count { it == 0 } } }
    val solution = layerFewestZeros!!.sumBy { arrayOfInts -> arrayOfInts.count { it == 1 } } * layerFewestZeros.sumBy { arrayOfInts -> arrayOfInts.count { it == 2 } }
    println(solution)

    val rendered = Array(height) { arrayOfNulls<Char>(width)}

    fun getPixel(x: Int, y: Int, depth: Int = 0): Char = when(image[depth][y][x]) {
        0 -> '0'
        1 -> '1'
        2 -> getPixel(x, y, depth+1)
        else -> throw IllegalStateException()
    }

    for(x in 0 until width) {
        for (y in 0 until height) {
            rendered[y][x] = getPixel(x, y)
        }
    }

    rendered.forEach {
        it.forEach { print(it) }
        println()
    }
}
