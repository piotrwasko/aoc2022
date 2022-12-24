package com.adventofcode.day23

class Task2 {
    fun solution(input: String): Int? {
        val data = input.split("\n").filter { it.isNotEmpty() }
            .mapIndexed { row, r -> r.mapIndexed { col, c -> (row to col) to (c == '#') } }
            .flatten().toMap()

        var allElves = data.filterValues { it }.keys
        var moveOrder = listOf(Dir.N, Dir.S, Dir.W, Dir.E)
//        printState(allElves)
        repeat(200000) { round ->
            var elvesLeft = allElves.filter { e -> !moveOrder.all { e.canMove(allElves, it) } }.toSet()
            var proposedMoves = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>?>()

            if (elvesLeft.isEmpty()) {
                return round
            }

            for (dir in moveOrder) {
                val (moved, stayed) = elvesLeft.partition { it.canMove(allElves, dir) }
                elvesLeft = stayed.toSet()
                moved.forEach {
                    val nextMove = when (dir) {
                        Dir.N -> Pair(it.first - 1, it.second)
                        Dir.S -> Pair(it.first + 1, it.second)
                        Dir.W -> Pair(it.first, it.second - 1)
                        Dir.E -> Pair(it.first, it.second + 1)
                    }
//                    println("$nextMove from $it")
                    if (nextMove in proposedMoves) {
                        proposedMoves[nextMove] = null
                    } else {
                        proposedMoves[nextMove] = it
                    }
                }
            }

            allElves = allElves - proposedMoves.values.filterNotNull().toSet() + proposedMoves.filterValues { it != null }.keys
            moveOrder = moveOrder.drop(1) + moveOrder.take(1)

//            printState(allElves)
        }

        val left = allElves.minBy { it.second }.second
        val right = allElves.maxBy { it.second }.second
        val top = allElves.minBy { it.first }.first
        val bot = allElves.maxBy { it.first }.first

        return (bot - top + 1) * (right - left + 1) - allElves.size
    }

    private fun printState(allElves: Set<Pair<Int, Int>>) {
        (-2..9).forEach { r ->
            (-3..10).forEach { c ->
                print(if ((r to c) in allElves) '#' else '.')
            }
            println()
        }
        println()
    }

    private fun Pair<Int, Int>.canMove(allElves: Set<Pair<Int, Int>>, dir: Dir): Boolean {
        return when(dir) {
            Dir.N -> (-1..1).all { Pair(this.first - 1, this.second + it) !in allElves }
            Dir.S -> (-1..1).all { Pair(this.first + 1, this.second + it) !in allElves }
            Dir.W -> (-1..1).all { Pair(this.first + it, this.second - 1) !in allElves }
            Dir.E -> (-1..1).all { Pair(this.first + it, this.second + 1) !in allElves }
        }
    }

    private data class Move(val from: Pair<Int, Int>, val to: Pair<Int, Int>)

    private enum class Dir {
        N,
        S,
        W,
        E
    }
}
