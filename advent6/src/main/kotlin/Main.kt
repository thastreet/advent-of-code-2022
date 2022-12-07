import java.io.File

fun main(args: Array<String>) {
    val input = File("input.txt").readText()

    val part1Answer = part1(input)
    println("part1Answer: $part1Answer")

    val part2Answer = part2(input)
    println("part2Answer: $part2Answer")
}

fun part1(input: String): Int = positionOfMarker(input, 4)

fun part2(input: String): Int = positionOfMarker(input, 14)

fun positionOfMarker(input: String, size: Int): Int =
    input.windowed(size).indexOfFirst { it.toCharArray().distinct().size == size } + size