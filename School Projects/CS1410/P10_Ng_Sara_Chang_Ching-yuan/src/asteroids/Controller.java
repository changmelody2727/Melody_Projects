package asteroids;

import java.awt.event.*;
import java.util.Iterator;
import javax.swing.*;
import asteroids.participants.Alien;
import asteroids.participants.AlienBullets;
import asteroids.participants.Asteroid;
import asteroids.participants.Ship;
import asteroids.participants.ShipBullets;
import asteroids.participants.Debris;
import static asteroids.Constants.*;

/**
 * Controls a game of Asteroids.
 */
public class Controller implements KeyListener, ActionListener
{

    // The state of all the Participants
    private ParticipantState pstate;

    // The ship (if one is active) or null (otherwise)
    private Ship ship;

    // The Alien (if one is active) or null (otherwise)
    private Alien alien;

    // The debris (if one is active) or null (otherwise)
    private Debris debris;

    // The sound (if one is active) or null (otherwise)
    private Sounds theSounds;

    // Count how many bullets are on the screen.
    private int bulletCount;

    // When the refresh timer goes off, it is time to refresh the animation.
    private Timer refreshTimer;
    // when this timer goes off, it's time to sound a new beat
    private Timer beatTimer;

    // The time at which a transition to a new stage of the game should be made.
    // A transition is scheduled a few seconds in the future to give the user
    // time to see what has happened before doing something like going to a new
    // level or resetting the current level.
    private long transitionTime;

    // Number of lives left
    private int lives;

    // The game display
    private Display display;

    // The level player is on
    private int level;

    // Keeps track of the Score
    private int score;

    // Used in the enhanced version to note whether an extra life has been given.
    private int livesIncrement = 0;

    // counts the high score for the enhanced version
    private int highestScore;

    // used to count the beat and the time between beats for the background sound
    private int beatCount = 0;
    private int beatInterval = INITIAL_BEAT;

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    /**
     * Constructs a controller to coordinate the game and screen
     */
    public Controller (boolean enhance)
    {
        // Initialize the ParticipantState
        pstate = new ParticipantState();

        // Set up the refresh timer.
        refreshTimer = new Timer(FRAME_INTERVAL, this);
        beatTimer = new Timer(1000, this);

        // Clear the transitionTime
        transitionTime = Long.MAX_VALUE;

        // Record the display object
        display = new Display(this);

        // Bring up the splash screen and start the refresh timer
        splashScreen();
        display.setVisible(true);
        refreshTimer.start();
        bulletCount = 0;
        level = 1;
        score = 0;
        enhanced = enhance;
        theSounds = new Sounds();
        // missiles = 1000;
        highestScore = 0;
        beatTimer.start();
    }

    /**
     * Returns the ship, or null if there isn't one
     */
    public Ship getShip ()
    {
        return ship;
    }

    /**
     * Configures the game screen to display the splash screen
     */
    private void splashScreen ()
    {
        // Clear the screen, reset the level, and display the legend
        clear();
        display.setLegend("Asteroids");

        // Place four asteroids near the corners of the screen.
        placeAsteroids();
    }

    /**
     * The game is over. Displays a message to that effect.
     */
    private void finalScreen ()
    {
        display.setLegend(GAME_OVER);
        display.removeKeyListener(this);
    }

    /**
     * Place a new ship in the center of the screen. Remove any existing ship first.
     */
    private void placeShip ()
    {
        // Place a new ship
        Participant.expire(ship);
        ship = new Ship(SIZE / 2, SIZE / 2, -Math.PI / 2, this);
        addParticipant(ship);
        display.setLegend("");
    }

    /**
     * Places four ( + )asteroids near the corners of the screen. Gives them random velocities and rotations.
     */
    private void placeAsteroids ()
    {
        // places four new asteroid for the first level
        addParticipant(new Asteroid(0, 2, EDGE_OFFSET + (-1 + RANDOM.nextInt(3) * RANDOM.nextInt(50)),
                EDGE_OFFSET + (-1 + RANDOM.nextInt(3) * RANDOM.nextInt(50)), 3, this));
         addParticipant(new Asteroid(1, 2, SIZE - (EDGE_OFFSET + (-1 + RANDOM.nextInt(3) * RANDOM.nextInt(50))),
         EDGE_OFFSET + (-1 + RANDOM.nextInt(3) * RANDOM.nextInt(50)), 3, this));
         addParticipant(new Asteroid(2, 2, EDGE_OFFSET + (-1 + RANDOM.nextInt(3) * RANDOM.nextInt(50)),
         SIZE - (EDGE_OFFSET + (-1 + RANDOM.nextInt(3) * RANDOM.nextInt(50))), 3, this));
         addParticipant(new Asteroid(3, 2, SIZE - (EDGE_OFFSET + (-1 + RANDOM.nextInt(3) * RANDOM.nextInt(50))),
         SIZE - (EDGE_OFFSET + (-1 + RANDOM.nextInt(3) * RANDOM.nextInt(50))), 3, this));
        
        // adds one asteroid for every level
         for (int i = 1; i < level; i++)
         {
         addParticipant(new Asteroid(RANDOM.nextInt(4), 2,
         (EDGE_OFFSET + (-1 + RANDOM.nextInt(3) * RANDOM.nextInt(50))) + 450 * RANDOM.nextInt(2),
         (EDGE_OFFSET + (-1 + RANDOM.nextInt(3) * RANDOM.nextInt(50))) + 450 * RANDOM.nextInt(2), 3, this));
         }
    }

    /**
     * Constructs an alien and determines size and position (random)
     */
    private void placeAlien ()
    {

        // creates either a medium or a small alien ship, depending on a random value
        if (level > 2)
        {
            int i = RANDOM.nextInt(2);
            if (i == 1)
            {
                theSounds.loopClip(theSounds.getBigSaucerClip());
            }
            else if (i == 0)
            {
                theSounds.loopClip(theSounds.getSmallSaucerClip());
            }

            alien = new Alien(1, SIZE * RANDOM.nextInt(2), EDGE_OFFSET + RANDOM.nextInt(SIZE - (2 * EDGE_OFFSET)), 1,
                    this);
            addParticipant(alien);
            placeAlienBullet(i);
        }

        // constructs only medium sized aliens
        else if (level == 2)
        {
            theSounds.loopClip(theSounds.getBigSaucerClip());
            alien = new Alien(1, SIZE * RANDOM.nextInt(2), EDGE_OFFSET + RANDOM.nextInt(SIZE - (2 * EDGE_OFFSET)), 1,
                    this);
            addParticipant(alien);
            placeAlienBullet(1);
        }

    }

    /**
     * A method that when called, starts firing bullets for an alien ship according to its size.
     * 
     * @param size
     */
    public void placeAlienBullet (int size)
    {
        if (size == 1)
        {
            addParticipant(new AlienBullets(alien.getXCenter(), alien.getYCenter(),
                    Participant.normalize(RANDOM.nextDouble() * 2 * Math.PI), this));
        }
        else if (size == 0 && ship != null)
        {
            // gets the angle needed to fire at the ship
            double a = 0;
            double b = 0;
            double position = 0;
            if (ship.getX() - alien.getX() > 0)
            {
                a = ship.getX() - alien.getX();
                b = ship.getY() - alien.getY();
                position = Math.atan(b / a);
            }
            else
            {
                a = ship.getX() - alien.getX();
                b = ship.getY() - alien.getY();
                position = Math.PI + Math.atan(b / a);
            }
            // places the alien bullet
            addParticipant(new AlienBullets(alien.getXCenter(), alien.getYCenter(), position, this));
        }
        theSounds.playClip(theSounds.getFireClip());
        new ParticipantCountdownTimer(alien, "shoot", 1000);
    }

    /**
     * This method is called when either a ship or an asteroid is destroyed. It places debris on the screen that dies
     * after a random time.
     */
    public void placeDebris (int variety, int n, double x, double y, double direction)
    {
        // creates asteroid debris
        if (variety == 0)
        {
            for (int i = 0; i < n; i++)
            {
                double debrisDirection = 0;
                if (direction == 0)
                {
                    debrisDirection = RANDOM.nextDouble() * 2 * Math.PI;
                }
                else
                {
                    debrisDirection = (direction - 0.5 * Math.PI + RANDOM.nextDouble());
                }
                debris = new Debris(0, x, y, RANDOM.nextDouble() + 0.5, debrisDirection);
                addParticipant(debris);
                new ParticipantCountdownTimer(debris, "debris", 2000);
            }
        }

        // creates ship debris
        else
        {
            for (int i = 0; i < n; i++)
            {
                double debrisDirection = 0;
                if (direction == 0)
                {
                    debrisDirection = RANDOM.nextDouble() * 2 * Math.PI;
                }
                else
                {
                    debrisDirection = (direction - 0.5 * Math.PI + RANDOM.nextDouble());
                }
                debris = new Debris(1, x, y, RANDOM.nextDouble() + 0.5, debrisDirection);
                addParticipant(debris);
                new ParticipantCountdownTimer(debris, "debris", RANDOM.nextInt(2000) + 500);
            }
        }
    }

    /**
     * Clears the screen so that nothing is displayed
     */
    private void clear ()
    {
        pstate.clear();
        display.setLegend("");
        ship = null;
    }

    /**
     * Sets things up and begins a new game.
     */
    private void initialScreen ()
    {
        // Clear the screen
        clear();

        // Place four asteroids
        placeAsteroids();

        // Place the ship
        placeShip();

        // Reset statistics
        lives = 3;
        level = 1;
        score = 0;

        // Start listening to events (but don't listen twice)
        display.removeKeyListener(this);
        display.addKeyListener(this);

        // Give focus to the game screen
        display.requestFocusInWindow();

    }

    /**
     * Adds a new Participant
     */
    public void addParticipant (Participant p)
    {
        pstate.addParticipant(p);
    }

    /**
     * The ship has been destroyed
     */
    public void shipDestroyed ()
    {
        // The sound when ship is destroyed
        theSounds.playClip(theSounds.getBangShipClip());

        // Debris appear after the ship is destoryed
        placeDebris(1, 4, ship.getX(), ship.getY(), RANDOM.nextInt(2));

        // Null out the ship
        ship = null;

        // Display a legend
        display.setLegend("Ouch!");

        // Decrement lives
        lives--;

        // Since the ship was destroyed, schedule a transition
        scheduleTransition(END_DELAY);
    }

    /**
     * An asteroid of the given size has been destroyed
     */
    public void asteroidDestroyed (int size, double x, double y)
    {
        // The sound when asteroid is destroyed
        if (size == 0)
        {
            theSounds.playClip(theSounds.getBangSmallClip());
        }
        else if (size == 1)
        {
            theSounds.playClip(theSounds.getBangMediumClip());
        }
        else if (size == 2)
        {
            theSounds.playClip(theSounds.getBangLargeClip());
        }

        // place the dust when an asteroid is destroyed
        placeDebris(0, 4, x, y, RANDOM.nextInt(2));

        // If all the asteroids are gone, schedule a transition
        if (pstate.countAsteroids() == 0)
        {
            scheduleTransition(END_DELAY);
            level++;
        }

        score = score + ASTEROID_SCORE[size];

    }

    /**
     * This method is called by the constructor of alien
     */
    public void alienDestroyed (int size)
    {
        // The sound when Alien is destroyed
        theSounds.playClip(theSounds.getBangAlienClip());

        // stops the looped alien sounds
        if (size == 1)
        {
            theSounds.stopClip(theSounds.getBigSaucerClip());
        }
        else
        {
            theSounds.stopClip(theSounds.getSmallSaucerClip());
        }

        // Adds the score when you destroy an alien ship
        score = score + ALIENSHIP_SCORE[size];
        alien = null;
        // The time between the placement of the alien ship
        scheduleTransition(ALIEN_DELAY + RANDOM.nextInt(ALIEN_DELAY + 1));
    }

    /**
     * decrements the bullet count when called by the bullet constructor
     */
    public void bulletDestroyed ()
    {
        bulletCount--;
    }

    /**
     * Schedules a transition m msecs in the future
     */
    private void scheduleTransition (int m)
    {
        transitionTime = System.currentTimeMillis() + m;
    }

    /**
     * returns the current level
     */
    public int getLevel ()
    {
        return level;
    }

    /**
     * returns the current lives. In enhanced games, lives is incremented if the score is 5000.
     */
    public int getLives ()
    {
        if (enhanced == true && score == 5000 && livesIncrement == 0)
        {
            lives++;
            livesIncrement = 1;
        }
        else if (enhanced == true && score == 10000 && livesIncrement == 1)
        {
            lives++;
            livesIncrement = 2;
        }
        return lives;
    }

    /**
     * returns the current score
     */
    public int getScores ()
    {
        return score;
    }

    /**
     * Used in the enhanced version to track the high score
     */
    public int getHighScore ()
    {
        if (score > highestScore)
        {
            highestScore = score;

        }
        return highestScore;
    }

    /**
     * Returns whether or not the game being played is enhanced.
     */
    public boolean getEnhance ()
    {
        return enhanced;
    }

    /**
     * This method will be invoked because of button presses and timer events.
     */
    @Override
    public void actionPerformed (ActionEvent e)
    {
        if (e.getSource() instanceof JButton)
        {

            // cares for button presses
            JButton b = (JButton) e.getSource();
            if (b.getText().equals(START_LABEL))
            {
                initialScreen();
            }

            // When the teleport button is clicked the ship instantly move to a random spot on the screen.
            if (b.getText().equals("Teleport"))
            {
                ship.setPosition(RANDOM.nextInt(750), RANDOM.nextInt(750));
            }
        }

        // Time to refresh the screen and deal with keyboard input
        else if (e.getSource() == refreshTimer)
        {

            // keyboard input is enacted with the refresh timer
            if (left == true && ship != null)
            {
                ship.turnLeft();

            }
            if (right == true && ship != null)
            {
                ship.turnRight();

            }
            if (up == true && ship != null)
            {
                ship.accelerate();
                ship.addFlame();
            }
            if (down == true && ship != null)
            {
                if (bulletCount < 8)
                {
                    theSounds.playClip(theSounds.getFireClip());
                    addParticipant(new ShipBullets(ship.getXNose(), ship.getYNose(), ship.getRotation(), this));
                    bulletCount++;
                    down = false;
                }
            }

            // It may be time to make a game transition
            performTransition();

            // Move the participants to their new locations
            pstate.moveParticipants();

            // Refresh screen
            display.refresh();

        }

        // plays the background beat if the beatTimer was the source of the call
        if (e.getSource() == beatTimer)

        {
            if (beatInterval >= FASTEST_BEAT)
            {
                if (beatCount % 2 == 0)
                {
                    theSounds.playClip(theSounds.getBeat1Clip());
                }
                else
                {
                    theSounds.playClip(theSounds.getBeat2Clip());
                }
                beatCount++;
                if (beatInterval == FASTEST_BEAT)
                {
                    beatTimer.setDelay(beatInterval);
                }
                else
                {
                    beatInterval = beatInterval - BEAT_DELTA;
                    beatTimer.setDelay(beatInterval);
                }
            }
        }
    }

    /**
     * Returns an iterator over the active participants
     */
    public Iterator<Participant> getParticipants ()
    {
        return pstate.getParticipants();
    }

    /**
     * If the transition time has been reached, transition to a new state
     */
    private void performTransition ()
    {
        // Do something only if the time has been reached
        if (transitionTime <= System.currentTimeMillis())
        {
            // Clear the transition time
            transitionTime = Long.MAX_VALUE;

            // If there are no lives left, the game is over. Show the final
            // screen.
            if (lives <= 0)
            {
                finalScreen();
            }

            // If the ship was destroyed, place a new one and continue
            else if (ship == null)
            {
                placeShip();
            }
            // Place asteroids for the next level
            if (pstate.countAsteroids() == 0)
            {
                placeAsteroids();
            }

            // If the alien ship was destroyed, place a new one and continue
            if (level > 1 && alien == null)
            {
                placeAlien();
            }
        }
    }

    /**
     * If a key of interest is pressed, record that it is down.
     */
    @Override
    public void keyPressed (KeyEvent e)
    {
        if ((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) && ship != null)
        {
            left = true;
        }
        else if ((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) && ship != null)
        {
            right = true;
        }
        else if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) && ship != null)
        {
            theSounds.loopClip(theSounds.getThrustClip());
            up = true;

        }
        else if ((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S
                || e.getKeyCode() == KeyEvent.VK_SPACE) && ship != null)
        {
            down = true;
        }

    }

    /**
     * Ignore these events.
     */
    @Override
    public void keyTyped (KeyEvent e)
    {

    }

    /**
     * When a key is released, the boolean set to monitor that key is set to false.
     */
    @Override
    public void keyReleased (KeyEvent e)
    {
        if ((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A))
        {
            left = false;
        }
        else if ((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D))
        {
            right = false;
        }
        else if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W))
        {
            up = false;
            theSounds.stopClip(theSounds.getThrustClip());
        }
        else if ((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S
                || e.getKeyCode() == KeyEvent.VK_SPACE))
        {
            down = false;
        }

    }
}