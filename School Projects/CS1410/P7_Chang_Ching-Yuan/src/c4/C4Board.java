package c4;

/**
 * Represents the state of a Connect Four board on which multiple games can be played. The board consists of a
 * rectangular grid of squares in which playing pieces can be placed. Squares are identified by their zero-based row and
 * column numbers, where rows are numbered starting with the bottom row, and columns are numbered by starting from the
 * leftmost column.
 * 
 * Multiple games can be played on a single board. Whenever a new game begins, the board is empty. There are two
 * players, identified by the constants P1 (Player 1) and P2 (Player 2). P1 moves first in the first game, P2 moves
 * first in the second game, and so on in alternating fashion.
 * 
 * A C4Board also keeps track of the outcomes of the games that have been played on it. It tracks the number of wins by
 * P1, the number of wins by P2, and the number of ties. It does not track games that were abandoned before being
 * completed.
 */
public class C4Board
{
    /** The number used to indicate Player 1 */
    public static final int P1 = 1;

    /** The number used to indicate Player 2 */
    public static final int P2 = 2;

    /** Player's turn to move */
    private int turnToMove;

    /** Size of the board */
    private int[][] board;

    /** The first player when the game starts */
    private boolean firstPlayer;

    /** Counts how many times P1 wins the game */
    private int P1Wins;

    /** Counts how many times P2 wins the game */
    private int P2Wins;

    /** Counts how many times the game end with a tie */
    private int ties;

    /** Shows whether the game is over or not */
    private boolean gameOver;

    /**
     * Creates a C4Board with the specified number of rows and columns. There should be no pieces on the board and it
     * should be player 1's turn to move.
     * 
     * However, if either rows or cols is less than four, throws an IllegalArgumentException.
     */
    public C4Board (int rows, int cols)
    {
        // if either rows or cols is less than four, throws an IllegalArgumentException.
        if (rows < 4 || cols < 4)
        {
            throw new IllegalArgumentException();
        }

        turnToMove = P1;
        firstPlayer = true;
        board = new int[rows][cols];
        P1Wins = 0;
        P2Wins = 0;
        ties = 0;
        gameOver = false;
    }

    /**
     * Sets up the board to play a new game, whether or not the current game is complete. All of the pieces should be
     * removed from the board. The player who had the second move in the previous game should have the first move in the
     * new game.
     */
    public void newGame ()
    {
        for (int j = 0; j < board.length; j++) // for the rows
        {
            for (int k = 0; k < board[0].length; k++) // for the columns
            {
                board[j][k] = 0;
            }
        }

        if (firstPlayer)
        {
            turnToMove = P2;
            firstPlayer = false;
        }
        else
        {
            turnToMove = P1;
            firstPlayer = true;
        }
        gameOver = false;

    }

    /**
     * Records a move, by the player whose turn it is to move, in the first open square in the specified column. If the
     * column is full, or if the game is over because a win or a tie has occurred, does nothing. If a non-existent
     * column is specified, throws an IllegalArgumentException.
     */
    public void moveTo (int col)
    {
        if (gameOver == false)
        {
            // If a non-existent column is specified, throws an IllegalArgumentException.
            if (col > board[0].length || col < 0)
            {
                throw new IllegalArgumentException();
            }

            if (FourInARow.fourInRow(board) == false)
            {
               
                    for (int j = 0; j < board.length; j++)
                    {
                        if (board[j][col] == 0)
                        {
                            board[j][col] = turnToMove;
                            if (turnToMove == P1)
                            {
                                turnToMove = P2;
                            }
                            else
                            {
                                turnToMove = P1;
                            }
                            break;

                        }
                    }

                
            }

            if (FourInARow.fourInRow(board) == true && turnToMove == P2)
            {
                P1Wins++;
                gameOver = true;
            }
            if (FourInARow.fourInRow(board) == true && turnToMove == P1)
            {
                P2Wins++;
                gameOver = true;

            }
            boolean isFull;
            isFull = true;
            for (int j = 0; j < board.length; j++) // for the rows
            {
                for (int k = 0; k < board[0].length; k++) // for the columns
                {
                    if (board[j][k] == 0)
                    {
                        isFull = false;
                    }
                }
            }
            if (isFull == true)
            {
                ties++;
                gameOver = true;
            }
        }

    }

    /**
     * Reports the occupant of the board square at the specified row and column. If the row or column doesn't exist,
     * throws an IllegalArgumentException. If the square is unoccupied, returns 0. Otherwise, returns P1 (if the square
     * is occupied by player 1) or P2 (if the square is occupied by player 2).
     */
    public int getOccupant (int row, int col)
    {
        // If the row doesn't exist, throws an IllegalArgumentException.
        if (row > board.length || row < 0)
        {
            throw new IllegalArgumentException();
        }

        // If the row doesn't exist, throws an IllegalArgumentException.
        if (col > board[0].length || col < 0)
        {
            throw new IllegalArgumentException();
        }

        if (board[row][col] == 1)
        {
            return P1;
        }
        if (board[row][col] == 2)
        {
            return P2;
        }
        else
        {
            return 0;
        }
    }

    /**
     * Reports whose turn it is to move. Returns P1 (if it is player 1's turn to move), P2 (if it is player 2's turn to
     * move, or 0 (if it is neither player's turn to move because the current game is over because of a win or tie).
     */
    public int getToMove ()
    {

        return turnToMove;
    }

    /**
     * Reports how many games have been won by player 1 since this board was created.
     */
    public int getWinsForP1 ()
    {
        return P1Wins;
    }

    /**
     * Reports how many games have been won by player 2 since this board was created.
     */
    public int getWinsForP2 ()
    {
        return P2Wins;
    }

    /**
     * Reports how many ties have occurred since this board was created.
     */
    public int getTies ()
    {
        return ties;
    }
}
