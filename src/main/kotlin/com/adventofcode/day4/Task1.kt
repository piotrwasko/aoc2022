package com.adventofcode.day4

class Task1 {
    fun solution(input: String): Int? {
        return input.split("\n").filter { it.isNotEmpty() }.map{ it.split(",")
            .map { it.split("-").toPair()} }
            .filter { it[0].fc(it[1]) || it[1].fc(it[0]) }
            .count()
    }

    fun Pair<Int,Int>.fc(other: Pair<Int, Int>): Boolean {
//        println(this + " fc " + other + "=" + (this.first <= other.first && this.first >= other.first))
        return this.first <= other.first && this.second >= other.second
    }



    private fun List<String>.toPair(): Pair<Int, Int> {
        return (this[0].toInt() to this[1].toInt())
    }

}
