package com.adventofcode.day22

import com.adventofcode.input.Input

fun main() {
    println("Running Day 22 solutions")

    val input = Input.load(day = 22)
//    println("Test: ${Task1().solution(testInput)}")
//    println("Task1: ${Task1().solution(input)}")
//    println("Test: ${Task2().solution(testInput)}")
    println("Task2: ${Task2().solution(input)}")
}

val testInput = """
        ...#
        .#..
        #...
        ....
...#.......#
........#...
..#....#....
..........#.
        ...#....
        .....#..
        .#......
        ......#.

10R5L5R10L4R5L5    
""".trimIndent()
