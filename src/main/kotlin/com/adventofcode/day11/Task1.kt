package com.adventofcode.day11

import java.util.function.Predicate

class Task1 {
    fun solution(input: String): Int? {
        val monkeys = input.split("\n\n").filter { it.isNotEmpty() }
            .map { it.split("\n").filter { it.isNotEmpty() } }
            .map { it.toMonkey() }

        repeat(20) {
            for (m in monkeys) {
                for (i in m.items) {
                    m.inspections++
                    val priority = m.operation(i) / 3
                    if (m.test.test(priority)) {
                        monkeys[m.throwToTrue].items.add(priority)
//                        println("item: $i, newP: $priority, to: ${m.throwToTrue}")
                    } else {
                        monkeys[m.throwToFalse].items.add(priority)
//                        println("item: $i, newP: $priority, to: ${m.throwToFalse}")
                    }
                }
                m.items.clear()
            }

//            println(it)
//            println(monkeys.map { it.items })
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
        println("parsing: $this")
        return Monkey(
            this[1]. split("items: ")[1].split(", ").map{ it.toInt() }.toMutableList(),
            this[2].split("=")[1].parseOperation(),
            this[3].parsePredicate(),
            this[4].parseResult(),
            this[5].parseResult()
        )
    }

    private fun String.parseResult(): Int {
        return this.split(" ").last().toInt()
    }

    private fun String.parsePredicate(): (Int) -> Boolean {
        return {it:Int ->  it % this.split(" ").last().toInt() == 0 }
    }

    private fun String.parseOperation(): (Int) -> Int {
        if (this.contains("*")) {
            val numbers = this.trim().split(" * ")
            if (numbers[0] == "old" && numbers[1] == "old") {
                return { it * it }
            } else {
                return { it * numbers[1].toInt()}
            }
        } else {
            return { it + this.trim().split(" + ")[1].toInt() }
        }
    }

    class Monkey(
        val items: MutableList<Int>,
        val operation: Function1<Int, Int>,
        val test: Predicate<Int>,
        val throwToTrue: Int,
        val throwToFalse: Int
    ) {
        var inspections = 0
    }

}
