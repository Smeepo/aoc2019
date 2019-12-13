package day11

import java.io.File

fun main() {
    val intCode: List<Long> = File("C:\\Users\\plass2.EAD\\IdeaProjects\\aoc2019\\src\\day11\\input")
        .bufferedReader()
        .readLine()
        .split(",")
        .map { it.toLong() }

    val iterationIntCode: MutableMap<Int, Long> = intCode.withIndex().associate { (index, value) -> Pair(index, value)}.toMutableMap()

    var pointer = 0
    var relativeBase = 0

    val painter = Painter(0,0)
    var paintInput = true;
    painter.hull[Pair(0,0)] = HullColor.WHITE

    w@while(true) {
        val current = iterationIntCode[pointer]!!
        val modes = mutableListOf<Int>()
        val opcode = if (current > 9 ) {
            current.toString()
                .substring(0, current.toString().length-2)
                .reversed()
                .forEach { modes.add(it.toString().toInt()) }
            current.toString().substring(current.toString().length-2).toLong()
        } else {
            current
        }

        val numInstructions = when(opcode.toInt()) {
            1 -> 4
            2 -> 4
            3 -> 2
            4 -> 2
            5 -> 3
            6 -> 3
            7 -> 4
            8 -> 4
            9 -> 2
            99 -> 1
            else -> throw IllegalStateException()
        }

        val instructions = mutableListOf<Long>()

        for(i in 1..numInstructions) {
            instructions.add(iterationIntCode.getOrElse(pointer+i) {0L} )
        }

        var jumped = false

        when(opcode.toInt()){
            1 -> {
                val target = if(modes.getOrElse(2) {0} == 0) instructions[2].toInt() else instructions[2].toInt() + relativeBase
                val value1 = if(modes.getOrElse(0) {0} == 0) iterationIntCode.getOrElse(instructions[0].toInt()) {0L} else if(modes[0] == 2) iterationIntCode.getOrElse((instructions[0]+relativeBase).toInt()) {0L} else instructions[0]
                val value2 = if(modes.getOrElse(1) {0} == 0) iterationIntCode.getOrElse(instructions[1].toInt()) {0L} else if(modes[1] == 2) iterationIntCode.getOrElse((instructions[1]+relativeBase).toInt()) {0L} else instructions[1]
                iterationIntCode[target] = value1 + value2
            }
            2 -> {
                val target = if(modes.getOrElse(2) {0} == 0) instructions[2].toInt() else instructions[2].toInt() + relativeBase
                val value1 = if(modes.getOrElse(0) {0} == 0) iterationIntCode.getOrElse(instructions[0].toInt()) {0L} else if(modes[0] == 2) iterationIntCode.getOrElse((instructions[0]+relativeBase).toInt()) {0L} else instructions[0]
                val value2 = if(modes.getOrElse(1) {0} == 0) iterationIntCode.getOrElse(instructions[1].toInt()) {0L} else if(modes[1] == 2) iterationIntCode.getOrElse((instructions[1]+relativeBase).toInt()) {0L} else instructions[1]
                iterationIntCode[target] = value1 * value2
            }
            3 -> {
                val target = if(modes.getOrElse(0) {0} == 0) instructions[0].toInt() else instructions[0].toInt() + relativeBase
                val input = painter.getColor().toLong()
                iterationIntCode[target] = input
            }
            4 -> {
                val output = if(modes.getOrElse(0) {0} == 0) iterationIntCode.getOrElse(instructions[0].toInt()) {0L} else if(modes[0] == 2) iterationIntCode.getOrElse((instructions[0]+relativeBase).toInt()) {0L} else instructions[0]
                if (paintInput) {
                    painter.paint(output.toInt())
                } else {
                    painter.rotateAndMove(output.toInt())
                }
                paintInput = !paintInput
            }
            5 -> {
                val condition = if(modes.getOrElse(0) {0} == 0) iterationIntCode.getOrElse(instructions[0].toInt()) {0L} else if(modes[0] == 2) iterationIntCode.getOrElse((instructions[0]+relativeBase).toInt()) {0L} else instructions[0]
                val jumpTarget = if(modes.getOrElse(1) {0} == 0) iterationIntCode.getOrElse(instructions[1].toInt()) {0L} else if(modes[1] == 2) iterationIntCode.getOrElse((instructions[1]+relativeBase).toInt()) {0L} else instructions[1]
                if (condition != 0L) {
                    pointer = jumpTarget.toInt()
                    jumped = true
                }
            }
            6 -> {
                val condition = if(modes.getOrElse(0) {0} == 0) iterationIntCode.getOrElse(instructions[0].toInt()) {0L} else if(modes[0] == 2) iterationIntCode.getOrElse((instructions[0]+relativeBase).toInt()) {0L} else instructions[0]
                val jumpTarget = if(modes.getOrElse(1) {0} == 0) iterationIntCode.getOrElse(instructions[1].toInt()) {0L} else if(modes[1] == 2) iterationIntCode.getOrElse((instructions[1]+relativeBase).toInt()) {0L} else instructions[1]
                if (condition == 0L) {
                    pointer = jumpTarget.toInt()
                    jumped = true
                }
            }
            7 -> {
                val target = if(modes.getOrElse(2) {0} == 0) instructions[2].toInt() else instructions[2].toInt() + relativeBase
                val value1 = if(modes.getOrElse(0) {0} == 0) iterationIntCode.getOrElse(instructions[0].toInt()) {0L} else if(modes[0] == 2) iterationIntCode.getOrElse((instructions[0]+relativeBase).toInt()) {0L} else instructions[0]
                val value2 = if(modes.getOrElse(1) {0} == 0) iterationIntCode.getOrElse(instructions[1].toInt()) {0L} else if(modes[1] == 2) iterationIntCode.getOrElse((instructions[1]+relativeBase).toInt()) {0L} else instructions[1]
                iterationIntCode[target] = if(value1 < value2) 1L else 0L
            }
            8 -> {
                val target = if(modes.getOrElse(2) {0} == 0) instructions[2].toInt() else instructions[2].toInt() + relativeBase
                val value1 = if(modes.getOrElse(0) {0} == 0) iterationIntCode.getOrElse(instructions[0].toInt()) {0L} else if(modes[0] == 2) iterationIntCode.getOrElse((instructions[0]+relativeBase).toInt()) {0L} else instructions[0]
                val value2 = if(modes.getOrElse(1) {0} == 0) iterationIntCode.getOrElse(instructions[1].toInt()) {0L} else if(modes[1] == 2) iterationIntCode.getOrElse((instructions[1]+relativeBase).toInt()) {0L} else instructions[1]
                iterationIntCode[target] = if(value1 == value2) 1L else 0L
            }
            9 -> {
                val value = if(modes.getOrElse(0) {0} == 0) iterationIntCode.getOrElse(instructions[0].toInt()) {0L} else if(modes[0] == 2) iterationIntCode.getOrElse((instructions[0]+relativeBase).toInt()) {0L} else instructions[0]
                relativeBase += value.toInt()
            }
            99 -> break@w
        }
        if (!jumped) {
            pointer += numInstructions
        }
    }
    println(painter.hull.size)
    painter.printHull()
}



