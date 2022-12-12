fun main(args: Array<String>) {
    val part1Answer = part1(monkeys)
    println("part1Answer: $part1Answer")

    val part2Answer = part2(monkeys)
    println("part2Answer: $part2Answer")
}

fun part1(monkeys: List<Monkey>): Int {
    repeat(20) {
        monkeys.forEach {
            it.executeTurn(keepLow = true) { item, to ->
                monkeys[to].receive(item)
            }
        }
    }

    return monkeys.map { it.inspectCount }.sortedDescending().let {
        it[0] * it[1]
    }
}

fun part2(monkeys: List<Monkey>): Int {
    repeat(10000) {
        monkeys.forEach {
            it.executeTurn(keepLow = false) { item, to ->
                monkeys[to].receive(item)
            }
        }
    }

    return monkeys.map { it.inspectCount }.sortedDescending().let {
        it[0] * it[1]
    }
}