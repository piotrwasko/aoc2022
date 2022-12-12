package com.adventofcode.day8

class Task1 {
    fun solution(input: String): Int? {
        val trees = input.split("\n").filter { it.isNotEmpty() }.map { it.toList().map { it.digitToInt() } }
        val visible = trees.map { it.map { false }.toMutableList() }

        for (i in 0 until trees.size) {
            var maxLeft = -1
            for (j in 0 until trees[i].size) {
                if (trees[i][j] > maxLeft) {
                    visible[i][j] = true
                    maxLeft = trees[i][j]
                }
            }
            var maxRight = -1
            for (j in (trees[i].size - 1) downTo  0) {
                if (trees[i][j] > maxRight) {
                    visible[i][j] = true
                    maxRight = trees[i][j]
                }
            }

        }
        println("top/bot")
        for (j in 0 until trees[0].size) {
            var maxTop = -1
            for (i in 0 until trees.size) {
                if (trees[i][j] > maxTop) {
                    visible[i][j] = true
                    maxTop = trees[i][j]
                }
            }
            var maxBot = -1
            for (i in (trees.size - 1) downTo  0) {
                if (trees[i][j] > maxBot) {
                    visible[i][j] = true
                    maxBot = trees[i][j]
                }
            }
        }
        return visible.map { it.map { if (it == true) 1 else 0 }.sum() }.sum()
    }
}
