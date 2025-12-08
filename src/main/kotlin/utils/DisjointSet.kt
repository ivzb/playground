package utils

class DisjointSet(n: Int) {

    private val parent = IntArray(n) { it }

    private val size = IntArray(n) { 1 }

    fun find(i: Int): Int {
        if (parent[i] == i) {
            return i
        }

        parent[i] = find(parent[i])

        return parent[i]
    }

    fun union(i: Int, j: Int): Boolean {
        val rootI = find(i)
        val rootJ = find(j)

        if (rootI != rootJ) {
            if (size[rootI] < size[rootJ]) {
                parent[rootI] = rootJ
                size[rootJ] += size[rootI]
            } else {
                parent[rootJ] = rootI
                size[rootI] += size[rootJ]
            }

            return true
        }

        return false
    }

    fun getAllComponentSizes(): List<Int> {
        return parent
            .indices
            .filter { parent[it] == it }
            .map { size[it] }
    }
}
