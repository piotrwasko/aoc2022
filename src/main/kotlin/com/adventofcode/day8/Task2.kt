package com.adventofcode.day8

class Task2 {
    fun solution(input: String): Int? {
        val trees = input.split("\n").filter { it.isNotEmpty() }.map { it.toList().map { it.digitToInt() } }
        val scores = trees.map { it.map { 1 }.toMutableList() }

        for (i in 0 until trees.size) {
            var maxLeft = HashMap<Int, Int>()
            for (j in 0 until trees[i].size) {
                var score = trees[i].size + 1
                for (d in trees[i][j]..9) {
                    val view = j - (maxLeft[d] ?: 0)
                    if (view < score) {
                        score = view
                    }
                }
                maxLeft[trees[i][j]] = j
                scores[i][j] *= score
            }

            var maxRight = HashMap<Int, Int>()
            for (j in (trees[i].size - 1) downTo  0) {
                var score = trees[i].size + 1
                for (d in trees[i][j]..9) {
                    val view = (maxRight[d] ?: (trees[i].size - 1)) - j
                    if (view < score) {
                        score = view
                    }
                }
                maxRight[trees[i][j]] = j
                scores[i][j] *= score
            }
//            println(scores)

        }
        println("top/bot")
        for (j in 0 until trees[0].size) {
            var maxTop = HashMap<Int, Int>()
            for (i in 0 until trees.size) {
                var score = trees.size + 1
                for (d in trees[i][j]..9) {
                    val view = i - (maxTop[d] ?: 0)
                    if (view < score) {
                        score = view
                    }
                }
                maxTop[trees[i][j]] = i
                scores[i][j] *= score
            }
//            println(scores)

            var maxBot = HashMap<Int, Int>()
            for (i in (trees.size - 1) downTo  0) {
                var score = trees.size + 1
                for (d in trees[i][j]..9) {
                    val view = (maxBot[d] ?: (trees.size - 1)) - i
                    if (view < score) {
                        score = view
                    }
                }
                maxBot[trees[i][j]] = i
                scores[i][j] *= score
            }
        }
//        println(scores)
        return scores.map { it.max() }.max()
    }
}
