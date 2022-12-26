package advent_of_code._2022

import Task
import readInput
import java.util.*
import kotlin.collections.ArrayList

object Task13 : Task {

    private const val ADDITIONAL_PACKET_1 = "[[2]]"
    private const val ADDITIONAL_PACKET_2 = "[[6]]"
    private val ADDITIONAL_PACKETS = listOf(ADDITIONAL_PACKET_1, ADDITIONAL_PACKET_2)

    override fun partA() = parseInput()
        .mapIndexed { index, (left, right) ->
            index to isInRightOrder(left, right)
        }
        .filter { (_, inTheRightOrder) -> inTheRightOrder }
        .sumOf { (index) -> index + 1 }

    override fun partB() = parseInput()
        .flatten()
        .plus(ADDITIONAL_PACKETS)
        .sortedWith(this::compareTrees)
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
        return when {
            cmp > 0 -> true
            cmp < 0 -> false
            else -> error("they are the same")
        }
    }

    private fun compareTrees(left: String, right: String): Int {
        val leftTree = parseTree(left)
        val rightTree = parseTree(right)
        return compareTrees(leftTree, rightTree)
    }

    private fun compareTrees(left: TreeList, right: TreeList): Int {
        if (left.item != null && right.item != null) {
            return (right.item - left.item).coerceIn(-1, 1)
        }

        if (left.item != null) {
            val leftChild = TreeList(parent = left.parent)
            leftChild.add(TreeList(left.item, parent = leftChild))
            return compareTrees(leftChild, right)
        }

        if (right.item != null) {
            val rightChild = TreeList(parent = right.parent)
            rightChild.add(TreeList(right.item, parent = rightChild))
            return compareTrees(left, rightChild)
        }

        val leftStack = LinkedList(left.children)
        val rightStack = LinkedList(right.children)

        while (leftStack.isNotEmpty() || rightStack.isNotEmpty()) {
            if (leftStack.isEmpty()) return 1
            if (rightStack.isEmpty()) return -1

            val leftChild = leftStack.removeFirst()
            val rightChild = rightStack.removeFirst()

            val cmp = compareTrees(leftChild, rightChild)

            if (cmp != 0) {
                return cmp
            }
        }

        return 0
    }

    data class TreeList(val item: Int? = null, val parent: TreeList?) {
        val children: ArrayList<TreeList> = ArrayList()

        fun add(child: TreeList) {
            if (item != null) error("tree list item is defined, cannot add child")
            children.add(child)
        }

        override fun toString(): String {
            if (item != null) return item.toString()

            return children
                .joinToString(",")
                .let {
                    if (parent != null) "[$it]" else it
                }
        }
    }

}