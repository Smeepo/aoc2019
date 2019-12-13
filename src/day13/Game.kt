package day13

import day13.Tile.*

class Game {
    val gameMap = mutableMapOf<Pair<Long, Long>, Tile>()
    var ballPosition = 0L
    var paddlePosition = 0L

    fun drawTile(x: Long, y: Long, id: Int) {
        if ( x == -1L && y == 0L ) println("Score: $id")
        else {
            gameMap[Pair(x,y)] = values()[id]
            if (id == 3) paddlePosition = x
            if (id == 4) ballPosition = x
        }
    }

    fun printScreen() {
        val height = gameMap.keys.map { it.second }.max()!!
        val width = gameMap.keys.map { it.first }.max()!!

        val screen = Array(height.toInt()+1) {
                y -> Array(width.toInt()+1) {
                    x -> gameMap.getOrDefault(Pair(x.toLong(), y.toLong()), EMPTY)
                }.map { when(it) {
            EMPTY -> " "
            WALL -> "█"
            BLOCK -> "|"
            HORIZONTAL_PADDLE -> "_"
            BALL -> "o"
        } }
        }

        val builder = StringBuilder()
        screen.forEach { row ->
            row.forEach { cell -> builder.append(cell) }
            builder.append(System.lineSeparator())
        }
        println(builder.toString())
    }

    fun getOptimalInput() = if (paddlePosition > ballPosition) -1 else if(paddlePosition < ballPosition) 1 else 0
}

enum class Tile(id: Int) {
    EMPTY(0), WALL(1), BLOCK(2), HORIZONTAL_PADDLE(3), BALL(4)
}
