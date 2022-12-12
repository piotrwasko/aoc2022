package com.adventofcode.day1

import com.adventofcode.input.Input

fun main() {
    println("Running Day 1 solutions")

    val input = Input.load(day = 1)
    println("Task1: ${Task1().solution(input)}")
    println("Task2: ${Task2().solution(input)}")
}
