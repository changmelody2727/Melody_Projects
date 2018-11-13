package LightsOut;

import static LightsOut.LightsOutView.*;
import java.util.Random;
import javax.swing.JOptionPane;

public class LightsOutModel
{
    /** The number used to indicate the light is lit */
    public static final int Light = 1;

    /** The number used to indicate the light is out */
    public static final int LightOut = 0;

    /** Size of the board */
    private int[][] board;

    /** How many moves player used **/
    private int clicks;

    /** What level the player is on **/
    private int level;

    /** Shows weather the player won or not **/
    private boolean LightsOut;

    /** Shows if it is in the manual mode or not **/
    private boolean mode;

    /**
     * Creates a C4Board with the specified number of rows and columns. There should be no pieces on the board and it
     * should be player 1's turn to move.
     * 
     * However, if either rows or cols is less than four, throws an IllegalArgumentException.
     */
    public LightsOutModel (int rows, int cols)
    {
        rows = 5;
        cols = 5;

        board = new int[rows][cols];
        clicks = 0;
        level = 0;
        LightsOut = false;
        mode = false;
    }

    public void newGame ()
    {
        LightsOut = false;
        mode = false;
        for (int j = 0; j < board.length; j++) // for the rows
        {
            for (int k = 0; k < board[0].length; k++) // for the columns
            {
                board[j][k] = LightOut;
            }
        }

        Random ran = new Random();

        for (int k = 0; k < (3 + level); k++)
        {
            int i = ran.nextInt(4);
            int j = ran.nextInt(4);
            moveTo(i, j);
        }
        clicks = 0;
    }

    public void moveTo (int row, int col)
    {
        if (LightsOut == false)
        {
            if (mode == false)
            {
                onOffSwitch(row, col);
                if (row != 4 && row != 0 && col != 4 && col != 0)
                {
                    onOffSwitch(row + 1, col);
                    onOffSwitch(row - 1, col);
                    onOffSwitch(row, col + 1);
                    onOffSwitch(row, col - 1);
                    clicks++;
                }

                else if (row == 0 && col == 0)
                {
                    onOffSwitch(row + 1, col);
                    onOffSwitch(row, col + 1);
                    clicks++;
                }
                else if (row == 0 && col == 4)
                {
                    onOffSwitch(row + 1, col);
                    onOffSwitch(row, col - 1);
                    clicks++;
                }
                else if (row == 4 && col == 0)
                {
                    onOffSwitch(row - 1, col);
                    onOffSwitch(row, col + 1);
                    clicks++;
                }
                else if (row == 4 && col == 4)
                {
                    onOffSwitch(row - 1, col);
                    onOffSwitch(row, col - 1);
                    clicks++;
                }
                else if (row == 0)
                {
                    onOffSwitch(row + 1, col);
                    onOffSwitch(row, col + 1);
                    onOffSwitch(row, col - 1);
                    clicks++;
                }
                else if (row == 4)
                {
                    onOffSwitch(row - 1, col);
                    onOffSwitch(row, col + 1);
                    onOffSwitch(row, col - 1);
                    clicks++;
                }
                else if (col == 0)
                {
                    onOffSwitch(row + 1, col);
                    onOffSwitch(row - 1, col);
                    onOffSwitch(row, col + 1);
                    clicks++;
                }
                else if (col == 4)
                {
                    onOffSwitch(row + 1, col);
                    onOffSwitch(row - 1, col);
                    onOffSwitch(row, col - 1);
                    clicks++;
                }

            }
        }
        if (mode == true)
        {
            if (board[row][col] == LightOut)
            {
                board[row][col] = Light;
            }
            else
            {
                board[row][col] = LightOut;
            }
        }

    }

    public boolean getWin ()
    {
        boolean allOut;
        allOut = true;
        if (mode == false)
        {
            for (int j = 0; j < board.length; j++) // for the rows
            {
                for (int k = 0; k < board[0].length; k++) // for the columns
                {
                    if (board[j][k] == Light)
                    {
                        allOut = false;
                    }
                }
            }

            if (allOut == true)
            {
                level++;
                LightsOut = true;
            }
        }
        else
        {
            return false;
        }
        return allOut;
    }

    public int getOccupant (int row, int col)
    {
        if (board[row][col] == 0)
        {
            return LightOut;
        }
        else
        {
            return Light;
        }
    }

    public int getClickCount ()
    {
        return clicks;
    }

    public int getLevelCount ()
    {
        return level;
    }

    public void clear ()
    {
        if (mode == true)
        {
            for (int j = 0; j < board.length; j++) // for the rows
            {
                for (int k = 0; k < board[0].length; k++) // for the columns
                {
                    board[j][k] = LightOut;
                }
            }
        }
    }

    public void onOffSwitch (int row, int col)
    {
        if (board[row][col] == LightOut)
        {
            board[row][col] = Light;
        }
        else
        {
            board[row][col] = LightOut;
        }
    }

    public void enterManualMode ()
    {
        mode = true;
    }

    public void exitManualMode ()
    {
        mode = false;
    }

}