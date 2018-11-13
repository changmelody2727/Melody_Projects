package asteroids.participants;

import static asteroids.Constants.RANDOM;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import asteroids.Participant;

public class Debris extends Participant
{
    private Shape outline;

    private final double velocity = 1;

    /**
     * Constructs debris of type 0 for asteroids and type 1 for ships
     */
    public Debris (int variety, double x, double y, double roatation, double direction)
    {
        setPosition(x, y);
        setVelocity(velocity, direction);
        setRotation(roatation);

        if (variety == 0)
        {
            Ellipse2D.Double circle = new Ellipse2D.Double(1, 1, 1, 1);
            outline = circle;
        }
        else
        {
            Path2D.Double poly = new Path2D.Double();
            poly.moveTo(0, 0);
            poly.lineTo(20, 0);
            outline = poly;
        }

    }

    @Override
    protected Shape getOutline ()
    {
        return outline;
    }

    /**
     * this is ignored for all debris
     */
    @Override
    public void collidedWith (Participant p)
    {
    }

    /**
     * This method is invoked when a ParticipantCountdownTimer completes its countdown.
     */
    @Override
    public void countdownComplete (Object payload)
    {
        if (payload.equals("debris"))
        {
            Participant.expire(this);
        }
    }

}
