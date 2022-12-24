package com.adventofcode.day23

import com.adventofcode.input.Input

fun main() {
    println("Running Day 23 solutions")

    val input = Input.load(day = 23)
//    println("Small: ${Task1().solution(smallTest)}")
    println("Test: ${Task1().solution(testInput)}")
    println("Task1: ${Task1().solution(input)}")
    println("Test: ${Task2().solution(testInput)}")
    println("Task2: ${Task2().solution(input)}")
}

val smallTest = """
.....
..##.
..#..
.....
..##.
.....
""".trimIndent()

val testInput = """
....#..
..###.#
#...#.#
.#...##
#.###..
##.#.##
.#..#..
""".trimIndent()
