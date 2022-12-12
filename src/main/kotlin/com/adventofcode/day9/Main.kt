package com.adventofcode.day9

import com.adventofcode.input.Input

fun main() {
    println("Running Day 1 solutions")

    val input = Input.load(day = 9)
    println("Test: ${Task1().solution(testInput)}")
    println("Task1: ${Task1().solution(input)}")
    println("Task2: ${Task2().solution(testInput2)}")
    println("Task2: ${Task2().solution(input)}")
}

val testInput = """
R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2
""".trimIndent()

val testInput2 = """
    R 5
    U 8
    L 8
    D 3
    R 17
    D 10
    L 25
    U 20
""".trimIndent()
