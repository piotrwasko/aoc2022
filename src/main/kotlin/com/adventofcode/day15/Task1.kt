package com.adventofcode.day15

import java.lang.Math.abs

class Task1 {
    fun solution(input: String, ROW: Int): Int? {
        val sensors = input.split("\n").filter { it.isNotEmpty() }
            .map { it.split(":").map { it.split("=") }
                .map { it[1].split(",")[0].toInt() to it[2].toInt() }
            }.map {it[0] to it[1]}
//        println(sensors)

        val rowOccupiedPositions = mutableSetOf<Int>()
        sensors.forEach {
            val dist = abs(it.first.first - it.second.first) + abs(it.first.second - it.second.second)
            val toRow = abs(ROW - it.first.second)
            IntRange(it.first.first - dist + toRow, it.first.first + dist - toRow).forEach { rowOccupiedPositions.add(it) }
        }

//        println(rowOccupiedPositions.sorted())
        rowOccupiedPositions.removeAll(sensors.map { it.second }.filter { it.second == ROW }.map { it.first }.toSet())
        return rowOccupiedPositions.size
    }

}
