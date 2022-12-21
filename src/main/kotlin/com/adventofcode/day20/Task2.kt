package com.adventofcode.day20

class Task2 {
    fun solution(input: String): Long {
        val data = input.split("\n").filter { it.isNotEmpty() }.map{ it.toInt() }

        var prev: Node? = null
        val nodes = mutableListOf<Node>()
        data.forEach {
            val n = Node(it * 811589153L, prev)
            nodes.add(n)
            prev?.next = n
            prev = n
        }
        nodes.last().next = nodes.first()
        nodes.first().prev = nodes.last()

        repeat(10) {
            nodes.forEachIndexed { i, node ->
                if (node.value != 0L) {
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
        }
        nodes.first { it.value == 0L }.let {
            var node = it
            var sun = 0L
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

    data class Node(val value: Long, var prev: Node?, var next: Node? = null)
}
