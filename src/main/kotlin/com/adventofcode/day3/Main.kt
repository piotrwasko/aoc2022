package com.adventofcode.day3

import com.adventofcode.day3.Task1
import com.adventofcode.day3.Task2
import com.adventofcode.input.Input

fun main() {
    println("Running Day 3 solutions")

    val input = Input.load(day = 3)
    println("Task1: ${Task1().solution(input)}")
    println("Task2: ${Task2().solution(input)}")
}
