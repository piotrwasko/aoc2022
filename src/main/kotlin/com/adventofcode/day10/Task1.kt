package com.adventofcode.day10

class Task1 {
    fun solution(input: String): Int? {
        val checkCycle = listOf(20, 60, 100, 140, 180, 220)
        var cycle = 0
        var counter = 1
        var sum = 0
        input.split("\n").filter { it.isNotEmpty() }
            .forEach {
                val parts = it.split(" ")
                when (parts[0]) {
                    "noop" -> {
                        cycle += 1
                        if (cycle in checkCycle) {
                            println("$cycle: $counter")
                            sum += cycle * counter
                        }
                    }

                    "addx" -> {
                        if (cycle + 1 in checkCycle) {
                            println("${cycle + 1}: $counter")
                            sum += (cycle + 1) * counter
                        } else if (cycle + 2 in checkCycle) {
                            println("${cycle + 2}: $counter")
                            sum += (cycle + 2) * counter
                        }
                        cycle += 2
                        counter += parts[1].toInt()
                    }
                }
            }
        return sum
    }
}
