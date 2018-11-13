package asteroids.participants;

import java.awt.Shape;
import java.awt.geom.*;
import asteroids.Controller;
import asteroids.Participant;
import asteroids.ParticipantCountdownTimer;
import asteroids.destroyers.*;
import static asteroids.Constants.*;

/**
 * Represents ships
 */
public class Ship extends Participant implements AsteroidDestroyer, AlienBulletDestroyer, AlienDestroyer
{
    // The outline of the ship
    private Shape outline;

    // Game controller
    private Controller controller;

    private boolean hasFlame;

    private int frameCount;

    // Constructs a ship at the specified coordinates
    // that is pointed in the given direction.
    public Ship (int x, int y, double direction, Controller controller)
    {

        this.controller = controller;
        setPosition(x, y);
        setRotation(direction);

        // makes a basic shape
        Path2D.Double poly = new Path2D.Double();
        poly.moveTo(20, 0);
        poly.lineTo(-20, 12);
        poly.lineTo(-13, 10);
        poly.lineTo(-13, -10);
        poly.lineTo(-20, -12);
        poly.closePath();
        outline = poly;

        hasFlame = false;
        frameCount = 0;
    }

    /**
     * Modifies the shape of the ship to include a flame
     */
    public void addFlame ()
    {
        if (hasFlame == false)
        {
            new ParticipantCountdownTimer(this, "flame", 0);
        }
        else if (hasFlame == true)
        {
            new ParticipantCountdownTimer(this, "extinguish", 100);
        }
    }

    /**
     * turns the x-coordinate of the point on the screen where the ship's nose is located.
     */
    public double getXNose ()
    {
        Point2D.Double point = new Point2D.Double(20, 0);
        transformPoint(point);
        return point.getX();
    }

    /**
     * Returns the x-coordinate of the point on the screen where the ship's nose is located.
     */
    public double getYNose ()
    {
        Point2D.Double point = new Point2D.Double(20, 0);
        transformPoint(point);
        return point.getY();
    }

    /**
     * Returns the outline
     */
    @Override
    protected Shape getOutline ()
    {
        return outline;
    }

    /**
     * Customizes the base move method by imposing friction
     */
    @Override
    public void move ()
    {
        applyFriction(SHIP_FRICTION);
        super.move();
        frameCount++;
    }

    /**
     * Returns the number of frames that have occured
     */
    public int frameCount ()
    {
        return frameCount;
    }

    /**
     * Turns right by Pi/16 radians
     */
    public void turnRight ()
    {
        rotate(Math.PI / 16);
    }

    /**
     * Turns left by Pi/16 radians
     */
    public void turnLeft ()
    {
        rotate(-Math.PI / 16);
    }

    /**
     * Accelerates by SHIP_ACCELERATION
     */
    public void accelerate ()
    {
        accelerate(SHIP_ACCELERATION);
    }

    /**
     * When a Ship collides with a ShipKiller, it expires
     */
    @Override
    public void collidedWith (Participant p)
    {
        if (p instanceof ShipDestroyer)
        {

            // Expire the ship from the game
            Participant.expire(this);

            // Tell the controller the ship was destroyed
            controller.shipDestroyed();
        }
    }

    /**
     * This method is invoked when a ParticipantCountdownTimer completes its countdown.
     */
    @Override
    public void countdownComplete (Object payload)
    {
//adds a flame to the ship
        if (payload.equals("flame"))
        {
            Path2D.Double poly = new Path2D.Double();
            poly.moveTo(20, 0);
            poly.lineTo(-20, 12);
            poly.lineTo(-13, 10);
            poly.lineTo(-13, -10);
            poly.lineTo(-20, -12);
            poly.closePath();
            poly.moveTo(-13, 5);
            poly.lineTo(-25, 0);
            poly.lineTo(-13, -5);
            outline = poly;
            hasFlame = true;
            new ParticipantCountdownTimer(this, "extinguished", 100);
        }
        
        //gets rid of a flame
        else if (payload.equals("extinguished"))
        {
            Path2D.Double poly = new Path2D.Double();
            poly.moveTo(20, 0);
            poly.lineTo(-20, 12);
            poly.lineTo(-13, 10);
            poly.lineTo(-13, -10);
            poly.lineTo(-20, -12);
            poly.closePath();
            outline = poly;
            hasFlame = false;
        }
        //gets the ship ready to add a flame, if there's already one there
        else if (payload.equals("extinguish"))
        {
            Path2D.Double poly = new Path2D.Double();
            poly.moveTo(20, 0);
            poly.lineTo(-20, 12);
            poly.lineTo(-13, 10);
            poly.lineTo(-13, -10);
            poly.lineTo(-20, -12);
            poly.closePath();
            outline = poly;
            hasFlame = false;
            new ParticipantCountdownTimer(this, "add", 100);
        }
        //calls the add flame method
        else if (payload.equals("add"))
        {
            addFlame();
        }

    }

}
