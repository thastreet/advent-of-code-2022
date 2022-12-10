import java.io.File
import kotlin.math.abs

data class Command(
    val direction: Char,
    val steps: Int
)

data class Position(
    val x: Int,
    val y: Int
)

fun Position.distanceFrom(other: Position) =
    Distance(x - other.x, y - other.y)

fun Position.incrementX() =
    copy(x = x + 1)

fun Position.decrementX() =
    copy(x = x - 1)

fun Position.incrementY() =
    copy(y = y + 1)

fun Position.decrementY() =
    copy(y = y - 1)

data class Distance(
    val horizontal: Int,
    val vertical: Int
)

fun main(args: Array<String>) {
    val lines = File("input.txt").readLines()
    val commands = parseCommands(lines)

    val part1Answer = part1(commands)
    println("part1Answer: $part1Answer")
}

fun parseCommands(lines: List<String>): List<Command> =
    lines.map {
        val parts = it.split(" ")
        Command(parts[0][0], parts[1].toInt())
    }

fun part1(commands: List<Command>): Int {
    var head = Position(0, 0)
    var tail = Position(0, 0)

    val tailVisited = mutableSetOf(tail)

    commands.forEach { command ->
        repeat(command.steps) {
            when (command.direction) {
                'R' -> head = head.incrementX()
                'L' -> head = head.decrementX()
                'U' -> head = head.decrementY()
                'D' -> head = head.incrementY()
            }

            val distance = head.distanceFrom(tail)

            if (distance.horizontal > 1 || distance.horizontal < -1) {
                tail = when {
                    distance.horizontal > 1 -> tail.incrementX()
                    else -> tail.decrementX()
                }

                when (distance.vertical) {
                    1 -> tail = tail.incrementY()
                    -1 -> tail = tail.decrementY()
                }
            } else if (distance.vertical > 1 || distance.vertical < -1) {
                tail = when {
                    distance.vertical > 1 -> tail.incrementY()
                    else -> tail.decrementY()
                }

                when (distance.horizontal) {
                    1 -> tail = tail.incrementX()
                    -1 -> tail = tail.decrementX()
                }
            }

            if (abs(distance.horizontal) > 1 && abs(distance.vertical) > 1) {
                throw IllegalStateException()
            }

            tailVisited.add(tail)
        }
    }

    return tailVisited.size
}