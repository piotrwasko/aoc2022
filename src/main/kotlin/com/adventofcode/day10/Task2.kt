package com.adventofcode.day10

import kotlin.math.abs

class Task2 {
    fun solution(input: String): String {

        var cycle = 0
        var counter = 1
        var sum = 0
        var drawing = ""
        input.split("\n").filter { it.isNotEmpty() }
            .forEach {
                val parts = it.split(" ")
                when (parts[0]) {
                    "noop" -> {
                        cycle += 1
                        if (abs(counter - ((cycle - 1) % 40)) <= 1) {
                            println("$cycle, $counter. Drawing.")
                            drawing += "#"
                        } else {
                            drawing += "."
                        }
                        println(drawing)
                    }

                    "addx" -> {
                        if (abs(counter - ((cycle) % 40)) <= 1) {
                            println("$cycle, $counter. Drawing.")
                            drawing += "#"
                        } else {
                            drawing += "."
                        }
                        println(drawing)
                        if (abs(counter - ((cycle + 1) % 40)) <= 1) {
                            println("$cycle, $counter. Drawing.")
                            drawing += "#"
                        } else {
                            drawing += "."
                        }
                        println(drawing)
                        cycle += 2
                        counter += parts[1].toInt()
                    }
                }
            }

        return drawing.windowed(40, 40).joinToString("\n", "\n")
    }
}
