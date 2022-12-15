package com.adventofcode.day15

import java.lang.Long.max

class Task2 {
    fun solution(input: String, MAX: Long): Long? {
        val sensors = input.split("\n").filter { it.isNotEmpty() }
            .map { it.split(":").map { it.split("=") }
                .map { it[1].split(",")[0].toLong() to it[2].toLong() }
            }.map {it[0] to it[1]}

        LongRange(0, MAX).forEach { row ->
            val rowOccupiedPositions = mutableSetOf<LongRange>()
            sensors.forEach {
                val dist = Math.abs(it.first.first - it.second.first) + Math.abs(it.first.second - it.second.second)
                val toRow = Math.abs(row - it.first.second)
                if (toRow <= dist) {
                    rowOccupiedPositions.add(LongRange(it.first.first - dist + toRow, it.first.first + dist - toRow))
                }
            }
            rowOccupiedPositions
                .sortedBy { it.first }
                .fold(-1L) { maxFound, r ->
                    if (r.first > maxFound + 1) {
                        return row + (maxFound + 1) * 4000000
                    } else {
                        max(r.last, maxFound)
                    }
                }
        }
        return null
    }

}
