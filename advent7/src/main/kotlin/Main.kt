import java.io.File

interface IFile {
    val name: String
    val size: Int
    val parent: IFile?
}

data class SingleFile(
    override val name: String,
    override val size: Int,
    override val parent: IFile?
) : IFile

data class Directory(
    override val name: String,
    override var parent: IFile?
) : IFile {
    var files: MutableMap<String, IFile> = mutableMapOf()
    override val size: Int = files.values.sumOf { it.size }
}

fun main(args: Array<String>) {
    val lines = File("input.txt").readLines()

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
                        directory = directory?.files?.values?.firstOrNull { it is Directory && it.name == parts[2] } as? Directory ?: run {
                            Directory(parts[0], directory).also {
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
                                    else -> directory?.files?.set(tempParts[1], SingleFile(tempParts[1], tempParts[0].toInt(), directory))
                                }
                            }
                        } while (tempIndex in lines.indices && tempParts[0] != "$")
                    }
                }
            }
        }

        ++index
    } while (index in lines.indices)
}