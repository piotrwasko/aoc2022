package com.adventofcode.day1

class Task1 {
    fun solution(input: String): Int? {
        return input.split("\n\n").maxOfOrNull { sumElf(it) }
    }

    private fun sumElf(it: String) = it.split("\n").filter { it.isNotEmpty() }.sumOf { it.toInt() }
}
