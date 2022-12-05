import java.io.File

fun main(args: Array<String>) {
    val lines = File("input.txt").readLines()
    val elves = parseElves(lines)

    val part1Answer = elves.maxOf { it.sum() }
    println("Part 1 answer: $part1Answer")

    val part2Answer = elves.map { it.sum() }.sortedDescending().take(3).sum()
    println("Part 2 answer: $part2Answer")
}

private fun parseElves(lines: List<String>): List<List<Int>> {
    val elves = mutableListOf<List<Int>>()

    var current = mutableListOf<Int>()
    lines.forEach {
        if (it.isBlank()) {
            elves.add(current)
            current = mutableListOf()
        } else {
            current.add(it.toInt())
        }
    }

    if (current.isNotEmpty()) {
        elves.add(current)
    }

    return elves
}