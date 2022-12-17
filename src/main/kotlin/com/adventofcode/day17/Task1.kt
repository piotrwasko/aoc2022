package com.adventofcode.day17

import com.adventofcode.day17.Task1.Shapes.CROSS
import com.adventofcode.day17.Task1.Shapes.I
import com.adventofcode.day17.Task1.Shapes.L
import com.adventofcode.day17.Task1.Shapes.LINE
import com.adventofcode.day17.Task1.Shapes.O
import java.lang.IllegalStateException
import kotlin.math.max

class Task1 {
    fun solution(input: String): Int? {
        val moves = input.trim()
        val grid = mutableListOf<MutableList<Boolean>>()
        val pieces = listOf(LINE, CROSS, L, I, O)
        repeat(7) { x -> grid.add(mutableListOf()); repeat(6000) { grid[x].add(false) } }
        repeat(7) {grid[it][0] = true}

        var block: Block? = null
        var nextShape = 0
        var step = 0
        var shapesDropped = 0
        var top = 0

        while(shapesDropped < 2022) {
            if (block == null) {
                block = Block(2, top + 4, pieces[nextShape])
                nextShape = (nextShape + 1) % pieces.size
            }
            var currentBlock = block!!
            when(moves[step % moves.length]) {
                '<' -> {
                    if (currentBlock.x != 0 && currentBlock.checkMove(grid, currentBlock.x - 1)) {
                        currentBlock = currentBlock.copy(x = currentBlock.x - 1)
                    }
                }
                '>' -> {
                    if (currentBlock.right() != 6 && currentBlock.checkMove(grid, currentBlock.x + 1)) {
                        currentBlock = currentBlock.copy(x = currentBlock.x + 1)
                    }
                }
                else -> throw IllegalStateException()
            }
            if (currentBlock.checkDrop(grid)) {
//                println("Drop: $currentBlock")
                block = currentBlock.copy(y = currentBlock.y - 1)
            } else {
                currentBlock.shape.forEach {
                    // lockIn
                    grid[currentBlock.x + it.first][currentBlock.y + it.second] = true
                }
                block = null
                shapesDropped++
                top = max(top, currentBlock.top())
//                println("LockIn: $currentBlock, ${top} ")
//                drawGrid(grid, top)
            }
            step++
        }
        return top
    }

    private fun drawGrid(grid: MutableList<MutableList<Boolean>>, top: Int) {
        for (y in top downTo max(0, top - 10)) {
            repeat(7) { print(if (grid[it][y]) "#" else ".") }
            println()
        }
        println()
    }

    data class Block(
        val x: Int,
        val y: Int,
        val shape: List<Pair<Int, Int>>
    ) {
        val width = shape.maxOf { it.first }
        val height = shape.maxOf { it.second }
        fun right() = x + width
        fun top() = y + height
        fun checkDrop(grid: MutableList<MutableList<Boolean>>): Boolean {
            return shape.none {
                grid[this.x + it.first].getOrNull(this.y + it.second - 1) ?: (this.y + it.second - 1 < 1)
            }
        }
        fun checkMove(grid: MutableList<MutableList<Boolean>>, newX: Int): Boolean {
            return shape.none {
                grid[newX + it.first].getOrNull(this.y + it.second) ?: false
            }
        }
    }

    object Shapes {
        val LINE = listOf(
            0 to 0,
            1 to 0,
            2 to 0,
            3 to 0
        )
        val CROSS = listOf(
            1 to 0,
            0 to 1, 1 to 1, 2 to 1,
            1 to 2
        )
        val L = listOf(
            2 to 2,
            2 to 1,
            2 to 0,
            1 to 0,
            0 to 0
        )
        val I = listOf(
            0 to 0,
            0 to 1,
            0 to 2,
            0 to 3
        )
        val O = listOf(
            0 to 0, 1 to 0,
            0 to 1, 1 to 1
        )
    }
}
