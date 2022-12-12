package com.adventofcode.day5

import com.adventofcode.day5.Task1
import com.adventofcode.day5.Task2
import com.adventofcode.input.Input

fun main() {
    println("Running Day 5 solutions")

    val input = Input.load(day = 5)
    println("Task1: ${Task1().solution(input)}")
    println("Task2: ${Task2().solution(input)}")
}
