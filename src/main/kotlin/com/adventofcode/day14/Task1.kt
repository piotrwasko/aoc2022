package com.adventofcode.day14

import kotlin.math.max
import kotlin.math.min

class Task1 {
    fun solution(input: String): Int? {
        val rocks = input.split("\n")
            .filter { it.isNotEmpty() }
            .map { it.split("->").map { it.split(",").map { it.trim().toInt() } } }

        val maxX = rocks.maxOf { it.maxOf { it[0] } }
        val minX = rocks.minOf { it.minOf { it[0] }}
        val maxY = rocks.maxOf { it.maxOf { it[1] } }

        val cave = ArrayList<ArrayList<Point>>(maxY)
        repeat(maxY + 1) {
            val l = ArrayList<Point>()
            repeat(IntRange(minX, maxX).count()) { l.add(Point.EMPTY) }
            cave.add(l)
        }

        rocks.forEach {
            it.windowed(2).forEach { pair ->
                val left = min(pair[0][0] - minX, pair[1][0] - minX)
                val right = max(pair[0][0] - minX, pair[1][0] - minX)
                val top = min(pair[0][1], pair[1][1])
                val bot = max(pair[0][1], pair[1][1])
                for (x in IntRange(left, right)) {
                    for (y in IntRange(top, bot)) {
                        cave[y][x] = Point.ROCK
                    }
                }
            }
        }

        val sandfall = ArrayDeque<Pair<Int, Int>>()
        sandfall.add((500 - minX) to 0)

        while (sandfall.isNotEmpty()) {
            val point = sandfall.last()
            if (point.second + 1 > maxY) {
                break
            } else if (cave[point.second + 1][point.first].isEmpty()) {
                sandfall.add(point.first to point.second + 1)
            } else if (point.first - 1 < 0) {
                break
            } else if (cave[point.second + 1][point.first - 1].isEmpty()) {
                sandfall.add(point.first - 1 to point.second + 1)
            } else if (point.first + 1 > maxX - minX) {
                break
            }  else if (cave[point.second + 1][point.first + 1].isEmpty()) {
                sandfall.add(point.first + 1 to point.second + 1)
            } else {
                cave[point.second][point.first] = Point.SAND
                sandfall.removeLast()
            }
        }

        return cave.map { it.count { it == Point.SAND } }.sum()
    }

    enum class Point {
        EMPTY,
        ROCK,
        SAND;

        fun isEmpty() = this == EMPTY
    }
}
