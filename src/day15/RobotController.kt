package day15

import kotlin.system.exitProcess

object RobotController {
    private var stop: Boolean = false
    var roboPosition = 0 to 0
    var hull = mutableMapOf<Pair<Int, Int>, Field>()
    var lastMove: Direction = Direction.NORTH
    var target = 0 to 0
    var visits = 0

    fun init() {
        hull[roboPosition] = Field(FieldState.START, visits++)
    }

    fun nextMove(): Int {
        var moveTo = adjacent().minBy { it.value.first.visited }
        lastMove = moveTo!!.key
        target = moveTo.value.second
        println("Recommending Move: ${moveTo.key}")
        return moveTo.key.code
    }

    fun handleResult(result: Long) {
        println("Move Result: $result")
        when(result){
            0L -> {
                hull[target] = Field(FieldState.BLOCKED, Int.MAX_VALUE)
            }
            1L -> {

                hull[target] = if(hull.getOrDefault(target, Field(FieldState.UNKNOWN, 0)).state == FieldState.START){
                    Field(FieldState.START, visits++)
                } else Field(FieldState.FREE, visits++)
                roboPosition = target
            }
            2L -> {
                hull[target] = Field(FieldState.OXYGEN, visits++)
                roboPosition = target
                hull[0 to 0] = Field(FieldState.START, 0)
                renderMap()
                println(visits)
                if (stop) {
                    simulate()
                    exitProcess(0)
                } else stop = true
            }
        }
    }

    private fun simulate() {
        var minutes = 0
        while(hull.count { it.value.state == FieldState.FREE } > 0) {
            minutes++
            hull.filter { it.value.state == FieldState.OXYGEN }
                .forEach {
                roboPosition = it.key
                    adjacent().forEach {
                        if(it.value.first.state == FieldState.FREE) {
                            hull[it.value.second] = Field(FieldState.OXYGEN, 0)
                        }
                    }
            }
        }
        println(minutes)

    }

    fun renderMap() {
        val minX = hull.entries.minBy { it.key.first }!!.key.first
        val maxX = hull.entries.maxBy { it.key.first }!!.key.first
        val minY = hull.entries.minBy { it.key.second }!!.key.second
        val maxY = hull.entries.maxBy { it.key.second }!!.key.second

        val outputBuilders = Array(maxY-minY+1) { StringBuilder() }
        for (rowI in minY..maxY) {
            val normal = rowI - minY
            outputBuilders[normal].apply {
                for (colI in minX..maxX) {
                    val drawable = hull.getOrDefault(colI to rowI, Field(FieldState.UNKNOWN, 0)).state.drawable
                    append(drawable)
                }
            }
        }
        outputBuilders.forEach {
            println(it.toString())
        }
    }

    fun adjacent(): Map<Direction, Pair<Field, Pair<Int, Int>>>{
        val map = mutableMapOf<Direction, Pair<Field, Pair<Int, Int>>>()
        var pos = roboPosition.first - 1 to roboPosition.second
        map[Direction.WEST] = hull.getOrDefault(pos, Field(FieldState.UNKNOWN, 0)) to pos
        pos = roboPosition.first to roboPosition.second - 1
        map[Direction.NORTH] = hull.getOrDefault(pos, Field(FieldState.UNKNOWN, 0)) to pos
        pos = roboPosition.first + 1 to roboPosition.second
        map[Direction.EAST] = hull.getOrDefault(pos, Field(FieldState.UNKNOWN, 0)) to pos
        pos = roboPosition.first to roboPosition.second + 1
        map[Direction.SOUTH] = hull.getOrDefault(pos, Field(FieldState.UNKNOWN, 0)) to pos
        return map.toMap()
    }
}