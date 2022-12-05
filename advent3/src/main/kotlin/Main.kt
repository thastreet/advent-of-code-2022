import java.io.File

val lowercase = 'a'..'z'
val uppercase = 'A'..'Z'

fun main(args: Array<String>) {
    val lines = File("input.txt").readLines()

    println("Part 1 answer: ${part1(lines)}")
    println("Part 2 answer: ${part2(lines)}")
}

val Char.priority: Int
    get() = when (this) {
        in lowercase -> lowercase.indexOf(this) + 1
        in uppercase -> uppercase.indexOf(this) + 27
        else -> 0
    }

fun part1(lines: List<String>): Int =
    lines.sumOf { line ->
        val first = line.subSequence(0, line.length / 2)
        val second = line.subSequence(line.length / 2, line.length)

        first.first { second.contains(it) }.priority
    }

fun part2(lines: List<String>): Int =
    lines.chunked(3).sumOf { group ->
        val first = group[0]
        val second = group[1]
        val third = group[2]

        first.first { second.contains(it) && third.contains(it) }.priority
    }