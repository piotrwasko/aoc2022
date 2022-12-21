package com.adventofcode.day20

import java.lang.Integer.max
import java.lang.Integer.min

class Task1 {
    fun solution(input: String): Int? {
        val data = input.split("\n").filter { it.isNotEmpty() }.map{ it.toInt() }

        var prev: Node? = null
        val nodes = mutableListOf<Node>()
        data.forEach {
            val n = Node(it, prev)
            nodes.add(n)
            prev?.next = n
            prev = n
        }
        nodes.last().next = nodes.first()
        nodes.first().prev = nodes.last()

        nodes.forEachIndexed { i, node ->
            if (node.value != 0) {
                node.prev?.next = node.next
                node.next?.prev = node.prev
                var targetAfter = node.prev!!
                var toGo = node.value % (nodes.size - 1)
                while (toGo > 0) {
                    targetAfter = targetAfter.next!!
                    toGo--
                }
                while (toGo < 0) {
                    targetAfter = targetAfter.prev!!
                    toGo++
                }
                targetAfter.next!!.prev = node
                node.next = targetAfter.next
                node.prev = targetAfter
                targetAfter.next = node
            }
        }
        nodes.first { it.value == 0 }.let {
            var node = it
            var sun = 0
            repeat(1000) { node = node.next!! }
            println("Found ${node.value}")
            sun += node.value
            repeat(1000) { node = node.next!! }
            println("Found ${node.value}")
            sun += node.value
            repeat(1000) { node = node.next!! }
            println("Found ${node.value}")
            sun += node.value
            return sun
        }
    }

    data class Node(val value: Int, var prev: Node?, var next: Node? = null)

    private fun irstSol(data: List<Int>) {
        var movesLeft = 0 // all under this index
        var movesRight = 0 // all under this index
        val output = mutableMapOf<Int, MutableList<Int>>()

        data.forEachIndexed { i, num ->
            var targetPos = (i + num) % data.size
            if (targetPos < 0) { targetPos = data.size + targetPos - 1 }

            val movedBetween = output.entries.filter { it.key >= i }.sumOf{ it.value.size }
            var newPos = targetPos - movedBetween
            println("new pos for $num: $targetPos -> $newPos")
            output[newPos]?.add(num) ?: run { output[newPos] = mutableListOf(num) }
            println(output.entries.sortedBy { it.key }.map { it.value })
        }
        println(output.entries.sortedBy { it.key }.map { it.value })
    }
}
