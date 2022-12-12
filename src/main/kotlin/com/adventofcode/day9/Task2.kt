package com.adventofcode.day9

import kotlin.math.abs
import kotlin.math.sign

class Task2 {
    val positions = HashSet<Pair<Int, Int>>()
    var knots = listOf(
        (0 to 0),
        (0 to 0),
        (0 to 0),
        (0 to 0),
        (0 to 0),
        (0 to 0),
        (0 to 0),
        (0 to 0),
        (0 to 0),
        (0 to 0)
    )

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

    fun move(m: Function1<Pair<Int, Int>, Pair<Int, Int>>) {
        knots = knots.drop(1).fold(listOf(m(knots[0]))) { acc, knot ->
            acc + move(acc.last(), knot)
        }
        positions.add(knots[9])
//        println(knots)
    }

    fun move(head: Pair<Int, Int>, tail: Pair<Int, Int>): Pair<Int, Int> {
        var newTail = tail
        if (abs(head.first - newTail.first) > 1 && abs(head.second - newTail.second) > 0) {
            newTail = (newTail.first + (head.first - newTail.first).sign) to newTail.second + (head.second - newTail.second).sign
        }
        if (abs(head.first - newTail.first) > 0 && abs(head.second - newTail.second) > 1) {
            newTail = (newTail.first + (head.first - newTail.first).sign) to newTail.second + (head.second - newTail.second).sign
        }
        if (abs(head.first - newTail.first) > 1) {
            newTail = (newTail.first + (head.first - newTail.first).sign) to newTail.second
        }
        if (abs(head.second - newTail.second) > 1) {
            newTail = (newTail.first to newTail.second + (head.second - newTail.second).sign)
        }
//        println("$head, $tail")
        return newTail
    }
}
