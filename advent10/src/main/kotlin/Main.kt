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
    var x = 1
    val values = mutableListOf<Int>()
    var instructionCycle = 1
    var ended = false
    var executionBlock: (() -> Unit)? = null
    var executeAt = 1

    val stack = Stack<Instruction>()
    instructions.reversed().forEach {
        stack.push(it)
    }

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
        }

        values.add(x)
        ++instructionCycle

        if (instructionCycle == executeAt && executionBlock != null) {
            executionBlock()
        }
    } while (!ended)

    val strengthCycles = listOf(20, 60, 100, 140, 180, 220)
    return strengthCycles.sumOf { it * values[it - 1] }
}