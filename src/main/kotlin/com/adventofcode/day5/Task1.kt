package com.adventofcode.day5

import java.util.ArrayDeque
import java.util.Deque

class Task1 {
    fun solution(input: String): String {
        val (stacks, proc) = asInput(input.split("\n\n").filter { it.isNotEmpty() })
//        println(stacks)
        return procedure(stacks, proc)
    }

    private fun procedure(stacks: java.util.ArrayList<Deque<Char>>, proc: List<Pair<Int, Pair<Int, Int>>>): String {
        proc.forEach { p ->
            repeat(p.first) { stacks[p.second.second - 1].push(stacks[p.second.first - 1].pop()) }
//            println(stacks)
        }
        return stacks.map { it.first }.joinToString("")
    }

    private fun asInput(input: List<String>): Pair<ArrayList<Deque<Char>>, List<Pair<Int, Pair<Int, Int>>>> {
        return (asStacks(input[0]) to asProcedure(input[1]))
    }

    private fun asStacks(s: String): ArrayList<Deque<Char>> {
        val stacks = ArrayList<Deque<Char>>()
        repeat(9) { stacks.add(ArrayDeque()) }
        s.split("\n").filter { it.isNotEmpty() && it.startsWith("[")}
            .forEach{ row -> row.windowed(4, 4, true).forEachIndexed { i:Int, r: String -> if (r.isNotBlank()) stacks[i].addLast(r.parseRow()) } }
        return stacks
    }

    private fun asProcedure(input: String): List<Pair<Int, Pair<Int, Int>>> {
        return input.split("\n").filter { it.isNotEmpty() }
            .map { asProcedureRow(it) }
    }

    private fun asProcedureRow(p: String): Pair<Int, Pair<Int, Int>> {
        val a = p.split(" ").filter { it.filter { it.isDigit() }.isNotBlank() }
        return (a[0].toInt() to (a[1].toInt() to a[2].toInt()))
    }

    private fun String.parseRow(): Char {
        return this.find { it.isLetter() } ?: throw IllegalStateException("No letter in $this")
    }
}
