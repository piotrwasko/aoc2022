package com.adventofcode.day18

class Task1 {
    fun solution(input: String): Int? {
        val drops = input.split("\n").filter { it.isNotEmpty() }.map { it.split(",")
            .map{ it.toInt() }}
            .map { Point(it[0], it[1], it[2]) }
            .toSet()

        var sum = 0
        drops.forEach {
            val covered = it.around().count { it in drops }
            sum += 6 - covered
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
    }

}
