package day7

import com.marcinmoskala.math.permutations
import java.io.File
import java.lang.IllegalStateException

fun main() {
    val phases = setOf(5, 6, 7, 8, 9)
    val phasePermutations = phases.permutations()
    println(
        phasePermutations.map {
            val amps = Array(5) { Computer() }
            try {
                var input = 0
                var i = 0
                while(true) {
                    input = amps[i].compute(it[i], input)
                    i++
                    if(i == 5) i = 0
                }
                0
            } catch (e: Exception) {
                amps[4].lastOutput
            }
        }.max()
    )
}
