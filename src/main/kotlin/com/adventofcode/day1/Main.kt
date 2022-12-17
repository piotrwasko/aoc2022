package com.adventofcode.day1

import com.adventofcode.input.Input

fun main() {
    println("Running Day 1 solutions")

    val input = Input.load(day = 1)
    println("Test: ${Task1().solution(testInput)}")
    println("Task1: ${Task1().solution(input)}")
    println("Test: ${Task2().solution(testInput)}")
    println("Task2: ${Task2().solution(input)}")
}

val testInput = """
    
""".trimIndent()
