package asteroids;

import javax.swing.*;
import asteroids.participants.Ship;
import java.awt.*;
import static asteroids.Constants.*;

/**
 * Defines the top-level appearance of an Asteroids game.
 */
@SuppressWarnings("serial")
public class Display extends JFrame
{
    // The area where the action takes place
    private Screen screen;

    private Controller Controller;

    private Asteroids asteroids;

    // The label for lives, level, and score.
    private JLabel level;
    private JLabel lives;
    private JLabel score;
    private JLabel high;
    // private JLabel missiles;

    /**
     * Lays out the game and creates the controller
     */
    public Display (Controller controller)
    {
        // Title at the top
        setTitle(TITLE);

        // Default behavior on closing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The main playing area and the controller
        screen = new Screen(controller);

        Controller = controller;
        // This panel contains the screen to prevent the screen from being
        // resized
        JPanel screenPanel = new JPanel();
        screenPanel.setLayout(new GridBagLayout());
        screenPanel.add(screen);

        // This panel contains buttons and labels
        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout());
        // controls.setBackground(Color.BLACK);

        controls.add(new JLabel());

        // The button that starts the game
        JButton startGame = new JButton(START_LABEL);
        controls.add(startGame);
        controls.add(new JLabel());

        // Organize everything
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(screenPanel, "Center");
        mainPanel.add(controls, "North");
        setContentPane(mainPanel);
        pack();

        level = new JLabel("Level: 1 ");
        lives = new JLabel("Lives: 3 ");
        score = new JLabel("Score: 0 ");
        high = new JLabel("High Score: 0 ");

        if (enhanced == false)
        {
            // Label for level, lives, score are placed.
            controls.add(level);
            controls.add(lives);
            controls.add(score);
        }

        else if (enhanced == true)
        {
            JButton teleport = new JButton("Teleport");
            controls.add(teleport);
            controls.add(new JLabel());
  
        }
        else
        {
            controls.add(new JPanel());
        }

        // Connect the controller to the start button
        startGame.addActionListener(controller);
    }

    /**
     * Called when it is time to update the screen display. This is what drives the animation.
     */
    public void refresh ()
    {
        setLevelCount(Controller.getLevel());
        setLivesCount(Controller.getLives());
        setScoreCount(Controller.getScores());
        screen.repaint();
    }

    /**
     * Sets the large legend
     */
    public void setLegend (String s)
    {
        screen.setLegend(s);
    }

    /**
     * Set the label that display the level
     */
    public void setLevelCount (int n)
    {
        level.setText("Level: " + n);
    }

    /**
     * Set the label that display the lives
     */
    public void setLivesCount (int n)
    {
        lives.setText(" Lives: " + n);
    }

    /**
     * Set the label that display the score
     */
    public void setScoreCount (int n)
    {
        score.setText(" Score: " + n);
    }
    
}
