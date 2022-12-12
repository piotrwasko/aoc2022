package com.adventofcode.day4

import com.adventofcode.input.Input

fun main() {
    println("Running Day 4 solutions")

    val input = Input.load(day = 4)
    println("Task1: ${Task1().solution(input)}")
    println("Task2: ${Task2().solution(input)}")
}
