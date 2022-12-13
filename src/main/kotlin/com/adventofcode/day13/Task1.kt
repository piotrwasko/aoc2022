package com.adventofcode.day13

import java.lang.IllegalStateException


class Task1 {
    fun solution(input: String): Int? {
        return input.split("\n\n")
            .filter { it.isNotEmpty() }
            .map { it.split("\n").filter { it.isNotEmpty() }.map { it.parsePacket() } }
            .mapIndexed { i, pair ->
                val cmp = compare(pair[0], pair[1])
                if (cmp == null) {
                    throw IllegalStateException("Equal!")
                } else if (cmp) {
                    (i + 1) to true
                } else {
                    (i + 1) to false
                }
            }
            .map { println(it); it }
            .filter { it.second }
            .sumOf { it.first }
    }
    private fun compare(left: Packet, right: Packet): Boolean? {
        if (left is PValue && right is PValue) {
            if (left.value == right.value) {
                return null
            }
            return left.value < right.value
        }
        if (left is PList && right is PList) {
            for (pair in left.list.zip(right.list)) {
                val cmp = compare(pair.first, pair.second)
                if (cmp != null) {
                    return cmp
                }
            }
            if (left.list.size == right.list.size) {
                return null
            }
            return left.list.size < right.list.size
        }
        if (left is PValue && right is PList) {
            return compare(PList(listOf(left)), right)
        }
        if (left is PList && right is PValue) {
            return compare(left, PList(listOf(right)))
        }
        throw IllegalStateException("Should be equal")
    }

    private fun String.parsePacket(): Packet {
//        println("Parsing: `$this`")
        return if (this.startsWith("[")) {
            this.parseList().second
        } else {
            PValue(this.toInt())
        }
    }


    private fun String.parseList(): Pair<String, PList> {
        assert(this.startsWith("["))
//        println("parsing list from `$this`")
        var current = this.drop(1)
        val list = listOf<Packet>().toMutableList()
        while (!current.startsWith("]")) {
            if (current.startsWith(",")) {
                current = current.drop(1)
            } else if (current.startsWith("[")) {
                val p = current.parseList()
                list.add(p.second)
                current = p.first
            } else {
                val p = current.parseInt()
                current = p.first
                list.add(p.second)
            }
        }
        return current.drop(1) to PList(list)
    }

    private fun String.parseInt(): Pair<String, PValue> {
//        println("Parsing int from `$this`")
        var current = this
        var number: String = ""
        while (current[0].isDigit()) {
            number += current[0]
            current = current.drop(1)
        }
        return current to PValue(number.toInt())
    }

    sealed class Packet

    class PList(val list: List<Packet>): Packet()

    class PValue(val value: Int): Packet()
}
