import java.io.File

fun main(args: Array<String>) {
    val lines = File("input.txt").readLines()

    val trees: Array<Array<Int>> = parseTrees(lines)

    val part1Answer = part1(trees)
    println("part1Answer: $part1Answer")

    val part2Answer = part2(trees)
    println("part2Answer: $part2Answer")
}

fun parseTrees(lines: List<String>): Array<Array<Int>> =
    Array(lines.size) { lineIndex ->
        val line = lines[lineIndex]
        Array(line.length) { charIndex ->
            line[charIndex].digitToInt()
        }
    }

fun part1(trees: Array<Array<Int>>): Int {
    var count = 2 * trees[0].size + (trees[0].size - 2) * 2
    for (j in trees.indices) {
        for (i in trees[0].indices) {
            val row = trees[j]
            val column = Array(trees.size) {
                trees[it][i]
            }

            val tree = trees[j][i]

            val visibleFromTop = (0 until j).all { column[it] < tree }
            val visibleFromBottom = (column.size - 1 downTo j + 1).all { column[it] < tree }
            val visibleFromLeft = (0 until i).all { row[it] < tree }
            val visibleFromRight = (row.size - 1 downTo i + 1).all { row[it] < tree }

            if (j > 0 && j < trees.size - 1 && i > 0 && i < trees[0].size - 1 && (visibleFromBottom || visibleFromTop || visibleFromRight || visibleFromLeft)) {
                ++count
            }
        }
    }

    return count
}

fun part2(trees: Array<Array<Int>>): Int {
    var highest = 0

    for (j in trees.indices) {
        for (i in trees[0].indices) {
            val row = trees[j]
            val column = Array(trees.size) {
                trees[it][i]
            }

            val tree = trees[j][i]

            var top = 0
            var bottom = 0
            var left = 0
            var right = 0

            for (currentJ in j - 1 downTo 0) {
                ++top
                if (column[currentJ] >= tree) break
            }

            for (currentJ in j + 1 until column.size) {
                ++bottom
                if (column[currentJ] >= tree) break
            }

            for (currentI in i - 1 downTo 0) {
                ++left
                if (row[currentI] >= tree) break
            }

            for (currentI in i + 1 until row.size) {
                ++right
                if (row[currentI] >= tree) break
            }

            val score = top * bottom * left * right
            if (score > highest) {
                highest = score
            }
        }
    }

    return highest
}