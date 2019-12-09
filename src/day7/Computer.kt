package day7

import java.io.File

class Computer {

    val intCode: List<Int> = File("C:\\Users\\plass2.EAD\\IdeaProjects\\aoc2019\\src\\day7\\input")
        .bufferedReader()
        .readLine()
        .split(",")
        .map { it.toInt() }

    val iterationIntCode = intCode.toMutableList()

    var pointer = 0

    var lastOutput = 0

    var phaseRead = false

    fun compute(phase: Int, input: Int): Int {
        w@while(true) {
            val current = iterationIntCode[pointer]
            val modes = mutableListOf<Int>()
            val opcode = if (current > 9 ) {
                current.toString()
                    .substring(0, current.toString().length-2)
                    .reversed()
                    .forEach { modes.add(it.toString().toInt()) }
                current.toString().substring(current.toString().length-2).toInt()
            } else {
                current
            }

            val numInstructions = when(opcode) {
                1 -> 4
                2 -> 4
                3 -> 2
                4 -> 2
                5 -> 3
                6 -> 3
                7 -> 4
                8 -> 4
                99 -> 1
                else -> throw IllegalStateException()
            }

            val instructions = mutableListOf<Int>()

            for(i in 1..numInstructions) {
                instructions.add(iterationIntCode[pointer+i])
            }

            var jumped = false

            when(opcode){
                1 -> {
                    val target = instructions[2]
                    val value1 = if(modes.getOrElse(0) {0} == 0) iterationIntCode[instructions[0]] else instructions[0]
                    val value2 = if(modes.getOrElse(1) {0} == 0) iterationIntCode[instructions[1]] else instructions[1]
                    iterationIntCode[target] = value1 + value2
                }
                2 -> {
                    val target = instructions[2]
                    val value1 = if(modes.getOrElse(0) {0} == 0) iterationIntCode[instructions[0]] else instructions[0]
                    val value2 = if(modes.getOrElse(1) {0} == 0) iterationIntCode[instructions[1]] else instructions[1]
                    iterationIntCode[target] = value1 * value2
                }
                3 -> {
                    val target = instructions[0]
                    if(phaseRead) iterationIntCode[target] = input
                    else {
                        iterationIntCode[target] = phase
                        phaseRead = true
                    }
                }
                4 -> {
                    val output = if(modes.getOrElse(0) {0} == 0) iterationIntCode[instructions[0]] else instructions[0]
                    pointer += numInstructions
                    lastOutput = output
                    return output
                }
                5 -> {
                    val condition = if(modes.getOrElse(0) {0} == 0) iterationIntCode[instructions[0]] else instructions[0]
                    val jumpTarget = if(modes.getOrElse(1) {0} == 0) iterationIntCode[instructions[1]] else instructions[1]
                    if (condition != 0) {
                        pointer = jumpTarget
                        jumped = true
                    }
                }
                6 -> {
                    val condition = if(modes.getOrElse(0) {0} == 0) iterationIntCode[instructions[0]] else instructions[0]
                    val jumpTarget = if(modes.getOrElse(1) {0} == 0) iterationIntCode[instructions[1]] else instructions[1]
                    if (condition == 0) {
                        pointer = jumpTarget
                        jumped = true
                    }
                }
                7 -> {
                    val target = instructions[2]
                    val value1 = if(modes.getOrElse(0) {0} == 0) iterationIntCode[instructions[0]] else instructions[0]
                    val value2 = if(modes.getOrElse(1) {0} == 0) iterationIntCode[instructions[1]] else instructions[1]
                    iterationIntCode[target] = if(value1 < value2) 1 else 0
                }
                8 -> {
                    val target = instructions[2]
                    val value1 = if(modes.getOrElse(0) {0} == 0) iterationIntCode[instructions[0]] else instructions[0]
                    val value2 = if(modes.getOrElse(1) {0} == 0) iterationIntCode[instructions[1]] else instructions[1]
                    iterationIntCode[target] = if(value1 == value2) 1 else 0
                }
                99 -> break@w
            }
            if (!jumped) {
                pointer += numInstructions
            }
        }
        throw IllegalStateException()
    }
}
