package com.adventofcode.day22

import javax.sound.midi.Track

class Task1 {
    fun solution(input: String): Int? {
        val data = input.split("\n\n").filter { it.isNotEmpty() }
        val board = data[0].split("\n").filter { it.isNotEmpty() }
            .map { it.toRow() }
        val track = data[1].toTrack() + Track(Dir.RIGHT, 49)
//        println(track.map { it.movement.toString() + it.dir.name[0] }.joinToString(""))

        var currentRow = 0
        var currentCol = board[currentRow].keys.first()
        var currentDir = Dir.RIGHT
        track.forEach { m ->
            var movementLeft = m.movement
            while (movementLeft > 0) {
                if (currentDir == Dir.RIGHT) {
                    val nextCol = if (currentCol == board[currentRow].keys.max()) {
                        board[currentRow].keys.min()
                    } else {
                        currentCol + 1
                    }
                    if (board[currentRow][nextCol] is Wall) {
                        break
                    }
                    currentCol = nextCol
                } else if (currentDir == Dir.LEFT) {
                    val nextCol = if (currentCol == board[currentRow].keys.min()) {
                        board[currentRow].keys.max()
                    } else {
                        currentCol - 1
                    }
                    if (board[currentRow][nextCol] is Wall) {
                        break
                    }
                    currentCol = nextCol
                } else if (currentDir == Dir.UP) {
                    val nextRow = if (currentRow == 0 || currentCol !in board[currentRow - 1].keys) {
                        board.indexOfLast { currentCol in it.keys }//.also { println("Wrapping up: $currentRow, $currentCol -> $it, $currentCol") }
                    } else {
                        currentRow - 1
                    }
                    if (board[nextRow][currentCol] is Wall) {
                        break
                    }
                    currentRow = nextRow
                } else if (currentDir == Dir.DOWN) {
                    val nextRow = if (currentRow == board.size - 1 || currentCol !in board[currentRow + 1].keys) {
                        board.indexOfFirst { currentCol in it.keys }//.also { println("Wrapping down: $currentRow, $currentCol -> $it, $currentCol") }
                    } else {
                        currentRow + 1
                    }
                    if (board[nextRow][currentCol] is Wall) {
                        break
                    }
                    currentRow = nextRow
                }
                movementLeft--
            }
            currentDir = currentDir.turn(m.dir)
            println("After $m Went to $currentRow, $currentCol, facing $currentDir")
        }

        return 1000 * (currentRow + 1) + 4 * (currentCol + 1) + currentDir.value
    }

    private fun String.toRow(): Map<Int, Field> {
        return this.mapIndexed { i, f -> when(f) {
            '.' -> i to Floor(i)
            '#' -> i to Wall(i)
            else -> null
        } }.filterNotNull().toMap()
    }

    private fun String.toTrack(): List<Track> {
        var movement: String? = null
        var dir: Dir? = null
        val track = mutableListOf<Track>()
        this.trim().forEach { when {
            movement == null && it.isDigit() -> movement = it.toString()
            dir == null && it.isDigit() -> movement += it
            dir != null && it.isDigit() -> {track.add(Track(dir!!, movement!!.toInt())); dir = null; movement = it.toString()}
            it == 'U' -> dir = Dir.UP
            it == 'D' -> dir = Dir.DOWN
            it == 'L' -> dir = Dir.LEFT
            it == 'R' -> dir = Dir.RIGHT
        } }
        return track
    }
    private sealed class Field(val y: Int)
    private class Floor(y: Int): Field(y)
    private class Wall(y: Int): Field(y)

    private data class Track(val dir: Dir, val movement: Int)
    private enum class Dir(val value: Int) {
        UP(3),
        RIGHT(0),
        DOWN(1),
        LEFT(2);

        fun turn(d: Dir) = when(d) {
            LEFT -> turnLeft()
            RIGHT -> turnRight()
            else -> this
        }
        fun turnRight(): Dir = when(this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        }

        fun turnLeft(): Dir = when(this) {
            UP -> LEFT
            RIGHT -> UP
            DOWN -> RIGHT
            LEFT -> DOWN
        }
    }
}
