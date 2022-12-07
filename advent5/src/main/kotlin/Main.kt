import java.io.File
import java.util.Stack

data class Command(
    val count: Int,
    val from: Int,
    val to: Int
)

fun main(args: Array<String>) {
    val lines = File("input.txt").readLines()
    val commands = parseCommands(lines)

    val part1Result = part1(getStacks(), commands)
    println("part1Result: $part1Result")

    val part2Result = part2(getStacks(), commands)
    println("part2Result: $part2Result")
}

fun getStacks(): List<Stack<Char>> =
    listOf(
        stackTopToBottom('V', 'Q', 'W', 'M', 'B', 'N', 'Z', 'C'),
        stackTopToBottom('B', 'C', 'W', 'R', 'Z', 'H'),
        stackTopToBottom('J', 'R', 'Q', 'F'),
        stackTopToBottom('T', 'M', 'N', 'F', 'H', 'W', 'S', 'Z'),
        stackTopToBottom('P', 'Q', 'N', 'L', 'W', 'F', 'G'),
        stackTopToBottom('W', 'P', 'L'),
        stackTopToBottom('J', 'Q', 'C', 'G', 'R', 'D', 'B', 'V'),
        stackTopToBottom('W', 'B', 'N', 'Q', 'Z'),
        stackTopToBottom('J', 'T', 'G', 'C', 'F', 'L', 'H')
    )

fun parseCommands(lines: List<String>): List<Command> =
    lines.map { line ->
        val parts = line
            .replace("move", "")
            .replace("from", ";")
            .replace("to", ";")
            .replace(" ", "")
            .split(";")

        Command(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
    }

fun part1(stacks: List<Stack<Char>>, commands: List<Command>): String {
    commands.forEach { command ->
        repeat(command.count) {
            stacks[command.to - 1].push(stacks[command.from - 1].pop())
        }
    }

    return getMessage(stacks)
}

fun part2(stacks: List<Stack<Char>>, commands: List<Command>): String {
    commands.forEach { command ->
        val temp = Stack<Char>()

        repeat(command.count) {
            temp.push(stacks[command.from - 1].pop())
        }

        repeat(command.count) {
            stacks[command.to - 1].push(temp.pop())
        }
    }

    return getMessage(stacks)
}

fun getMessage(stacks: List<Stack<Char>>): String =
    stacks.map { it.peek() }.joinToString(separator = "")

fun stackTopToBottom(vararg chars: Char): Stack<Char> =
    Stack<Char>().apply {
        chars.reversed().forEach {
            push(it)
        }
    }