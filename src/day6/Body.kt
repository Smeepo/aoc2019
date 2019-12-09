package day6

class Body(var parent: Body?, val name: String) {

    val children: MutableSet<Body> = mutableSetOf()

    fun numParents(): Int {
        return if (parent == null) 0
        else parent!!.numParents() + 1
    }

    fun findLowestCommonOrbitWithSum(other: Body): Pair<Body, Int>? {
        val myParents = listParents()
        val otherParents = other.listParents()

        val closest = myParents.find {
            otherParents.contains(it)
        }
        if (closest == null) return null
        else return Pair(closest, myParents.indexOf(closest)+otherParents.indexOf(closest))
    }

    fun listParents(): List<Body> {
        val list = mutableListOf<Body>()
        listParents(list)
        return list.toList()
    }

    private fun listParents(list: MutableList<Body>){
        if (parent == null) return
        list.add(parent!!)
        parent!!.listParents(list)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Body) return false

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }


}
