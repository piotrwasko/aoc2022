package com.adventofcode.day24

import java.util.PriorityQueue

class Task2 {
    fun solution(input: String): Int? {
        val data = input.split("\n").filter { it.isNotEmpty() }
            .drop(1).dropLast(1)

        val blizzards = data.mapIndexed { r, row ->
            row.drop(1).dropLast(1).mapIndexed { c, char -> when(char) {
                '.' -> null
                '^' -> Pos(c, r) to Blizzard(Dir.UP)
                'v' -> Pos(c, r) to Blizzard(Dir.DOWN)
                '<' -> Pos(c, r) to Blizzard(Dir.LEFT)
                '>' -> Pos(c, r) to Blizzard(Dir.RIGHT)
                else -> throw IllegalStateException()
            }
            }
        }.flatten().filterNotNull()

        val COLS = data[0].length - 2
        val ROWS = data.size

        val start = Pos(0, -1)
        val target = Pos(COLS - 1, ROWS)

        val field = Field(COLS, ROWS, mutableListOf(blizzards))

        val bestTo = findBestWay(0, start, target, field, ROWS, COLS)
        val bestBack = findBestWay(bestTo, target, start, field, ROWS, COLS)
        val bestToAgain = findBestWay(bestBack, start, target, field, ROWS, COLS)
        return bestToAgain
    }

    private fun findBestWay(
        turn: Int,
        start: Pos,
        target: Pos,
        field: Field,
        ROWS: Int,
        COLS: Int
    ): Int {
        val visited = mutableSetOf<Pair<Int, Pos>>()
        val toCheck = PriorityQueue<Pair<Int, Pos>>(Comparator.comparing { it.first })
        toCheck.add(turn to start)

        var bestScore = 100000
        while (toCheck.isNotEmpty()) {
            val (turn, pos) = toCheck.remove()
            //            println("Checking $turn: $pos")

            if (pos.row == target.row && pos.col == target.col) {
                bestScore = Integer.min(bestScore, turn)
                continue
            }
            if (turn >= bestScore || Pair(turn, pos) in visited) {
                continue
            }

            visited.add(Pair(turn, pos))

            val nextTurn = turn + 1
            val newField = field.get(nextTurn)
            listOf<Pos>(
                pos,
                pos.copy(col = pos.col + 1),
                pos.copy(col = pos.col - 1),
                pos.copy(row = pos.row + 1),
                pos.copy(row = pos.row - 1),
            ).filter { it == target || it == start || (it.row in 0 until ROWS && it.col in 0 until COLS) }
                .forEach { nextPos ->
                    if (nextPos !in newField) {
                        toCheck.add(nextTurn to nextPos)
                    }
                }
        }


        return bestScore
    }

    private data class Pos(val col: Int, val row: Int)
    private data class Blizzard(val dir: Dir)

    private data class Field(
        val cols: Int, val rows: Int,
        val blizzards: MutableList<List<Pair<Pos, Blizzard>>>
    ) {

        private val hasBlizzard = blizzards.map { it.map { it.first }.toSet() }.toMutableList()
        fun get(turn: Int): Set<Pos> {
            if (turn < blizzards.size) {
                return hasBlizzard[turn]
            }
            blizzards.add(
                blizzards[turn - 1].map { e -> when (e.second.dir) {
                    Dir.UP -> Pos(e.first.col, (rows + e.first.row - 1) % rows) to e.second
                    Dir.DOWN -> Pos(e.first.col, (rows + e.first.row + 1) % rows) to e.second
                    Dir.LEFT -> Pos((cols + e.first.col - 1) % cols, e.first.row) to e.second
                    Dir.RIGHT -> Pos((cols + e.first.col + 1) % cols, e.first.row) to e.second
                }
                })
            hasBlizzard.add(blizzards[turn].map { it.first }.toSet())
            return hasBlizzard[turn]
        }
    }

    private enum class Dir {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
}
