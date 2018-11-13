package asteroids.participants;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import asteroids.Participant;
import asteroids.ParticipantCountdownTimer;
import asteroids.destroyers.BulletDestroyer;
import static asteroids.Constants.*;
import asteroids.Controller;

public class Bullets extends Participant
{
    // The outline of the ship
    private Shape outline;

    private Controller controller;

    /**
     * A constructor that creates generic bullets.
     */
    public Bullets (double x, double y, double rotation, Controller controller)
    {
        this.controller = controller;
        setPosition(x, y);
        setVelocity(BULLET_SPEED, rotation);

        Ellipse2D.Double circle = new Ellipse2D.Double(1, 1, 1, 1);
        outline = circle;

        new ParticipantCountdownTimer(this, "expire", BULLET_DURATION);

    }

    @Override
    protected Shape getOutline ()
    {
        return outline;
    }
  

    /**
     * This method is invoked when a ParticipantCountdownTimer completes its countdown.
     */
    @Override
    public void countdownComplete (Object payload)
    {
        //kills bullets after their lifespan is over
        if (payload.equals("expire"))
        {
            Participant.expire(this);
            controller.bulletDestroyed();
        }
    }

    @Override
    public void collidedWith (Participant p)
    {
        
    }

}
