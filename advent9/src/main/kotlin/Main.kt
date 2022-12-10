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

    val part2Answer = part2(commands)
    println("part2Answer: $part2Answer")
}

fun parseCommands(lines: List<String>): List<Command> =
    lines.map {
        val parts = it.split(" ")
        Command(parts[0][0], parts[1].toInt())
    }

fun part1(commands: List<Command>): Int = simulate(commands, 1)

fun part2(commands: List<Command>): Int = simulate(commands, 9)

fun simulate(commands: List<Command>, tailsCount: Int): Int {
    var head = Position(0, 0)

    val tails = MutableList(tailsCount) {
        Position(0, 0)
    }

    val tailVisited = mutableSetOf(tails.last())

    commands.forEach { command ->
        repeat(command.steps) {
            when (command.direction) {
                'R' -> head = head.incrementX()
                'L' -> head = head.decrementX()
                'U' -> head = head.decrementY()
                'D' -> head = head.incrementY()
            }

            tails.forEachIndexed { index, position ->
                if (index == 0) {
                    tails[0] = simulateNewPositions(head, position)
                } else {
                    tails[index] = simulateNewPositions(tails[index - 1], tails[index])
                }
            }

            tailVisited.add(tails.last())
        }
    }

    return tailVisited.size
}

fun simulateNewPositions(position1: Position, position2: Position): Position {
    val distance = position1.distanceFrom(position2)
    var newPosition = position2

    if (distance.horizontal > 1 || distance.horizontal < -1) {
        newPosition = when {
            distance.horizontal > 1 -> newPosition.incrementX()
            else -> newPosition.decrementX()
        }

        when {
            distance.vertical >= 1 -> newPosition = newPosition.incrementY()
            distance.vertical <= -1 -> newPosition = newPosition.decrementY()
        }
    } else if (distance.vertical > 1 || distance.vertical < -1) {
        newPosition = when {
            distance.vertical > 1 -> newPosition.incrementY()
            else -> newPosition.decrementY()
        }

        when {
            distance.horizontal >= 1 -> newPosition = newPosition.incrementX()
            distance.horizontal <= -1 -> newPosition = newPosition.decrementX()
        }
    }

    val newDistance = position1.distanceFrom(newPosition)
    if (abs(newDistance.horizontal) > 1 && abs(newDistance.vertical) > 1) {
        throw IllegalStateException()
    }

    return newPosition
}