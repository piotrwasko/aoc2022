package com.adventofcode.day6

import com.adventofcode.input.Input

fun main() {
    println("Running Day 1 solutions")

    val input = Input.load(day = 6)
    println("Test: ${Task1().solution("bvwbjplbgvbhsrlpgdmjqwftvncz")}")
    println("Test: ${Task1().solution("nppdvjthqldpwncqszvftbrmjlhg")}")
    println("Test: ${Task1().solution("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")}")
    println("Test: ${Task1().solution("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")}")
    println("Task1: ${Task1().solution(input)}")
    println("Task2: ${Task2().solution(input)}")
}
