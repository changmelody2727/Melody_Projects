package LightsOut;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import static LightsOut.LightsOutModel.*;
import static LightsOut.LightsOutView.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Implements a Connect 4 game with a GUI interface.
 * 
 * @author Joe Zachary
 */
@SuppressWarnings("serial")
public class LightsOutView extends JFrame implements ActionListener
{
    // Some formatting constants
    private final static int WIDTH = 600;
    private final static int HEIGHT = 600;
    public final static int ROWS = 5;
    public final static int COLS = 5;
    public final static Color BACKGROUND_COLOR = Color.GRAY;
    public final static Color P1_COLOR = Color.CYAN;
    public final static Color P2_COLOR = Color.YELLOW;
    public final static Color BOARD_COLOR = Color.BLUE;
    public final static Color TIE_COLOR = Color.WHITE;
    public final static Color BUTTON_COLOR = Color.DARK_GRAY;
    public final static int BORDER = 5;
    public final static int FONT_SIZE = 20;

    /** The "smarts" of the game **/
    private LightsOutModel model;

    /** How many clicks the player use **/
    private JLabel clickCount;

    /** Which level the player is on **/
    private JLabel levelCount;

    /** The portion of the GUI that contains the playing surface **/
    private Board board;

    private boolean isManual;

    /**
     * Creates and initializes the game.
     */
    public LightsOutView ()
    {
        // Set the title that appears at the top of the window
        setTitle("Lights Out Puzzle");

        // Cause the application to end when the windows is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Give the window its initial dimensions
        setSize(WIDTH, HEIGHT);

        // The root panel contains everything
        JPanel root = new JPanel();
        root.setLayout(new BorderLayout());
        setContentPane(root);

        // The center portion contains the playing board
        model = new LightsOutModel(ROWS, COLS);
        board = new Board(model, this);
        root.add(board, "Center");

        // The top portion contains the scores
        JPanel scores = new JPanel();
        scores.setLayout(new BorderLayout());
        root.add(scores, "North");

        // Score and indicator for the first player
        JPanel clicks = new JPanel();
        clicks.setBackground(BACKGROUND_COLOR);
        clickCount = new JLabel("Clicks: 0");
        clickCount.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        clickCount.setForeground(P1_COLOR);
        clickCount.setBorder(new EmptyBorder(0, BORDER, 0, BORDER));
        clicks.add(clickCount);
        scores.add(clicks, "West");

        // Score and indicator for the second player
        JPanel level = new JPanel();
        level.setBackground(BACKGROUND_COLOR);
        levelCount = new JLabel("Level: 0");
        levelCount.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        levelCount.setForeground(P2_COLOR);
        levelCount.setBorder(new EmptyBorder(0, BORDER, 0, BORDER));
        level.add(levelCount);
        scores.add(level, "East");

        JPanel modes = new JPanel();
        modes.setLayout(new BorderLayout());
        root.add(modes, "South");

        // The bottom portion contains the New Game button
        JButton newGame = new JButton("New Game");
        newGame.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        newGame.setForeground(BUTTON_COLOR);
        newGame.setBackground(TIE_COLOR);
        newGame.addActionListener(this);
        modes.add(newGame, "West");

        // The bottom portion contains the mode button
        JButton mode = new JButton("Enter Manual Setup");
        mode.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        mode.setForeground(BUTTON_COLOR);
        mode.setBackground(TIE_COLOR);
        mode.addActionListener(this);
        modes.add(mode, "Center");

        // The bottom portion contains the clear button
        JButton clear = new JButton("Clear");
        clear.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        clear.setForeground(BUTTON_COLOR);
        clear.setBackground(TIE_COLOR);
        clear.addActionListener(this);
        modes.add(clear, "East");

        // Refresh the display and we're ready
        board.refresh();
    }

    /**
     * Sets the label that displays player 1's win count
     */
    public void setClickCount (int n)
    {
        clickCount.setText("Clicks: " + n);

    }

    /**
     * Sets the label that displays player 2's win count
     */
    public void setLevelCount (int n)
    {
        levelCount.setText("Level: " + n);
    }

    /**
     * Called when the New Game button is clicked. Tells the model to begin a new game, then refreshes the display.
     */
    @Override
    public void actionPerformed (ActionEvent e)
    {

        if (e.getSource() instanceof JButton)
        {
            JButton b = (JButton) e.getSource();
            if (b.getText().equals("Enter Manual Setup"))
            {
                b.setText("Exit Manual Setup");
                model.enterManualMode();
                board.refresh();
            }
            else if (b.getText().equals("Exit Manual Setup"))
            {
                b.setText("Enter Manual Setup");
                model.exitManualMode();
                board.refresh();
            }
            if (b.getText().equals("Clear"))
            {
                isManual = true;
                // isManual = model.getManual();
                model.clear();
                board.refresh();

            }
            if (b.getText().equals("New Game"))
            {
                model.newGame();
                // b.setText("TEST");
                board.refresh();
            }
        }

    }

    public boolean manual ()
    {
        return isManual;
    }
}

/**
 * The playing surface of the game.
 */
@SuppressWarnings("serial")
class Board extends JPanel implements MouseListener
{
    /** The "smarts" of the game */
    private LightsOutModel model;

    /** The top level GUI for the game */
    private LightsOutView display;

    /**
     * Creates the board portion of the GUI.
     */
    public Board (LightsOutModel model, LightsOutView display)
    {
        // Record the model and the top-level display
        this.model = model;
        this.display = display;

        // Set the background color and the layout
        setBackground(BOARD_COLOR);
        setLayout(new GridLayout(ROWS, COLS));

        // Create and lay out the grid of squares that make up the game.
        for (int i = ROWS - 1; i >= 0; i--)
        {
            for (int j = 0; j < COLS; j++)
            {
                Square s = new Square(i, j);
                s.addMouseListener(this);
                add(s);
            }
        }
    }

    /**
     * Refreshes the display. This should be called whenever something changes in the model.
     */
    public void refresh ()
    {
        // Iterate through all of the squares that make up the grid
        Component[] squares = getComponents();
        for (Component c : squares)
        {
            Square s = (Square) c;

            // Set the color of the squares appropriately
            int status = model.getOccupant(s.getRow(), s.getCol());
            if (status == Light)
            {
                s.setColor(P1_COLOR);
            }
            else
            {
                s.setColor(BACKGROUND_COLOR);
            }
        }

        // Update the win and tie counts
        display.setClickCount(model.getClickCount());
        display.setLevelCount(model.getLevelCount());

        // Ask that this board be repainted
        repaint();
    }

    /**
     * Called whenever a Square is clicked. Notifies the model that a move has been attempted.
     */
    @Override
    public void mouseClicked (MouseEvent e)
    {
        Square s = (Square) e.getSource();
        model.moveTo(s.getRow(), s.getCol());
        refresh();
        
        if (model.getWin() == true)
        {
            JOptionPane.showMessageDialog(null, "You Won!!\nYou used " + model.getClickCount() + " steps");
        }
    }

    @Override
    public void mousePressed (MouseEvent e)
    {
    }

    @Override
    public void mouseReleased (MouseEvent e)
    {
    }

    @Override
    public void mouseEntered (MouseEvent e)
    {
    }

    @Override
    public void mouseExited (MouseEvent e)
    {
    }
}

/**
 * A single square on the board where a move can be made
 */
@SuppressWarnings("serial")
class Square extends JPanel
{
    /** The row within the board of this Square. Rows are numbered from zero; row zero is at the bottom of the board. */
    private int row;

    /** The column within the board of this Square. Columns are numbered from zero; column zero is at the left */
    private int col;

    /** The current Color of this Square */
    private Color color;

    /**
     * Creates a square and records its row and column
     */
    public Square (int row, int col)
    {
        this.row = row;
        this.col = col;
        this.color = BACKGROUND_COLOR;
    }

    /**
     * Returns the row of this Square
     */
    public int getRow ()
    {
        return row;
    }

    /**
     * Returns the column of this Square
     */
    public int getCol ()
    {
        return col;
    }

    /**
     * Sets the color of this square
     */
    public void setColor (Color color)
    {
        this.color = color;
    }

    /**
     * Paints this Square onto g
     */
    @Override
    public void paintComponent (Graphics g)
    {
        g.setColor(BOARD_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(color);
        g.fillRect(BORDER, BORDER, getWidth() - 2 * BORDER, getHeight() - 2 * BORDER);
    }
}
