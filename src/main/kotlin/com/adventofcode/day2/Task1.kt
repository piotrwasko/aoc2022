package com.adventofcode.day2

class Task1 {
    fun solution(input: String): Int {
        return input.split("\n").filter { it.isNotEmpty() }.map { it.toRound() }.sumOf { it.score() }
    }



    private fun String.toRound(): Round {
        val letters = this.split(" ")
        val opponent = when(letters[0]) {
            "A" -> Shape.Rock
            "B" -> Shape.Paper
            "C" -> Shape.Scissors
            else -> { throw IllegalStateException() }
        }
        val me = when(letters[1]) {
            "X" -> Shape.Rock
            "Y" -> Shape.Paper
            "Z" -> Shape.Scissors
            else -> { throw IllegalStateException() }
        }
        return Round(opponent, me)
    }


    private data class Round(val opponent: Shape, val me: Shape) {

        fun score(): Int {
            val result = if (opponent == me) 3 else if (
                (opponent == Shape.Scissors && me == Shape.Rock)
                || (opponent == Shape.Rock && me == Shape.Paper)
                || (opponent == Shape.Paper && me == Shape.Scissors)
            ) 6 else 0
            return me.score + result
        }
    }

    private enum class Shape(val score: Int) {
        Rock(1),
        Paper(2),
        Scissors(3)
    }
}
