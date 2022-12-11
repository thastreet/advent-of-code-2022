import java.io.File
import java.lang.IllegalArgumentException
import java.util.Stack

sealed interface Instruction {
    val cycles: Int
}

object Noop : Instruction {
    override val cycles = 1
}

data class Add(val value: Int) : Instruction {
    override val cycles = 2
}

fun main(args: Array<String>) {
    val lines = File("input.txt").readLines()
    val instructions = parseInstructions(lines)

    val part1Answer = part1(instructions)
    println("part1Answer: $part1Answer")

    val part2Answer = part2(instructions)
    println("part2Answer: $part2Answer")
}

fun parseInstructions(lines: List<String>): List<Instruction> =
    lines.map { line ->
        val parts = line.split(" ")
        when (parts[0]) {
            "noop" -> Noop
            "addx" -> Add(parts[1].toInt())
            else -> throw IllegalArgumentException("Unknown instruction: ${parts[0]}")
        }
    }

fun part1(instructions: List<Instruction>): Int {
    val positions = getPositions(instructions)
    val strengthCycles = listOf(20, 60, 100, 140, 180, 220)
    return strengthCycles.sumOf { it * positions[it - 1] }
}

fun part2(instructions: List<Instruction>): String {
    val positions = getPositions(instructions)

    positions.forEachIndexed { index, x ->
        if (index % 40 in x - 1..x + 1) {
            print("#")
        } else {
            print(".")
        }

        if ((index + 1) % 40 == 0) {
            println()
        }
    }

    return "ECZUZALR"
}

fun getPositions(instructions: List<Instruction>): List<Int> {
    val stack = Stack<Instruction>()
    instructions.reversed().forEach {
        stack.push(it)
    }

    var x = 1
    val positions = mutableListOf<Int>()
    var instructionCycle = 1
    var ended = false
    var executionBlock: (() -> Unit)? = null
    var executeAt = 1

    do {
        if (instructionCycle == executeAt && stack.isNotEmpty()) {
            val instruction = stack.pop()
            executeAt = instructionCycle + instruction.cycles

            when (instruction) {
                is Add -> {
                    executionBlock = { x += instruction.value }
                }

                else -> {
                    executionBlock = null
                }
            }
        }

        if (instructionCycle == executeAt && stack.isEmpty()) {
            ended = true
        } else {
            positions.add(x)
        }

        ++instructionCycle

        if (instructionCycle == executeAt && executionBlock != null) {
            executionBlock()
        }
    } while (!ended)

    return positions
}