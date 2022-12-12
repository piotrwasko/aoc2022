package com.adventofcode.day11

import java.util.function.Predicate

class Task2 {
    fun solution(input: String): Long? {
        val monkeys = input.split("\n\n").filter { it.isNotEmpty() }
            .map { it.split("\n").filter { it.isNotEmpty() } }
            .map { it.toMonkey() }

        val divisiblity = lcm(monkeys.map { it.divisibleBy }.toLongArray())

        repeat(10000) {
            for (m in monkeys) {
                for (i in m.items) {
                    m.inspections++
                    val priority = m.operation(i) % divisiblity
                    if (m.test.test(priority)) {
                        monkeys[m.throwToTrue.toInt()].items.add(priority)
//                        prLongln("item: $i, newP: $priority, to: ${m.throwToTrue}")
                    } else {
                        monkeys[m.throwToFalse.toInt()].items.add(priority)
//                        prLongln("item: $i, newP: $priority, to: ${m.throwToFalse}")
                    }
                }
                m.items.clear()
            }

//            prLongln(it)
//            prLongln(monkeys.map { it.items })
        }

        val insp = monkeys.sortedBy { it.inspections }.takeLast(2).map { it.inspections }
        return insp[0] * insp[1]
    }


    private fun List<String>.toMonkey(): Monkey {
        /*
    Monkey 0:
      Starting items: 79, 98
      Operation: new = old * 19
      Test: divisible by 23
        If true: throw to monkey 2
        If false: throw to monkey 3
        */
//        prLongln("parsing: $this")
        return Monkey(
            this[1]. split("items: ")[1].split(", ").map{ it.toLong() }.toMutableList(),
            this[2].split("=")[1].parseOperation(),
            this[3].parsePredicate(),
            this[4].parseResult(),
            this[5].parseResult(),
            this[3].split(" ").last().toLong()
        )
    }

    private fun String.parseResult(): Long {
        return this.split(" ").last().toLong()
    }

    private fun String.parsePredicate(): (Long) -> Boolean {
        return {it:Long ->  it % this.split(" ").last().toLong() == 0L }
    }

    private fun String.parseOperation(): (Long) -> Long {
        if (this.contains("*")) {
            val numbers = this.trim().split(" * ")
            if (numbers[0] == "old" && numbers[1] == "old") {
                return { it * it }
            } else {
                return { it * numbers[1].toLong()}
            }
        } else {
            return { it + this.trim().split(" + ")[1].toLong() }
        }
    }

    class Monkey(
        val items: MutableList<Long>,
        val operation: Function1<Long, Long>,
        val test: Predicate<Long>,
        val throwToTrue: Long,
        val throwToFalse: Long,
        val divisibleBy: Long
    ) {
        var inspections: Long = 0
    }

    private fun gcd(a: Long, b: Long): Long {
        var a = a
        var b = b
        while (b > 0) {
            val temp = b
            b = a % b // % is remainder
            a = temp
        }
        return a
    }

    private fun lcm(a: Long, b: Long): Long {
        return a * (b / gcd(a, b))
    }

    private fun lcm(input: LongArray): Long {
        var result = input[0]
        for (i in 1 until input.size) result = lcm(result, input[i])
        return result
    }
}
