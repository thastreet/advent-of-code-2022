import java.io.File

interface IFile {
    val name: String
    val size: Int
    val parent: Directory?
}

data class SingleFile(
    override val name: String,
    override val size: Int,
    override val parent: Directory?
) : IFile

data class Directory(
    override val name: String,
    override var parent: Directory?
) : IFile {
    var files: MutableMap<String, IFile> = mutableMapOf()
    override val size: Int
        get() = files.values.sumOf { it.size }

    fun flatten(output: MutableList<Directory>) {
        files.values.filterIsInstance<Directory>().forEach {
            output.add(it)
            it.flatten(output)
        }
    }
}

fun main(args: Array<String>) {
    val lines = File("input.txt").readLines()
    val root = parseTree(lines)

    val part1Answer = part1(root)
    println("part1Answer: $part1Answer")

    val part2Answer = part2(root)
    println("part2Answer: $part2Answer")
}

fun part1(root: Directory): Int {
    val candidates = mutableListOf<Directory>()
    findPart1Candidates(root, candidates)
    return candidates.sumOf { it.size }
}

fun part2(root: Directory): Int {
    val directories = mutableListOf<Directory>()
    root.flatten(directories)
    directories.sortBy { it.size }

    val unused = 70000000 - root.size
    val needed = 30000000 - unused
    return directories.first { it.size >= needed }.size
}

fun findPart1Candidates(directory: Directory, candidates: MutableList<Directory>) {
    directory.files.values.filterIsInstance<Directory>().forEach {
        if (it.size <= 100000) {
            candidates.add(it)
        }
        findPart1Candidates(it, candidates)
    }
}

fun parseTree(lines: List<String>): Directory {
    var index = 0

    var directory: Directory? = null
    var root: Directory? = null

    do {
        val line = lines[index]

        val parts = line.split(" ")
        when (parts[0]) {
            "$" -> {
                when (parts[1]) {
                    "cd" -> {
                        directory = if (parts[2] == "..") {
                            directory?.parent
                        } else {
                            directory?.files?.values
                                ?.firstOrNull { it is Directory && it.name == parts[2] } as? Directory
                                ?: Directory(parts[0], directory).also {
                                    if (root == null) {
                                        root = it
                                    }
                                }
                        }
                    }

                    "ls" -> {
                        var tempIndex = index
                        var tempParts: List<String> = emptyList()

                        do {
                            ++tempIndex
                            if (tempIndex in lines.indices) {
                                val tempLine = lines[tempIndex]
                                tempParts = tempLine.split(" ")
                                when (tempParts[0]) {
                                    "$" -> index = tempIndex - 1
                                    "dir" -> directory?.files?.set(tempParts[1], Directory(tempParts[1], directory))
                                    else -> directory?.files?.set(
                                        tempParts[1],
                                        SingleFile(tempParts[1], tempParts[0].toInt(), directory)
                                    )
                                }
                            }
                        } while (tempIndex in lines.indices && tempParts[0] != "$")
                    }
                }
            }
        }

        ++index
    } while (index in lines.indices)

    return root!!
}