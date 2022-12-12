package com.adventofcode.day12

import java.util.PriorityQueue

class Task2 {
    fun solution(input: String): Int? {
        val grid = input.split("\n")
            .filter { it.isNotEmpty() }
            .map{ it.toList() }

        var startX: Int = -1
        var startY: Int = -1
        var endX: Int = -1
        var endY: Int = -1

        grid.forEachIndexed { i, it ->
            val foundS = it.indexOfFirst { it == 'S' }
            if (foundS != -1) {
                startY = foundS
                startX = i
            }
            val foundE = it.indexOfFirst { it == 'E' }
            if (foundE != -1) {
                endY = foundE
                endX = i
            }
        }
        val start = startX to startY
        val end = endX to endY

        val intGrid = grid.map { r -> r.map { if (it == 'S') 0 else if (it == 'E') ('z' - 'a') else (it - 'a')}}

        val comparator: Comparator<Pair<Int, Pair<Int, Int>>> = Comparator.comparing { it.first }
        val visited = HashSet<Pair<Int, Int>>()
        val shortestWay = PriorityQueue( comparator )
        for (i in intGrid.indices) {
            for (j in intGrid[i].indices) {
                if (intGrid[i][j] == 0) {
                    shortestWay.add(0 to (i to j))
                }
            }
        }

        while (shortestWay.isNotEmpty()) {
            val currentP = shortestWay.poll()
//            println("Inspecting: $currentP")
            if (currentP.second == end) {
                return currentP.first
            }
            val currentVal = intGrid[currentP.second.first][currentP.second.second]
//            println(currentVal)
            // up
            if (currentP.second.first - 1 >= 0 && intGrid[currentP.second.first - 1][currentP.second.second] <= currentVal + 1
                && !visited.contains((currentP.second.first - 1 to currentP.second.second))) {
                shortestWay.add(currentP.first + 1 to (currentP.second.first - 1 to currentP.second.second))
                visited.add((currentP.second.first - 1 to currentP.second.second))
            }
            // down
            if (currentP.second.first + 1 < intGrid.size && intGrid[currentP.second.first + 1][currentP.second.second] <= currentVal + 1
                && !visited.contains((currentP.second.first + 1 to currentP.second.second))) {
                shortestWay.add(currentP.first + 1 to (currentP.second.first + 1 to currentP.second.second))
                visited.add((currentP.second.first + 1 to currentP.second.second))
            }
            // left
            if (currentP.second.second - 1 >= 0 && intGrid[currentP.second.first][currentP.second.second - 1] <= currentVal + 1
                && !visited.contains((currentP.second.first to currentP.second.second - 1))) {
                shortestWay.add(currentP.first + 1 to (currentP.second.first to currentP.second.second - 1))
                visited.add((currentP.second.first to currentP.second.second - 1))
            }
            // right
            if (currentP.second.second + 1 < intGrid[0].size && intGrid[currentP.second.first][currentP.second.second + 1] <= currentVal + 1
                && !visited.contains((currentP.second.first to currentP.second.second + 1))) {
                shortestWay.add(currentP.first + 1 to (currentP.second.first to currentP.second.second + 1))
                visited.add((currentP.second.first to currentP.second.second + 1))
            }
        }
        return null
    }

}
