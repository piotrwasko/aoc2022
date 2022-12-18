package com.adventofcode.day18

import java.util.Comparator

class Task2 {
    fun solution(input: String): Int? {
        val drops = input.split("\n").filter { it.isNotEmpty() }.map { it.split(",")
            .map{ it.toInt() }}
            .map { Point(it[0], it[1], it[2]) }
            .toSet()
        var sum = 0
        val air = mutableSetOf<Point>()

//        plrintDrops(drops)
        drops.forEach {
            it.around().filter { it !in drops }.forEach {
                air.add(it)
            }
            val cov = it.around().count { it in drops }
            sum += 6 - cov
        }
        println("PrevSum: $sum")

        val border = mutableSetOf<Point>()
        val borderL = Point(drops.minOf { it.x } - 1, drops.minOf { it.y } - 1, drops.minOf { it.z } - 1)
        val borderR = Point(drops.maxOf { it.x } + 1, drops.maxOf { it.y } + 1, drops.maxOf { it.z } + 1)
        for (x in listOf(drops.minOf { it.x } - 1, drops.maxOf { it.x } + 1))
            for (y in drops.minOf { it.y } - 1.. drops.maxOf { it.y } + 1)
                for (z in drops.minOf { it.z } - 1.. drops.maxOf { it.z } + 1) {
                    border.add(Point(x, y, z))
                }

        for (y in listOf(drops.minOf { it.y } - 1, drops.maxOf { it.y } + 1))
            for (x in drops.minOf { it.x } - 1.. drops.maxOf { it.x } + 1)
                for (z in drops.minOf { it.z } - 1.. drops.maxOf { it.z } + 1) {
                    border.add(Point(x, y, z))
                }
        for (z in listOf(drops.minOf { it.z } - 1, drops.maxOf { it.z } + 1))
            for (x in drops.minOf { it.x } - 1.. drops.maxOf { it.x } + 1)
                for (y in drops.minOf { it.y } - 1.. drops.maxOf { it.y } + 1) {
                    border.add(Point(x, y, z))
                }

        while (border.addAll(border.flatMap { it.around() }.filter { it !in border && it > borderL && it < borderR && it !in drops})) {
            println("Added more fresh air: ${border.size}")
        }

        val freshAir = border
        println("sides touching fresh Air: ${drops.sumOf { it.around().count { it in freshAir } }}")

        (air - freshAir).forEach {
            sum -= it.around().filter { it in drops }.count()
        }
        return sum
    }

    data class Point(val x: Int, val y: Int, val z: Int) {
        fun around(): Set<Point> {
            val a = mutableSetOf<Point>()
            for (x in -1..1)
                a.add(this.copy(x = this.x + x))
            for (y in -1..1)
                a.add(this.copy(y = this.y + y))
            for (z in -1..1)
                a.add(this.copy(z = this.z + z))
            a.remove(this)
            return a
        }

        operator fun compareTo(other: Point): Int {
            return if (x < other.x && y < other.y && z < other.z) {
                -1
            } else if (x > other.x && y > other.y && z > other.z) {
                1
            } else {
                0
            }
        }
    }
}
