package com.adventofcode.day25

import com.adventofcode.input.Input

fun main() {
    println("Running Day 1 solutions")

    val input = Input.load(day = 25)
    println("Test: ${Task1().solution(testInput)}")
    println("Task1: ${Task1().solution(input)}")
}

val testInput = """
1=-0-2
12111
2=0=
21
2=01
111
20012
112
1=-1=
1-12
12
1=
122
""".trimIndent()
