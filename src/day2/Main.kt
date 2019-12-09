package day2

import java.io.File

fun main() {
    val intCode: List<Int> = File("C:\\Users\\plass2.EAD\\IdeaProjects\\aoc2019\\src\\day2\\input")
        .bufferedReader()
        .readLine()
        .split(",")
        .map { it.toInt() }

    for (noun in 0..99) {
        f@for (verb in 0..99) {
            val iterationIntCode = intCode.toMutableList()

            iterationIntCode[1] = noun
            iterationIntCode[2] = verb

            var pointer = 0
            w@while(true) {
                when(iterationIntCode[pointer]){
                    1 -> iterationIntCode[iterationIntCode[pointer+3]] = iterationIntCode[iterationIntCode[pointer+1]] + iterationIntCode[iterationIntCode[pointer+2]]
                    2 -> iterationIntCode[iterationIntCode[pointer+3]] = iterationIntCode[iterationIntCode[pointer+1]] * iterationIntCode[iterationIntCode[pointer+2]]
                    99 -> break@w
                    else -> continue@f
                }
                pointer += 4
            }
            if (iterationIntCode[0] == 19690720) {
                println("noun: $noun, verb: $verb")
            }
        }
    }
//
//    intCode[1] = 12
//    intCode[2] = 2
//
//    var pointer = 0
//    outer@while(true) {
//        when(intCode[pointer]){
//            1 -> intCode[intCode[pointer+3]] = intCode[intCode[pointer+1]] + intCode[intCode[pointer+2]]
//            2 -> intCode[intCode[pointer+3]] = intCode[intCode[pointer+1]] * intCode[intCode[pointer+2]]
//            99 -> break@outer
//            else -> throw IllegalStateException("Oh no! Something went wrong UwU")
//        }
//        pointer += 4
//    }
//
//    println(intCode[0])
}
