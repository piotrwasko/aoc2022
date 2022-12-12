package com.adventofcode.day3

class Task1 {
    fun solution(input: String): Int? {
        return input.split("\n").filter { it.isNotEmpty() }
            .map { it.take(it.length/2) to it.drop(it.length/2)}
            .map { it.findDuplicates() }
            .map { it.toPriorities() }
            .sum()
    }
}

private fun Char.toPriorities(): Int {
    return if (this.isLowerCase()) (this.code - 96) else (this.code - 65 + 27)
}

private fun Pair<String, String>.findDuplicates(): Char {
    for (l: Char in this.first) {
        if (this.second.contains(l)) {
            return l
        }
    }
    throw Exception()
}
