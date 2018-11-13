package asteroids.participants;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import asteroids.Controller;
import asteroids.Participant;
import asteroids.ParticipantCountdownTimer;
import asteroids.destroyers.AlienDestroyer;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.destroyers.BulletDestroyer;
import asteroids.destroyers.ShipDestroyer;
import static asteroids.Constants.*;

public class Alien extends Participant implements AsteroidDestroyer, BulletDestroyer, ShipDestroyer
{
    // The size of the asteroid (0 = small, 1 = medium, 2 = large)
    private int size;

    // The outline of the asteroid
    private Shape outline;

    // The game controller
    private Controller controller;

    private int Speed;

    public Alien (int size, double x, double y, int speed, Controller controller)
    {
        // Make sure size and variety are valid
        if (size != 0 && size != 1)
        {
            throw new IllegalArgumentException("Invalid alien size: " + size);
        }

        // Create the alien Ship
        this.controller = controller;
        this.size = size;
        setPosition(x, y);
        if (speed == 0)
        {
            Speed = FAST_ASTEROID_SPEED;
        }
        else
        {
            Speed = SLOW_ASTEROID_SPEED;
        }

        // tells the ship it's going to start moving
        new ParticipantCountdownTimer(this, "moves", RANDOM.nextInt(1000));

        setVelocity(speed, -1 + RANDOM.nextInt(3));

        createAlianOutline(size);

    }

    /**
     * Creates the appropriate outline for an alien ship and scales if necessary
     * 
     * @param size
     */
    private void createAlianOutline (int size)
    {
        // This will contain the outline
        Path2D.Double poly = new Path2D.Double();

        // Fill out according to variety
        poly.moveTo(-18, 0);
        poly.lineTo(-9, -9);
        poly.lineTo(9, -9);
        poly.lineTo(18, 0);
        poly.lineTo(12, 9);
        poly.lineTo(-12, 9);
        poly.lineTo(-18, 0);
        poly.lineTo(18, 0);
        poly.moveTo(9, -9);
        poly.lineTo(6, -15);
        poly.lineTo(-6, -15);
        poly.lineTo(-9, -9);

        double scale = ALIENSHIP_SCALE[size];
        poly.transform(AffineTransform.getScaleInstance(scale, scale));

        // Save the outline
        outline = poly;
    }

    /**
     * Returns the size of the alien
     */
    public int getSize ()
    {
        return size;
    }

    /**
     * Returns the outline of the alien
     */
    @Override
    protected Shape getOutline ()
    {
        return outline;
    }

    /**
     * Returns the middle x point on the screen where the alien is
     * 
     * @return
     */
    public double getXCenter ()
    {
        Point2D.Double point = new Point2D.Double(0, 0);
        transformPoint(point);
        return point.getX();
    }

    /**
     * Returns the middle y point on the screen where the alien is
     * 
     * @return
     */
    public double getYCenter ()
    {
        Point2D.Double point = new Point2D.Double(0, 0);
        transformPoint(point);
        return point.getY();
    }

    /**
     * Called when the alien hits anything, kills the alien if required
     */
    @Override
    public void collidedWith (Participant p)
    {
        if (p instanceof AlienDestroyer)
        {
            // Expire the alien ship
            Participant.expire(this);

            // Inform the controller
            controller.alienDestroyed(size);
        }

    }

    /**
     * This method is invoked when a ParticipantCountdownTimer completes its countdown.
     */
    @Override
    public void countdownComplete (Object payload)
    {
        // Gives the alien a new random path
        if (payload.equals("moves"))
        {
            setVelocity(Speed, -1 + RANDOM.nextInt(3));
            new ParticipantCountdownTimer(this, "moves", RANDOM.nextInt(1000));
        }
        
        //allows the alien to shart shooting
        if (payload.equals("shoot"))
        {
            controller.placeAlienBullet(this.getSize());
        }

    }

}
