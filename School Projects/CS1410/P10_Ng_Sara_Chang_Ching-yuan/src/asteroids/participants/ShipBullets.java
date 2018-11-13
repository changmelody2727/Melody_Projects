package asteroids.participants;

import asteroids.Controller;
import asteroids.Participant;
import asteroids.destroyers.AlienBulletDestroyer;
import asteroids.destroyers.AlienDestroyer;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.destroyers.BulletDestroyer;

public class ShipBullets extends Bullets implements AlienDestroyer, AsteroidDestroyer, AlienBulletDestroyer
{

    private Controller controller;

    /**
     * Constructs the bullets of the type that are used for ships
     */
    public ShipBullets (double x, double y, double rotation, Controller controller)
    {
        super(x, y, rotation, controller);
        this.controller = controller;

    }

    /**
     * Kills the bullet under appropriate circumstances.
     */
    @Override
    public void collidedWith (Participant p)
    {
        if (p instanceof BulletDestroyer)
        {
            // Expire the bullet from the game
            Participant.expire(this);

            controller.bulletDestroyed();
        }

    }

}
