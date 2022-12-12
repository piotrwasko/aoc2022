package com.adventofcode.day3

class Task2 {
    fun solution(input: String): Int? {
        return input.split("\n").filter { it.isNotEmpty() }
            .windowed(3, 3)
            .map { it.findDuplicates() }
            .map { it.toPriorities() }
            .sum();
    }

    private fun Char.toPriorities(): Int {
        return if (this.isLowerCase()) (this.code - 96) else (this.code - 65 + 27)
    }
}

private fun List<String>.findDuplicates(): Char {
    println(this)
    for (l: Char in this[0]) {
        println("Parsing $l: ${this[1].contains(l)} ${this[2].contains(l)}")
        if (this[1].contains(l) && this[2].contains(l)) {
            println("returning ${l}")
            return l
        }
    }
    throw Exception()
}
