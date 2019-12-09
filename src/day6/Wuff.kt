package day6

import java.io.File

fun main() {
    val input = File("C:\\Users\\plass2.EAD\\IdeaProjects\\aoc2019\\src\\day6\\input").bufferedReader().readLines()
    //Maps names to Bodys for easier access
    val bodyMap: MutableMap<String, Body> = mutableMapOf()

    input.forEach {
        val split = it.split(")")
        val parentName = split[0]
        val childName = split[1]

        val parentBody = bodyMap.getOrDefault(parentName, Body(null, parentName))
        val childBody = bodyMap.getOrDefault(childName, Body(null, childName))

        parentBody.children.add(childBody)
        childBody.parent = parentBody

        bodyMap[parentName] = parentBody
        bodyMap[childName] = childBody
    }
    println(bodyMap.values.sumBy { it.numParents() })
    println(bodyMap["YOU"]!!.findLowestCommonOrbitWithSum(bodyMap["SAN"]!!))
}
