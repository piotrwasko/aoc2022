package com.adventofcode.day7

import com.adventofcode.input.Input

fun main() {
    println("Running Day 7 solutions")

    val input = Input.load(day = 7)
//    println("Test: ${Task1().solution(test)}")
    println("Task1: ${Task1().solution(input)}")
    println("Test: ${Task2().solution(test)}")
    println("Task2: ${Task2().solution(input)}")
}

val test = """
    ${'$'} cd /
    ${'$'} ls
    dir a
    14848514 b.txt
    8504156 c.dat
    dir d
    ${'$'} cd a
    ${'$'} ls
    dir e
    29116 f
    2557 g
    62596 h.lst
    ${'$'} cd e
    ${'$'} ls
    584 i
    ${'$'} cd ..
    ${'$'} cd ..
    ${'$'} cd d
    ${'$'} ls
    4060174 j
    8033020 d.log
    5626152 d.ext
    7214296 k
""".trimIndent()
