abstract class Monkey {
    var inspectCount: Int = 0

    abstract val items: MutableList<Int>

    protected abstract fun test(keepLow: Boolean, old: Int, throwTo: (item: Int, to: Int) -> Unit)

    fun executeTurn(keepLow: Boolean, throwTo: (item: Int, to: Int) -> Unit) {
        while (items.isNotEmpty()) {
            ++inspectCount
            test(keepLow, items.removeFirst(), throwTo)
        }
    }

    fun handleNew(keepLow: Boolean, new: Int): Int = if (keepLow) (new / 3f).toInt() else new

    fun receive(item: Int) {
        items.add(item)
    }
}

val monkeys = listOf(
    object : Monkey() {
        override val items = mutableListOf(63, 57)

        override fun test(keepLow: Boolean, old: Int, throwTo: (item: Int, to: Int) -> Unit) {
            val new = handleNew(keepLow, old * 11)

            if (new % 7 == 0) {
                throwTo(new, 6)
            } else {
                throwTo(new, 2)
            }
        }
    },
    object : Monkey() {
        override val items = mutableListOf(82, 66, 87, 78, 77, 92, 83)

        override fun test(keepLow: Boolean, old: Int, throwTo: (item: Int, to: Int) -> Unit) {
            val new = handleNew(keepLow, old + 1)

            if (new % 11 == 0) {
                throwTo(new, 5)
            } else {
                throwTo(new, 0)
            }
        }
    },
    object : Monkey() {
        override val items = mutableListOf(97, 53, 53, 85, 58, 54)

        override fun test(keepLow: Boolean, old: Int, throwTo: (item: Int, to: Int) -> Unit) {
            val new = handleNew(keepLow, old * 7)

            if (new % 13 == 0) {
                throwTo(new, 4)
            } else {
                throwTo(new, 3)
            }
        }
    },
    object : Monkey() {
        override val items = mutableListOf(50)

        override fun test(keepLow: Boolean, old: Int, throwTo: (item: Int, to: Int) -> Unit) {
            val new = handleNew(keepLow, old + 3)

            if (new % 3 == 0) {
                throwTo(new, 1)
            } else {
                throwTo(new, 7)
            }
        }
    },
    object : Monkey() {
        override val items = mutableListOf(64, 69, 52, 65, 73)

        override fun test(keepLow: Boolean, old: Int, throwTo: (item: Int, to: Int) -> Unit) {
            val new = handleNew(keepLow, old + 6)

            if (new % 17 == 0) {
                throwTo(new, 3)
            } else {
                throwTo(new, 7)
            }
        }
    },
    object : Monkey() {
        override val items = mutableListOf(57, 91, 65)

        override fun test(keepLow: Boolean, old: Int, throwTo: (item: Int, to: Int) -> Unit) {
            val new = handleNew(keepLow, old + 5)

            if (new % 2 == 0) {
                throwTo(new, 0)
            } else {
                throwTo(new, 6)
            }
        }
    },
    object : Monkey() {
        override val items = mutableListOf(67, 91, 84, 78, 60, 69, 99, 83)

        override fun test(keepLow: Boolean, old: Int, throwTo: (item: Int, to: Int) -> Unit) {
            val new = handleNew(keepLow, old * old)

            if (new % 5 == 0) {
                throwTo(new, 2)
            } else {
                throwTo(new, 4)
            }
        }
    },
    object : Monkey() {
        override val items = mutableListOf(58, 78, 69, 65)

        override fun test(keepLow: Boolean, old: Int, throwTo: (item: Int, to: Int) -> Unit) {
            val new = handleNew(keepLow, old + 7)

            if (new % 19 == 0) {
                throwTo(new, 5)
            } else {
                throwTo(new, 1)
            }
        }
    }
)