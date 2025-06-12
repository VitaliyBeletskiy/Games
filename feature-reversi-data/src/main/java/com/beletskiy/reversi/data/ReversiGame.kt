package com.beletskiy.reversi.data

interface IReversiGame {

    fun newGame(): GameState
    fun makeMove(row: Int, column: Int): GameState

    companion object {
        const val BOARD_SIZE: Int = 8

        fun getInstance(): IReversiGame {
            return ReversiGameImpl()
        }
    }
}

class ReversiGameImpl : IReversiGame {

    enum class Direction(val offsetRow: Int, val offsetCol: Int) {
        UP(-1, 0),
        UP_RIGHT(-1, 1),
        RIGHT(0, 1),
        DOWN_RIGHT(1, 1),
        DOWN(1, 0),
        DOWN_LEFT(1, -1),
        LEFT(0, -1),
        UP_LEFT(-1, -1)
    }

    private companion object {
        const val BOARD_SIZE = 8
        val BOARD_RANGE = 0 until BOARD_SIZE
    }

    private val board: Array<Array<Cell>> = Array(8) { Array(8) { Cell(disc = Disc.NONE) } }
    private var currentPlayerDisc: PlayerDisc = PlayerDisc.BLACK
    private var isGameOver: Boolean = false
    private var blackScore: Int = 0
    private var whiteScore: Int = 0

    override fun newGame(): GameState {
        initializeBoard()
        currentPlayerDisc = PlayerDisc.BLACK
        isGameOver = false
        blackScore = 2
        whiteScore = 2
        return getGameState()
    }

    override fun makeMove(row: Int, column: Int): GameState {
        // check if the cell is within the board range and not occupied and the game is not over
        if (row !in BOARD_RANGE || column !in BOARD_RANGE || board[row][column].isOccupied() || isGameOver) {
            return getGameState()
        }
        // check if there is at least one direction to capture opponent's discs
        val capturingDirections = getCapturingDirections(row, column)
        if (capturingDirections.isEmpty()) {
            return getGameState()
        }
        // capture opponent's discs
        flipCapturedDiscs(row, column, capturingDirections)

        // place the player's disc in the specified cell
        board[row][column] = Cell(disc = currentPlayerDisc.toDisc())

        // update scores
        blackScore = board.sumOf { row -> row.count { it.disc == Disc.BLACK } }
        whiteScore = board.sumOf { row -> row.count { it.disc == Disc.WHITE } }

        // if the next player can make a move, switch players
        if (canMakeMove(currentPlayerDisc.opposite())) {
            currentPlayerDisc = currentPlayerDisc.opposite()
        } else {
            // if the next player cannot make a move, check if the current player can make a move
            isGameOver = !canMakeMove(currentPlayerDisc)
        }

        return getGameState()
    }

    /**
     * Returns a list of directions in which the current player can capture
     * at least one opponent's disc starting from the given cell.
     *
     * For each [Direction], this function checks
     * if there is at least one adjacent opponent disc followed by a disc of the
     * current player, indicating a valid line of capture according to Reversi rules.
     *
     * @param row Row index of the starting cell.
     * @param col Column index of the starting cell.
     * @return List of directions where a capture is possible. Empty if no valid directions.
     */
    private fun getCapturingDirections(
        row: Int,
        col: Int,
        currentPlayer: PlayerDisc = this.currentPlayerDisc,
    ): List<Direction> {
        val directions = mutableListOf<Direction>()
        val playerDisc = currentPlayer.toDisc()
        val opponentDisc = currentPlayer.opposite().toDisc()

        val directionsWithOpponent = mutableListOf<Direction>()
        Direction.entries.forEach { direction ->
            val newRow = row + direction.offsetRow
            val newCol = col + direction.offsetCol
            if (newRow in BOARD_RANGE && newCol in BOARD_RANGE) {
                if (board[newRow][newCol].disc == opponentDisc) {
                    directionsWithOpponent.add(direction)
                }
            }
        }

        directionsWithOpponent.forEach { direction ->
            var newRow = row + direction.offsetRow
            var newCol = col + direction.offsetCol

            while (newRow in BOARD_RANGE && newCol in BOARD_RANGE && board[newRow][newCol].isOccupied()) {
                if (board[newRow][newCol].disc == playerDisc) {
                    directions.add(direction)
                    break
                }
                newRow += direction.offsetRow
                newCol += direction.offsetCol
            }
        }

        return directions
    }

    private fun flipCapturedDiscs(row: Int, column: Int, capturingDirections: List<Direction>) {
        val currentDisc = currentPlayerDisc.toDisc()
        val opponentDisc = currentPlayerDisc.opposite().toDisc()

        capturingDirections.forEach { direction ->
            var newRow = row + direction.offsetRow
            var newCol = column + direction.offsetCol

            while (newRow in BOARD_RANGE && newCol in BOARD_RANGE && board[newRow][newCol].disc == opponentDisc) {
                board[newRow][newCol] = board[newRow][newCol].copy(disc = currentDisc)
                newRow += direction.offsetRow
                newCol += direction.offsetCol
            }
        }
    }

    private fun canMakeMove(playerDisc: PlayerDisc): Boolean {
        var canMakeMove = false
        for (i in BOARD_RANGE) {
            for (j in BOARD_RANGE) {
                if (board[i][j].disc == Disc.NONE && getCapturingDirections(
                        i,
                        j,
                        playerDisc
                    ).isNotEmpty()
                ) {
                    canMakeMove = true
                    break
                }
            }
        }
        return canMakeMove
    }

    private fun getGameState(): GameState {
        val winner = if (isGameOver) {
            when {
                blackScore > whiteScore -> PlayerDisc.BLACK
                whiteScore > blackScore -> PlayerDisc.WHITE
                else -> null
            }
        } else null

        // Update move options for each cell
        for (row in BOARD_RANGE) {
            for (col in BOARD_RANGE) {
                if (board[row][col].disc == Disc.NONE) {
                    val canMove = getCapturingDirections(row, col).isNotEmpty()
                    board[row][col] =
                        board[row][col].copy(moveOption = if (canMove) MoveOption.POSSIBLE else MoveOption.NONE)
                } else {
                    // If the cell is occupied, reset the move option
                    board[row][col] = board[row][col].copy(moveOption = MoveOption.NONE)
                }
            }
        }

        return GameState(
            board.map { it.toList() },
            currentPlayerDisc,
            isGameOver,
            blackScore,
            whiteScore,
            winner,
        )
    }

    private fun initializeBoard() {
        for (i in BOARD_RANGE) {
            for (j in BOARD_RANGE) {
                board[i][j] = Cell(disc = Disc.NONE)
            }
        }
        board[3][3] = Cell(disc = Disc.WHITE)
        board[3][4] = Cell(disc = Disc.BLACK)
        board[4][3] = Cell(disc = Disc.BLACK)
        board[4][4] = Cell(disc = Disc.WHITE)
    }

    // region For testing
    internal fun setBoard(newBoard: List<List<Cell>>? = null) {
        newBoard?.let {
            for (i in BOARD_RANGE) {
                for (j in BOARD_RANGE) {
                    board[i][j] = newBoard[i][j]
                }
            }
        } ?: initializeBoard()
    }

    internal fun getBoard(): List<List<Cell>> {
        return board.map { it.toList() }
    }

    internal fun printBoard() {
        println("========")
        for (i in BOARD_RANGE) {
            val strBuilder = StringBuilder()
            for (j in BOARD_RANGE) {
                val symbol = when (board[i][j].disc) {
                    Disc.BLACK -> "⚫"
                    Disc.WHITE -> "⚪"
                    else -> "·"
                }
                strBuilder.append(String.format("%-2s", symbol)) // padding for alignment
            }
            println(strBuilder.toString())
        }
        println("========")
    }
    // endregion
}
