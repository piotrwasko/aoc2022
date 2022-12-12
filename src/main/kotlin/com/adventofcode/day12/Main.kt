package com.adventofcode.day12

import com.adventofcode.input.Input

fun main() {
    println("Running Day 12 solutions")

    val input = Input.load(day = 12)
    println("Test: ${Task1().solution(testInput)}")
    println("Task1: ${Task1().solution(input)}")
    println("Test: ${Task2().solution(testInput)}")
    println("Task2: ${Task2().solution(input)}")
}

val testInput = """
Sabqponm
abcryxxl
accszExk
acctuvwj
abdefghi
"""
