package com.adventofcode.day7

class Task1 {
    fun solution(input: String): Int? {
        val dirs = Tree("/", null)
        input.split("\n")
            .filter { it.isNotEmpty() }
            .fold(dirs) { acc, it -> it.toCommand(acc, dirs) }

        return dirs.findSmaller(100000).sumOf { it.size.value }

    }

    data class Tree(val name: String, val parent: Tree?) {
        val children: MutableList<Tree> = listOf<Tree>().toMutableList()
        val files: MutableList<File> = listOf<File>().toMutableList()
        val size: Lazy<Int> = lazy { children.sumOf { it.size.value } + files.sumOf{ it.size } }

        fun up() = parent ?: throw IllegalStateException()
        fun cd(dir: String): Tree = children.filter { it.name == dir }.first()
        fun addChild(name: String): Tree {
            children.add(Tree(name, this))
            return this
        }
        fun addFile(name: String, size: Int): Tree {
            files.add(File(size))
            return this
        }
    }

    data class File(val size: Int)

    private fun String.toCommand(current: Tree, root: Tree): Tree {
        val parts = this.split(" ")
//        println(parts)
        return when(parts[0]) {
            "$" -> when(parts[1]) {
                "cd" -> when(parts[2]) {
                    "/" -> root
                    ".." -> current.up()
                    else -> current.cd(parts[2])
                }
                else -> current
            }
            "dir" -> current.addChild(parts[1])
            else -> current.addFile(parts[1], parts[0].toInt())
        }//.also { println(it.files) }
    }

    private fun Task1.Tree.findSmaller(limit: Int): List<Task1.Tree> {
        return ((if (size.value < limit) listOf(this) else listOf()) + this.children.flatMap { it.findSmaller(limit) })
    }
}
