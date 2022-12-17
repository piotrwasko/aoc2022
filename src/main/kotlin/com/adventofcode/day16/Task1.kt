package com.adventofcode.day16

import java.util.Comparator
import java.util.PriorityQueue
import kotlin.math.max

class Task1 {
    fun solution(input: String): Long {
        val valves = input.split("\n").filter { it.isNotEmpty() }
            .map { it.toValve() }
            .associateBy { it.name }

        val toOpen = valves.values.filter { it.flow > 0 }.map { it.name }
        val toCheck = PriorityQueue<Point>(Comparator.comparingInt<Point>{ -it.timeLeft })

        val visited = HashSet<Pair<String, Set<String>>>()
        val start = Point("AA", setOf(), 30, 0)

        toCheck.add(start)

        var topScore = 0L

        while (toCheck.isNotEmpty()) {
            val point = toCheck.remove()
            val valve = valves[point.current]!!
            visited.add(point.current to point.open)

            val openScore = point.open.sumOf { valves[it]!!.flow }

            if (point.timeLeft == 0 || point.open.size == toOpen.size) {
                topScore = max(topScore, point.score + openScore * point.timeLeft)
                continue
            }

            if (valve.flow > 0 && !point.open.contains(point.current)) {
                toCheck.add(point.copy(
                    open = point.open + point.current,
                    timeLeft = point.timeLeft - 1,
                    score = point.score + openScore
                ))
            }

            valve.connectedTo.filter { !visited.contains(it to point.open) }.forEach { next ->
                toCheck.add(
                    Point(
                        next,
                        point.open,
                        point.timeLeft - 1,
                        score = point.score + openScore
                    )
                )
            }
        }
        return topScore
    }



    private fun String.toValve(): Valve {
        //Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
        println(this)
        val parts = this.split(";")
        val flow = parts[0].split("=")
        val connectedTo = parts[1].split(Regex("valve.? "))[1].split(", ")
        return Valve(flow[0].split(" ")[1], flow[1].toInt(), connectedTo)
    }


    data class Valve(val name: String, val flow: Int, val connectedTo: List<String>)

    data class Point(
        val current: String,
        val open: Set<String>,
        val timeLeft: Int,
        val score: Long
    )
}
