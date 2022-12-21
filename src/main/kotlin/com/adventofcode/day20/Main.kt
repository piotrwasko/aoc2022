package com.adventofcode.day20

import com.adventofcode.input.Input

fun main() {
    println("Running Day 20 solutions")

    val input = Input.load(day = 20)
    println("Test: ${Task1().solution(testInput)}")
    println("Task1: ${Task1().solution(input)}")
    println("Test: ${Task2().solution(testInput)}")
    println("Task2: ${Task2().solution(input)}")
}

val testInput = """
    1
    2
    -3
    3
    -2
    0
    4
""".trimIndent()
