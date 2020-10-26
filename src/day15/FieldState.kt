package day15

enum class FieldState(val drawable: Char) {
    UNKNOWN(' ' ), FREE('.'), BLOCKED('#'), OXYGEN('O'), START('S')
}