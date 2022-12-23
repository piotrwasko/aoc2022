package com.adventofcode.day19

import java.util.Comparator
import java.util.PriorityQueue

class Task2 {
    fun solution(input: String): Int? {
        val blueprints = input.split("\n")
            .filter { it.isNotEmpty() }
            .map { it.toBlueprint() }
            .take(3)

        val start = State(
            mutableMapOf(Mat.ORE to 1, Mat.CLAY to 0, Mat.OBSIDIAN to 0, Mat.GEODE to 0),
            Store(mutableMapOf(Mat.ORE to 0, Mat.CLAY to 0, Mat.OBSIDIAN to 0, Mat.GEODE to 0)),
            32
        )



        return blueprints.map { blueprint ->
            println("Next Blueprint")
            val toCheck = PriorityQueue<State>(Comparator.comparing { it.time })
            toCheck.add(start)
            var bestGeodeScore = 0

            while (toCheck.isNotEmpty()) {
                val state = toCheck.remove()
                if (state.time == 0) {
                    if (bestGeodeScore < state.mat(Mat.GEODE)) {
                        println("Best score: ${state.mat(Mat.GEODE)} for state $state")
                    }
                    bestGeodeScore = Integer.max(state.mat(Mat.GEODE), bestGeodeScore)
                    continue
                }

                val possibleBuildStates = findPossibleBuildStates(blueprint, state)
                if (possibleBuildStates.isEmpty()) {
                    toCheck.add(state.copy(
                        store = state.store.add(state.time, state.robots),
                        time = 0
                    ))
                }
                toCheck.addAll(possibleBuildStates.filter { it.potentialScore() >= bestGeodeScore })
            }

            bestGeodeScore
        }.reduce{ a, b -> a * b }
    }

    private fun findPossibleBuildStates(blueprint: Blueprint, state: State): List<State> {
        return Mat.values()
            .filter { mat -> blueprint.shouldBuild(mat, state) && blueprint.costs[mat]!!.all { state.robots[it.first]!! > 0 } }
            .mapNotNull {  mat ->
                val waitFor = blueprint.costs[mat]!!
                    .filter { it.second > state.store[it.first]!! }
                    .maxOfOrNull { ((it.second - state.store[it.first]!!) / state.robots[it.first]!!) + (if ((it.second - state.store[it.first]!!) % state.robots[it.first]!! == 0) 0 else 1) }
                    ?: 0
//                println("$state, build $mat after $waitFor")
                if (waitFor + 1 < state.time) {
                    state.copy(
                        robots = state.robots.map { it.key to it.value + (if (it.key == mat) 1 else 0) }.toMap(),
                        store = state.store.add(waitFor + 1, state.robots).minus(1, blueprint.costs[mat]!!),
                        time = state.time - waitFor - 1
                    )
                } else {
                    null
                }
            }
    }

    data class State(
        val robots: Map<Mat, Int>,
        val store: Store,
        var time: Int
    ) {
        fun mat(m: Mat) = store[m]!!
        fun potentialScore(): Int {
            return store[Mat.GEODE]!! + (robots[Mat.GEODE]!! * time) + ((time + 1) * time/2)
        }
    }

    data class Store(
        val materials: Map<Mat, Int>
    ): Map<Mat, Int> by materials {
        fun minus(times: Int, costs: List<Pair<Mat, Int>>): Store {
            val newMats = materials.map { e -> e.key to e.value - times * (costs.find{ it.first == e.key}?.second ?: 0) }.toMap()
            return Store(
                newMats
            )
        }

        fun add(times: Int, newMats: Map<Mat, Int>) = Store(materials.map { e -> e.key to (e.value + times * newMats[e.key]!!) }.toMap())
    }

    data class Blueprint(
        val id: Int,
        val costs: Map<Mat, List<Pair<Mat, Int>>>
    ) {
        fun shouldBuild(ore: Mat, state: State): Boolean {
            val maxNeeded = if (ore == Mat.GEODE) 1000 else costs.values.flatten().filter { it.first == ore }.maxOf{ it.second }
            if (state.robots[ore]!! >= maxNeeded) {
                return false
            }
            return true
        }
    }

    enum class Mat {
        ORE,
        CLAY,
        OBSIDIAN,
        GEODE
    }

    private fun String.toBlueprint(): Blueprint {
        val a = this.split(": ")
        val b = a[1].split(". ").filter { it.isNotEmpty() }
        return Blueprint(
            a[0].split(" ")[1].toInt(),
            b.associate { it.toCost() }
        )

    }

    private fun String.toCost(): Pair<Mat, List<Pair<Mat, Int>>> {
        val a = this.split("costs ")
        val mat = Mat.valueOf(a[0].split(" ")[1].uppercase())
        val costs = a[1]
            .replace(".", "")
            .split(" and ")
            .map{ it.split(" ") }.map { Mat.valueOf(it[1].uppercase()) to it[0].toInt() }
        return mat to costs
    }

}
