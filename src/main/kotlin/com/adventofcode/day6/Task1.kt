package com.adventofcode.day6

import java.util.HashMap

class Task1 {
    fun solution(input: String): Int? {
        val letters = HashMap<Char, Int>()
        var start = 0
        var end = 0
        while (end - start + 1 <= 4) {
            val lastOcc = letters[input[end]]
            letters[input[end]] = end
            if (lastOcc == null || lastOcc < start) {
                end += 1
            } else {
                start = lastOcc + 1
                end += 1
            }
//            println("s: ${start}, e: $end, l: $letters")
        }
        return end
    }
}
