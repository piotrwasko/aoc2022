package com.adventofcode.day1

class Task2 {
    fun solution(input: String): Int? {
        return input.split("\n\n").map { sumElf(it) }.sorted().takeLast(3).sum()
    }

    private fun sumElf(it: String) = it.split("\n").filter { it.isNotEmpty() }.sumOf { it.toInt() }
}
