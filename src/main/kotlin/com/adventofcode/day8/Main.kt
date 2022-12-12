package com.adventofcode.day8

import com.adventofcode.input.Input

fun main() {
    println("Running Day 8 solutions")

    val input = Input.load(day = 8)
    println("Test: ${Task1().solution(testInput)}")
    println("Task1: ${Task1().solution(input)}")
    println("Test2: ${Task2().solution(testInput)}")
    println("Task2: ${Task2().solution(input)}")
}

val testInput = """
30373
25512
65332
33549
35390"""
