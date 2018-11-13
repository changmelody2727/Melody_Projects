package asteroids;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.*;
import static asteroids.Constants.*;

/**
 * The area of the display in which the game takes place.
 */
@SuppressWarnings("serial")
public class Screen extends JPanel 
{
    // Legend that is displayed across the screen
    private String legend;

    // Game controller
    private Controller controller;

    /**
     * Creates an empty screen
     */
    public Screen (Controller controller)
    {
        this.controller = controller;
        legend = "";
        setPreferredSize(new Dimension(SIZE, SIZE));
        setMinimumSize(new Dimension(SIZE, SIZE));
        setBackground(Color.black);
        setForeground(Color.white);
        // setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 120));
        setFocusable(true);
    }

    /**
     * Set the legend
     */
    public void setLegend (String legend)
    {
        this.legend = legend;
    }

    /**
     * Paint the participants onto this panel
     */
    @Override
    public void paintComponent (Graphics g)
    {
        // Do the default painting
        super.paintComponent(g);

        // Draw each participant in its proper place
        Iterator<Participant> iter = controller.getParticipants();
        while (iter.hasNext())
        {
            iter.next().draw((Graphics2D) g);
        }

        // Draw the legend across the middle of the panel
        int size = g.getFontMetrics().stringWidth(legend);

        if (enhanced == false)
        {
            setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 120));
            g.drawString(legend, (SIZE - size) / 2, SIZE / 2);
        }
        
        //draws the labels for the enhanced version.
        if (enhanced == true)
        {
            g.drawString(legend, (SIZE - size) / 2, SIZE / 2);
            g.drawString("Level: " + controller.getLevel(), 50, 100);
            g.drawString("Lives: " + controller.getLives(), 50, 125);
            g.drawString("Score: " + controller.getScores(), 50, 150);
            g.drawString("High Score: " + controller.getHighScore(), 50, 175);
            setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));        }
    }

}
