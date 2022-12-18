package com.adventofcode.day18

import com.adventofcode.input.Input

fun main() {
    println("Running Day 18 solutions")

    val input = Input.load(day = 18)
    println("Test: ${Task1().solution(testInput)}")
    println("Task1: ${Task1().solution(input)}")
    println("Test: ${Task2().solution(testInput)}")
    println("Task2: ${Task2().solution(input)}")
}

val testInput = """
    2,2,2
    1,2,2
    3,2,2
    2,1,2
    2,3,2
    2,2,1
    2,2,3
    2,2,4
    2,2,6
    1,2,5
    3,2,5
    2,1,5
    2,3,5
""".trimIndent()
