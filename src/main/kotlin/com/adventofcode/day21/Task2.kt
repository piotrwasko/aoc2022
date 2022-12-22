package com.adventofcode.day21

import java.lang.IllegalStateException

class Task2 {
    fun solution(input: String): Long? {
        val data = input.split("\n").filter{ it.isNotEmpty() }
            .map { it.toMonkey() }
            .associateBy { it.name }

        val parents = mutableMapOf<String, String>()
        data.values.forEach { m ->
            m.children.forEach { c -> parents[c] = m.name }
        }

        var mySide = "humn"
        val myPath = mutableListOf("humn")
        while (parents[mySide] != "root") {
            mySide = parents[mySide]!!
            myPath.add(mySide)
        }

        val otherSide = data["root"]!!.children.first { it != mySide }
        val currentMonkeys = mutableMapOf(0 to listOf(data["root"]!!))
        var step = 0
        while (!currentMonkeys[step]!!.all { it.number != null }) {
            step++
            currentMonkeys[step] = currentMonkeys[step - 1]!!.flatMap { it.children.map { data[it]!! } }
        }

        val results = currentMonkeys[step]!!.map { it.name to it.number }.toMap().toMutableMap()
        for (s in (step - 1) downTo 0) {
            currentMonkeys[s]!!.filter { it.name !in myPath && it.name != "root"}.forEach {
                results[it.name] = it.number ?: it.operation!!(results[it.children[0]]!!, results[it.children[1]]!!)
            }
        }

        var target = results[otherSide]!!
        var currentSide = mySide
        var currentMonkey: Monkey
        while (currentSide != "humn") {
            currentMonkey = data[currentSide]!!
            target = currentMonkey.reverseOp(target, myPath, results)
            currentSide = currentMonkey.children.first { it in myPath }
        }

        return target
    }

    private fun String.toMonkey(): Monkey {
        val a = this.split(": ")
        return when {
            a[1].contains("+") -> Monkey(a[0], {a, b -> a + b}, a[1].split(" + "), null, "+")
            a[1].contains("-") -> Monkey(a[0], {a, b -> a - b}, a[1].split(" - "), null, "-")
            a[1].contains("*") -> Monkey(a[0], {a, b -> a * b}, a[1].split(" * "), null, "*")
            a[1].contains("/") -> Monkey(a[0], {a, b -> a / b}, a[1].split(" / "), null, "/")
            else -> Monkey(a[0], null, listOf(), a[1].toLong(), null)
        }
    }
    data class Monkey(
        val name: String,
        val operation: Function2<Long, Long, Long>?,
        val children: List<String>,
        val number: Long?,
        val op: String?
    ) {
        fun reverseOp(target: Long, myPath: MutableList<String>, results: MutableMap<String, Long?>): Long {
            if (this.children[0] !in myPath) {
                return when (op) {
                    "+" -> target - results[this.children[0]]!!
                    "-" -> results[this.children[0]]!! - target
                    "*" -> target / results[this.children[0]]!!
                    "/" -> results[this.children[0]]!! / target
                    else -> throw IllegalStateException()
                }
            } else {
                return when (op) {
                    "+" -> target - results[this.children[1]]!!
                    "-" -> target + results[this.children[1]]!!
                    "*" -> target / results[this.children[1]]!!
                    "/" -> results[this.children[1]]!! * target
                    else -> throw IllegalStateException()
                }
            }
        }
    }
}
