package com.adventofcode.day25

import java.util.Comparator

class Task1 {
    fun solution(input: String): String {
        val sum = input.split("\n")
            .filter { it.isNotEmpty() }
            .map { ElfInt.parse(it) }
            .sumOf { it.x }

        println("Sum: $sum")

        return ElfInt(sum).toString()
    }

    private data class ElfInt(val x: Long) {

        override fun toString(): String {
            var res = ""
            var left = x
            var carry = 0
            while (left > 0) {
                var rest = left % 5
                left /= 5
                when (rest + carry) {
                    0L -> {res += '0'; carry = 0}
                    1L -> {res += '1'; carry = 0}
                    2L -> {res += '2'; carry = 0}
                    3L -> {res += '='; carry = 1}
                    4L -> {res += '-'; carry = 1}
                    5L -> {res += '0'; carry = 1}
                }
            }
            return res.reversed()
        }

        companion object {
            fun parse(s: String): ElfInt {
                var x = 0L
                s.forEach {
                    val digit = when(it) {
                        '0' -> 0
                        '1' -> 1
                        '2' -> 2
                        '-' -> -1
                        '=' -> -2
                        else -> throw IllegalStateException()
                    }
                    x = (x * 5) + digit
                }
                return ElfInt(x)
            }
        }
    }
}
