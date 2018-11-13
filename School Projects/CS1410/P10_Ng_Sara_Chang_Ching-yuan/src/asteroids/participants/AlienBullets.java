package asteroids.participants;

import asteroids.Controller;
import asteroids.Participant;
import asteroids.destroyers.AlienBulletDestroyer;
import asteroids.destroyers.BulletDestroyer;
import asteroids.destroyers.ShipDestroyer;

public class AlienBullets extends Bullets implements ShipDestroyer, BulletDestroyer
{

    private Controller controller;

    /**
     * Allows for placement of an alien bullet
     * 
     */
    public AlienBullets (double x, double y, double rotation, Controller control)
    {
        super(x, y, rotation, control);
        controller = control;

    }

    /** 
     * Destroys an alien bullet if it collides with an alien bullet destroyer
     */
    @Override
    public void collidedWith (Participant p)
    {
        if (p instanceof AlienBulletDestroyer)
        {
            // Expire the bullet from the game
            Participant.expire(this);

        }

    }

}
