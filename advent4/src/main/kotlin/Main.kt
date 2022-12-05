import java.io.File

fun main(args: Array<String>) {
    val lines = File("input.txt").readLines()

    val pairs: List<Pair<IntRange, IntRange>> = parsePairs(lines)

    println("part1Answer: ${part1(pairs)}")
    println("part2Answer: ${part2(pairs)}")
}

fun parsePairs(lines: List<String>): List<Pair<IntRange, IntRange>> =
    lines.map { line ->
        val parts = line.split(",")
        val range1Parts = parts[0].split("-")
        val range2Parts = parts[1].split("-")
        IntRange(
            range1Parts[0].toInt(),
            range1Parts[1].toInt()
        ) to IntRange(
            range2Parts[0].toInt(),
            range2Parts[1].toInt()
        )
    }

fun part1(pairs: List<Pair<IntRange, IntRange>>): Int =
    pairs.count { (first, second) ->
        first.contains(second, partially = false) || second.contains(first, partially = false)
    }

fun part2(pairs: List<Pair<IntRange, IntRange>>): Int =
    pairs.count { (first, second) ->
        first.contains(second, partially = true) || second.contains(first, partially = true)
    }

fun IntRange.contains(other: IntRange, partially: Boolean): Boolean =
    if (partially)
        any { other.contains(it) }
    else
        all { other.contains(it) }