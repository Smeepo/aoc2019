package day4

val input = 165432..707912

fun main(){
    print(input.filter { it.isValidPassword() }.count())
}

fun Int.isValidPassword(): Boolean{
    val password = this.toString()

    var groups: MutableMap<Int, Int> = mutableMapOf()
    var groupSize = 0
    var last: Char = password[0]
    for((index, c) in password.withIndex()) {
        if(c == last) {
            groupSize++
        } else {
            groups[groupSize] = groups.getOrDefault(groupSize,0) + 1
            groupSize = 1
        }
        if (index == password.length - 1) groups[groupSize] = groups.getOrDefault(groupSize,0) + 1
        if(c < last) return false
        last = c
    }
    return groups.getOrDefault(2,0) >= 1
}
