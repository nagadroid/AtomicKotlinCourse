package inheritance2

/* The simplified version of Maze interface which doesn't yet support movement */
interface Maze {
    fun all(): Set<GameElement>

    fun allAt(position: Position): Set<GameElement>

    fun destroy(element: GameElement)

    fun position(element: GameElement): Position?
}

class MazeImpl(
        val representation: String
) : Maze {

    val width = representation.lines().maxBy { it.length }?.length ?: 0
    val height = representation.lines().size

    private val matrix: GameMatrix = GameMatrixImpl(width, height)

    private val elementToPosition = mutableMapOf<GameElement, Position>()

    init {
        val lines = representation.lines()
        for (y in 0 until height) {
            for (x in 0 until width) {
                val ch = lines.getOrNull(y)?.getOrNull(x)
                val element = createGameElement(ch)
                if (element != null) {
                    add(element, Position(x, y))
                }
            }
        }
    }

    fun add(element: GameElement, position: Position) {
        matrix.add(element, position)
        elementToPosition[element] = position
    }

    override fun all(): Set<GameElement> {
        return elementToPosition.keys.toSet()
    }

    override fun allAt(position: Position): Set<GameElement> {
        return matrix.elementsAt(position)
    }

    override fun position(element: GameElement): Position? =
            elementToPosition[element]

    override fun destroy(element: GameElement) {
        val position = position(element) ?: return
        matrix.remove(element, position)
        elementToPosition.remove(element)
    }

    override fun toString(): String {
        assertConsistentState()
        return matrix.toString()
    }

    private fun assertConsistentState() {
        for (x in 0 until width) {
            for (y in 0 until height) {
                val position = Position(x, y)
                val elements = matrix.elementsAt(position)
                elements.forEach { element ->
                    val storedPosition = elementToPosition[element]
                    if (storedPosition != position) {
                        throw AssertionError("Inconsistent stored positions for element $element: " +
                                "$storedPosition != $position")
                    }
                }
            }
        }
        for ((element, position) in elementToPosition) {
            val elements = matrix.elementsAt(position)
            if (!elements.contains(element)) {
                throw AssertionError("Inconsistent stored positions for element $element: " +
                        "no such element at $position")
            }
        }
    }
}