package _2022

import Task
import readInput
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

object Task13 : Task {

    private const val ADDITIONAL_PACKET_1 = "[[2]]"
    private const val ADDITIONAL_PACKET_2 = "[[6]]"

    override fun partA() = parseInput()
        .mapIndexed { index, (left, right) ->
            val inTheRightOrder = isInRightOrder(left, right)

            inTheRightOrder to index + 1
        }
        .filter { (inTheRightOrder) -> inTheRightOrder }
        .sumOf { (_, index) -> index }

    override fun partB() = parseInput()
        .flatten()
        .plus(listOf("[[2]]", "[[6]]"))
        .sortedWith { left, right -> compareTrees(left, right) }
        .reversed()
        .let {
            val index1 = it.indexOf(ADDITIONAL_PACKET_1) + 1
            val index2 = it.indexOf(ADDITIONAL_PACKET_2) + 1
            index1 * index2
        }

    private fun parseInput() = readInput("_2022/13")
        .split("\n\n").map {
            it.split("\n")
        }

    fun parseTree(input: String): TreeList {
        val root = TreeList(parent = null)
        var current = root
        var int = ""

        fun addInt() {
            if (int != "") {
                val item = TreeList(int.toInt(), parent = current)
                current.add(item)
                int = ""
            }
        }

        input.forEach { char ->
            when {
                char == '[' -> {
                    val newTree = TreeList(parent = current)
                    current.add(newTree)
                    current = newTree
                }

                char == ']' -> {
                    addInt()

                    val parent = current.parent

                    if (parent != null) {
                        current = parent
                    }
                }

                char == ',' -> {
                    addInt()
                }

                char.isDigit() -> {
                    int += char
                }
            }
        }

        return root
    }

    fun isInRightOrder(left: String, right: String): Boolean {
        val cmp = compareTrees(left, right)

        return when (cmp) {
            1 -> true
            -1 -> false
            0 -> error("0 -> they are the same?")
             else -> error("other value -> $cmp")
        }
    }

    fun compareTrees(left: String, right: String): Int {
        val leftTree = parseTree(left)
        val rightTree = parseTree(right)

        return compare(leftTree, rightTree)
    }

    private fun compare(left: TreeList, right: TreeList): Int {
        println(" - Compare $left vs $right")

        if (left.item != null && right.item != null) {
            val delta = left.item - right.item

            if (left.item < right.item) {
                println(" - Left side is smaller, so inputs are in the right order")
                return 1
            } else if (left.item > right.item) {
                println(" - Right side is smaller, so inputs are not in the right order")
                return -1
            } else {
                // continue checking
                return 0
            }
        }

        if (left.item == null && right.item == null) {
            val leftStack = LinkedList(left.children)
            val rightStack = LinkedList(right.children)

            while (leftStack.isNotEmpty() || rightStack.isNotEmpty()) {
                if (leftStack.isEmpty()) {
                    println(" - Left side ran out of items, so inputs are in the right order")
                    return 1
                }

                if (rightStack.isEmpty()) {
                    println(" - Right side ran out of items, so inputs are not in the right order")
                    return -1
                }

                val leftChild = leftStack.removeFirst()
                val rightChild = rightStack.removeFirst()

                val cmp = compare(leftChild, rightChild)

                if (cmp != 0) {
                    return cmp
                }
            }
        }

        if (left.item != null && right.item == null) {
            // create new left tree with list of item instead of item
            val newLeft = TreeList(parent = left.parent)
            newLeft.add(TreeList(left.item, parent = newLeft))
            return compare(newLeft, right)
        }

        if (left.item == null && right.item != null) {
            // create new right tree with list of item instead of item
            val newRight = TreeList(parent = right.parent)
            newRight.add(TreeList(right.item, parent = newRight))
            return compare(left, newRight)
        }

//        return left.children.size - right.children.size
        return 0
    }

    data class TreeList(val item: Int? = null, val parent: TreeList?) {
        val children: ArrayList<TreeList> = ArrayList()

        fun add(child: TreeList) {
            if (item != null) error("tree list item is defined, cannot add child")
            children.add(child)
        }

        override fun toString(): String {
            if (item != null) {
                return item.toString()
            }

            val ch = children.joinToString(",")

            return if (parent != null) {
                return "[$ch]"
            } else {
                ch
            }
        }
    }

}