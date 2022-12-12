package com.adventofcode.input

object Input {

    fun load(day: Int, file: String = "input.txt") = Input::class.java.getResource("/inputs/day${day}/${file}").readText()
}
