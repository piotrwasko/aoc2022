package com.adventofcode.day21

class Task1 {
    fun solution(input: String): Long? {
        val data = input.split("\n").filter{ it.isNotEmpty() }
            .map { it.toMonkey() }
            .associateBy { it.name }

        val currentMonkeys = mutableMapOf(0 to listOf(data["root"]!!))
        var step = 0
        while (!currentMonkeys[step]!!.all { it.number != null }) {
            step++
            currentMonkeys[step] = currentMonkeys[step - 1]!!.flatMap { it.children.map { data[it]!! } }
        }

        val results = currentMonkeys[step]!!.map { it.name to it.number }.toMap().toMutableMap()
        for (s in (step - 1) downTo 0) {
                currentMonkeys[s]!!.forEach {
                    results[it.name] = it.number ?: it.operation!!(results[it.children[0]]!!, results[it.children[1]]!!)
                }
        }

        return results["root"]
    }

    private fun String.toMonkey(): Monkey {
        val a = this.split(": ")
        return when {
            a[1].contains("+") -> Monkey(a[0], {a, b -> a + b}, a[1].split(" + "), null)
            a[1].contains("-") -> Monkey(a[0], {a, b -> a - b}, a[1].split(" - "), null)
            a[1].contains("*") -> Monkey(a[0], {a, b -> a * b}, a[1].split(" * "), null)
            a[1].contains("/") -> Monkey(a[0], {a, b -> a / b}, a[1].split(" / "), null)
            else -> Monkey(a[0], null, listOf(), a[1].toLong())
        }
    }
    data class Monkey(
        val name: String,
        val operation: Function2<Long, Long, Long>?,
        val children: List<String>,
        val number: Long?
    )
}
