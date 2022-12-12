package com.adventofcode.day2

import com.adventofcode.input.Input

fun main() {
    println("Running Day 2 solutions")

    val input = Input.load(day = 2)
    println("Task1: ${Task1().solution(input)}")
    println("Task2: ${Task2().solution(input)}")
}
