package com.adventofcode.day13

import com.adventofcode.input.Input

fun main() {
    println("Running Day 13 solutions")

    val input = Input.load(day = 13)
    println("Test: ${Task1().solution(testInput)}")
    println("Task1: ${Task1().solution(input)}")
    println("Test: ${Task2().solution(testInput)}")
    println("Task2: ${Task2().solution(input)}")
}

val testInput = """
    [1,1,3,1,1]
    [1,1,5,1,1]

    [[1],[2,3,4]]
    [[1],4]

    [9]
    [[8,7,6]]

    [[4,4],4,4]
    [[4,4],4,4,4]

    [7,7,7,7]
    [7,7,7]

    []
    [3]

    [[[]]]
    [[]]

    [1,[2,[3,[4,[5,6,7]]]],8,9]
    [1,[2,[3,[4,[5,6,0]]]],8,9]
""".trimIndent()
