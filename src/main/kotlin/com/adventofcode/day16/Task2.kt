package com.adventofcode.day16

import java.lang.Integer.max
import java.lang.Integer.min
import java.util.Comparator
import java.util.PriorityQueue

class Task2 {
    fun solution(input: String): Int {
        val valves = input.split("\n").filter { it.isNotEmpty() }
            .map { it.toValve() }
            .associateBy { it.name }

        val toOpen = valves.values.filter { it.flow > 0 }.map { it.name }
        val toCheck = PriorityQueue<Point>(Comparator.comparingInt<Point>{ -it.timeLeft })

        var distances = calculateDistances(valves, toOpen)
        println(distances)

        val start = Point(26, Opener("AA", 0), Opener("AA", 0), setOf("AA"), 0)
        toCheck.add(start)

        var topScore = 0

        var timeLeftCheck = 30
        while (toCheck.isNotEmpty()) {
            val point = toCheck.remove()

            val reached = listOf(point.me, point.elephant).filter { it.distance == 0 && it.target !in point.visitedNodes}
            val newVisited = point.visitedNodes + reached.map { it.target }
            val toVisit = (toOpen - newVisited).toSet()
            val newScore = point.score + reached.map{ it.target }.toSet().sumOf { valves[it]!!.flow * point.timeLeft }

            val potentialScore = point.score + toVisit.sumOf { valves[it]!!.flow * (point.timeLeft - 1) }
            if (potentialScore < topScore) {
                continue
            }

            if (point.timeLeft < timeLeftCheck) {
                timeLeftCheck = point.timeLeft
                println("$timeLeftCheck: toCheck: ${toCheck.size}, topScore: $topScore")
            }
//            if (newScore > topScore) {
//                println("[Minute ${26 - point.timeLeft}]\n\tReached: $reached\n\tHigh score: $newScore at $point")
//            }
            topScore = max(topScore, newScore)

            if (point.me.distance == 0 && point.elephant.distance == 0) {
                val nextMe = point.me.next(distances, toVisit)
                val nextElephant = point.elephant.next(distances, toVisit)
                nextMe.forEach { me -> nextElephant.forEach { el ->
                    val nextMove = min(me.distance, el.distance)
                    if (nextMove <= point.timeLeft) {
                        toCheck.add(Point(
                            point.timeLeft - nextMove,
                            me = me.copy(distance = me.distance - nextMove),
                            elephant = el.copy(distance = el.distance - nextMove),
                            newVisited,
                            newScore
                            )
                        )
                    }
                } }
            } else if (point.me.distance == 0) {
                val nextMe = point.me.next(distances, toVisit)
                nextMe.forEach { me ->
                    val nextMove = min(me.distance, point.elephant.distance)
                    if (nextMove <= point.timeLeft) {
                        toCheck.add(Point(
                            point.timeLeft - nextMove,
                            me = me.copy(distance = me.distance - nextMove),
                            elephant = point.elephant.copy(distance = point.elephant.distance - nextMove),
                            newVisited,
                            newScore
                        )
                        )
                    }
                }
            } else if (point.elephant.distance == 0) {
                val nextElephant = point.elephant.next(distances, toVisit)
                nextElephant.forEach { el ->
                    val nextMove = min(point.me.distance, el.distance)
                    if (nextMove <= point.timeLeft) {
                        toCheck.add(Point(
                            point.timeLeft - nextMove,
                            me = point.me.copy(distance = point.me.distance - nextMove),
                            elephant = el.copy(distance = el.distance - nextMove),
                            newVisited,
                            newScore
                        )
                        )
                    }
                }
            }

        }

        return topScore
    }

    private fun calculateDistances(valves: Map<String, Valve>, toOpen: List<String>): Map<Pair<String, String>, Int> {
        return (toOpen + "AA").flatMap { start ->
            val distances = HashMap<String, Int>()
            val toCheck = PriorityQueue<Pair<String, Int>>(compareBy { it.second })
            val visited = mutableSetOf<String>()
            toCheck.add(start to 0)

            while(toCheck.isNotEmpty()) {
                val p = toCheck.remove()
                val valve = valves[p.first]!!
                visited.add(p.first)
                valve.connectedTo.forEach {
                    if (it !in visited) {
                        toCheck.add(it to p.second + 1)
                    }
                }
                if (p.first in toOpen) {
                    distances[p.first] = min(distances[p.first] ?: 999, p.second + 1)
                }
            }
            distances.map { (start to it.key) to it.value }
        }.toMap()
    }


    private fun String.toValve(): Valve {
        //Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
        val parts = this.split(";")
        val flow = parts[0].split("=")
        val connectedTo = parts[1].split(Regex("valve.? "))[1].split(", ")
        return Valve(flow[0].split(" ")[1], flow[1].toInt(), connectedTo)
    }


    data class Valve(val name: String, val flow: Int, val connectedTo: List<String>)

    data class Point(
        val timeLeft: Int,
        val me: Opener,
        val elephant: Opener,
        val visitedNodes: Set<String>,
        val score: Int
    )

    data class Opener(
        val target: String,
        val distance: Int
    ) {
        fun next(distances: Map<Pair<String, String>, Int>, toVisit: Set<String>): List<Opener> {
            return toVisit.map { Opener(it, distances[this.target to it]!!) }
        }
    }

}


/*


        var topScore = 0L

        var timeLeftTrack = 30;

        while (toCheck.isNotEmpty()) {
            val point = toCheck.remove()
            visited.add((point.current to point.elephant) to point.open)
            visited.add((point.elephant to point.current) to point.open)

            if (point.timeLeft < timeLeftTrack) {
                timeLeftTrack = point.timeLeft
                println("$timeLeftTrack - left to check: ${toCheck.size}")
            }

            val openScore = point.open.sumOf { valves[it]!!.flow }

            if (point.timeLeft == 0 || point.open.size == toOpen.size) {
//                println(point)
                topScore = max(topScore, point.score + openScore * point.timeLeft)
                continue
            }

            val valve = valves[point.current]!!
            val elephantValve = valves[point.elephant]!!
            // O O
            val opened = listOf(point.current, point.elephant).filter {
                canOpen(valves, it, point)
            }
            if (opened.size == 2) {
                toCheck.add(point.copy(
                    open = point.open + opened,
                    timeLeft = point.timeLeft - 1,
                    score = point.score + openScore
                ))
            }
            // O M
            if (canOpen(valves, point.current, point)) {
                elephantValve.connectedTo.map { elephant -> point.current to elephant }
                    .filter { !visited.contains(it to point.open + point.current) }
                    .forEach {
                        toCheck.add(point.copy(
                            elephant = it.second,
                            open = point.open + point.current,
                            timeLeft = point.timeLeft - 1,
                            score = point.score + openScore
                        ))
                    }
            }
            // M O
            if (canOpen(valves, point.elephant, point)) {
                valve.connectedTo.map { me -> me to point.elephant }
                    .filter { !visited.contains(it to point.open + point.elephant) }
                    .forEach {
                        toCheck.add(point.copy(
                            current = it.first,
                            open = point.open + point.elephant,
                            timeLeft = point.timeLeft - 1,
                            score = point.score + openScore
                        ))
                    }
            }

            // M M
            val connections = valve.connectedTo.flatMap { me -> elephantValve.connectedTo.map { elephant -> me to elephant } }

            connections.filter { !visited.contains(it to point.open) }.forEach { next ->
                toCheck.add(Point(
                    next.first,
                    next.second,
                    point.open,
                    point.timeLeft - 1,
                    score = point.score + openScore
                ))
            }
        }
        return topScore
* */
