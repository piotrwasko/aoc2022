package com.adventofcode.day9

import kotlin.math.abs
import kotlin.math.sign

class Task1 {
    val positions = HashSet<Pair<Int, Int>>()
    var head = (0 to 0)
    var tail = (0 to 0)

    fun solution(input: String): Int? {
        val moves = input.split("\n").filter { it.isNotEmpty() }.map { it.split(" ") }.map {it[0] to it[1].toInt()}

        positions.add(0 to 0)

        for (move in moves) {
            when(move.first) {
                "U" -> repeat(move.second) { move { it.first to it.second + 1 } }
                "D" -> repeat(move.second) { move { it.first to it.second - 1 } }
                "L" -> repeat(move.second) { move { it.first - 1 to it.second } }
                "R" -> repeat(move.second) { move { it.first + 1 to it.second } }
            }
        }

        return positions.size
    }

    fun move(m: Function1<Pair<Int, Int>, Pair<Int, Int>>): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        head = m(head)
        tail = tail
        if (abs(head.first - tail.first) > 1 && abs(head.second - tail.second) > 0) {
            tail = (tail.first + (head.first - tail.first).sign) to tail.second + (head.second - tail.second).sign
        }
        if (abs(head.first - tail.first) > 0 && abs(head.second - tail.second) > 1) {
            tail = (tail.first + (head.first - tail.first).sign) to tail.second + (head.second - tail.second).sign
        }
        if (abs(head.first - tail.first) > 1) {
            tail = (tail.first + (head.first - tail.first).sign) to tail.second
        }
        if (abs(head.second - tail.second) > 1) {
            tail = (tail.first to tail.second + (head.second - tail.second).sign)
        }
//        println("$head, $tail")
        positions.add(tail)
        return head to tail
    }
}
