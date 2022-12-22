package com.adventofcode.day22

class Task2 {
    fun solution(input: String): Int? {
        val data = input.split("\n\n").filter { it.isNotEmpty() }
        val board = data[0].split("\n").filter { it.isNotEmpty() }
            .map { it.toRow() }
        val track = data[1].toTrack() + Track(Dir.RIGHT, 49)
//        println(track.map { it.movement.toString() + it.dir.name[0] }.joinToString(""))
        
        var currentPos = Pos(0, board[0].keys.first(), Dir.RIGHT)
        track.forEach { m ->
            var movementLeft = m.movement
            while (movementLeft > 0) {
                if (currentPos.dir == Dir.RIGHT) {
                    val newPos: Pos = if (currentPos.col == board[currentPos.row].keys.max()) {
                        wrapPos(currentPos)
                    } else {
                        currentPos.copy(col = currentPos.col + 1)
                    }
                    if (board[newPos.row][newPos.col] is Wall) {
                        break
                    }
                    currentPos = newPos
                } else if (currentPos.dir == Dir.LEFT) {
                    val nextPos: Pos = if (currentPos.col == board[currentPos.row].keys.min()) {
                        wrapPos(currentPos)
                    } else {
                        currentPos.copy(col = currentPos.col - 1)
                    }
                    if (board[nextPos.row][nextPos.col] is Wall) {
                        break
                    }
                    currentPos = nextPos
                } else if (currentPos.dir == Dir.UP) {
                    val nextPos: Pos = if (currentPos.row == 0 || currentPos.col !in board[currentPos.row - 1].keys) {
                        wrapPos(currentPos)//.also { println("Wrapping up: $currentPos.row, $currentPos.col -> $it, $currentPos.col") }
                    } else {
                        currentPos.copy(row = currentPos.row - 1)
                    }
                    if (board[nextPos.row][nextPos.col] is Wall) {
                        break
                    }
                    currentPos = nextPos
                } else if (currentPos.dir == Dir.DOWN) {
                    val nextPos: Pos = if (currentPos.row == board.size - 1 || currentPos.col !in board[currentPos.row + 1].keys) {
                        wrapPos(currentPos)//.also { println("Wrapping down: $currentPos.row, $currentPos.col -> $it, $currentPos.col") }
                    } else {
                        currentPos.copy(row = currentPos.row + 1)
                    }
                    if (board[nextPos.row][nextPos.col] is Wall) {
                        break
                    }
                    currentPos = nextPos
                }
                movementLeft--
            }
            currentPos = currentPos.copy(dir = currentPos.dir.turn(m.dir))
            println("After $m Went to $currentPos")
        }

        return 1000 * (currentPos.row + 1) + 4 * (currentPos.col + 1) + currentPos.dir.value
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

    private fun wrapPos(p: Pos): Pos {
        return when(p.dir) {
            Dir.UP -> when (p.row) {
                0 -> when {
                    p.col in 50..99 -> Pos(150 + p.col - 50, 0, Dir.RIGHT)
                    p.col in 100..149 ->Pos(199, p.col - 100, Dir.UP)
                    else -> throw IllegalStateException()
                }
                100 -> Pos(50 + p.col, 50, Dir.RIGHT)
                else -> throw IllegalStateException()
            }
            Dir.RIGHT -> when (p.col) {
                49 -> Pos(149, p.row - 150 + 50, Dir.UP)
                99 -> when {
                    p.row in 50..99 -> Pos(49, p.row - 50 + 100, Dir.UP)
                    p.row in 100..149 -> Pos(49 - (p.row - 100), 149, Dir.LEFT)
                    else -> throw IllegalStateException()
                }
                149 -> Pos(149 - p.row, 99, Dir.LEFT)
                else -> throw IllegalStateException()
            }
            Dir.DOWN -> when(p.row) {
                49 -> Pos(p.col - 100 + 50, 99, Dir.LEFT)
                149 -> Pos(p.col - 50 + 150, 49, Dir.LEFT)
                199 -> Pos(0, p.col + 100, Dir.DOWN)
                else -> throw IllegalStateException()
            }
            Dir.LEFT -> when(p.col) {
                0 -> when {
                    p.row in 100..149 -> Pos(49 - (p.row - 100), 50, Dir.RIGHT)
                    p.row in 150..199 -> Pos(0, p.row - 150 + 50, Dir.DOWN)
                    else -> throw IllegalStateException()
                }
                50 -> when {
                    p.row in 0..49 -> Pos(100 + (49 - p.row), 0, Dir.RIGHT)
                    p.row in 50..99 -> Pos(100, p.row - 50, Dir.DOWN)
                    else -> throw IllegalStateException()
                }
                else -> throw IllegalStateException()
            }
        }
    }

    private sealed class Field(val y: Int)
    private class Floor(y: Int): Field(y)
    private class Wall(y: Int): Field(y)

    private data class Pos(val row: Int, val col: Int, val dir: Dir)
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
