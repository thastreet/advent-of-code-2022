import Outcome.DRAW
import Outcome.LOSS
import Outcome.WIN
import Shape.PAPER
import Shape.ROCK
import Shape.SCISSOR
import java.io.File

enum class Shape(val score: Int) {
    ROCK(1),
    PAPER(2),
    SCISSOR(3)
}

enum class Outcome(val score: Int) {
    WIN(6),
    DRAW(3),
    LOSS(0)
}

val Shape.beats: Shape
    get() = when (this) {
        ROCK -> SCISSOR
        PAPER -> ROCK
        SCISSOR -> PAPER
    }

val Shape.beatenBy: Shape
    get() = when (this) {
        ROCK -> PAPER
        PAPER -> SCISSOR
        SCISSOR -> ROCK
    }

fun Char.toShape(): Shape =
    when (this) {
        'A', 'X' -> ROCK
        'B', 'Y' -> PAPER
        'C', 'Z' -> SCISSOR
        else -> throw IllegalArgumentException("Invalid shape: $this")
    }

fun Char.toOutcome(): Outcome =
    when (this) {
        'X' -> LOSS
        'Y' -> DRAW
        'Z' -> WIN
        else -> throw IllegalArgumentException("Invalid outcome: $this")
    }

fun main(args: Array<String>) {
    val input = File("input.txt")
    val lines = input.readLines()

    println("Part 1 answer: ${part1(lines)}")
    println("Part 2 answer: ${part2(lines)}")
}

private fun part1(lines: List<String>): Int =
    parseRounds(lines).sumOf { (opponent, player) ->
        player.score + getOutcomeScore(opponent, player)
    }

private fun parseRounds(lines: List<String>): List<Pair<Shape, Shape>> =
    lines.map {
        it.split(" ").run {
            get(0)[0].toShape() to get(1)[0].toShape()
        }
    }

private fun part2(lines: List<String>): Int =
    parseOutcomes(lines).sumOf { (opponent, outcome) ->
        val player = getPlayer(opponent, outcome)
        player.score + getOutcomeScore(opponent, player)
    }

private fun getPlayer(opponent: Shape, outcome: Outcome): Shape =
    when (outcome) {
        WIN -> opponent.beatenBy
        DRAW -> opponent
        LOSS -> opponent.beats
    }

private fun parseOutcomes(lines: List<String>): List<Pair<Shape, Outcome>> =
    lines.map {
        it.split(" ").run {
            get(0)[0].toShape() to get(1)[0].toOutcome()
        }
    }

private fun getOutcomeScore(opponent: Shape, player: Shape): Int =
    when {
        player.beats == opponent -> WIN.score
        player.beatenBy == opponent -> LOSS.score
        else -> DRAW.score
    }