package day12

import kotlin.math.abs

class Moon(var xPos: Int, var yPos: Int, var zPos: Int) {
    var xVel: Int = 0
    var yVel: Int = 0
    var zVel: Int = 0

    fun getPotentialEnergy(): Int = abs(xPos) + abs(yPos) + abs(zPos)

    fun getKineticEnergy(): Int = abs(xVel) + abs(yVel) + abs(zVel)

    fun applyGravity(other: Moon) {
        //x-Axis
        applyXGravity(other)

        //y-Axis
        applyYGravity(other)

        //z-Axis
        applyZGravity(other)
    }

    fun applyXGravity(other: Moon) {
        if(other.xPos > this.xPos) {
            other.xVel--
            this.xVel++
        } else if (other.xPos < this.xPos) {
            other.xVel++
            this.xVel--
        }
    }

    fun applyYGravity(other: Moon) {
        if(other.yPos > this.yPos) {
            other.yVel--
            this.yVel++
        } else if (other.yPos < this.yPos) {
            other.yVel++
            this.yVel--
        }
    }

    fun applyZGravity(other: Moon) {
        if(other.zPos > this.zPos) {
            other.zVel--
            this.zVel++
        } else if (other.zPos < this.zPos) {
            other.zVel++
            this.zVel--
        }
    }

    fun step() {
        xPos += xVel
        yPos += yVel
        zPos += zVel
    }

    override fun toString(): String {
        return "Moon(pos=<x= $xPos, y= $yPos, z= $zPos>, vel=<x= $xVel, y= $yVel z= $zVel>)"
    }


}
