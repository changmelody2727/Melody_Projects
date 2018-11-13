package asteroids;

import java.io.BufferedInputStream;
import java.io.IOException;
import javax.sound.sampled.*;

/**
 * The sound files used by the project, all created at the same time. this class should be used.
 */
public class Sounds
{
    // the clips that can be played in the game
    private Clip fireClip;
    private Clip smallSaucerClip;
    private Clip bangAlienClip;
    private Clip bangLargeClip;
    private Clip bangMediumClip;
    private Clip bangSmallClip;
    private Clip bangShipClip;
    private Clip beat1Clip;
    private Clip beat2Clip;
    private Clip bigSaucerClip;
    private Clip thrustClip;

    /**
     * Stores created sound clips into the variables above.
     */
    public Sounds ()
    {

        // We create the clips in advance so that there will be no delay
        // when we need to play them back. Note that the actual wav
        // files are stored in the "sounds" project.
        bangAlienClip = (createClip("/sounds/bangAlienShip.wav"));
        bangLargeClip = (createClip("/sounds/bangLarge.wav"));
        bangMediumClip = (createClip("/sounds/bangMedium.wav"));
        bangSmallClip = (createClip("/sounds/bangSmall.wav"));
        bangShipClip = (createClip("/sounds/bangShip.wav"));
        beat1Clip = (createClip("/sounds/beat1.wav"));
        beat2Clip = (createClip("/sounds/beat2.wav"));
        fireClip = (createClip("/sounds/fire.wav"));
        smallSaucerClip = (createClip("/sounds/saucerSmall.wav"));
        bigSaucerClip = (createClip("/sounds/saucerBig.wav"));
        thrustClip = (createClip("/sounds/thrust.wav"));

    }

    /**
     * Creates an audio clip from a sound file.
     */
    public Clip createClip (String soundFile)
    {
        // Opening the sound file this way will work no matter how the
        // project is exported. The only restriction is that the
        // sound files must be stored in a package.
        try (BufferedInputStream sound = new BufferedInputStream(getClass().getResourceAsStream(soundFile)))
        {
            // Create and return a Clip that will play a sound file. There are
            // various reasons that the creation attempt could fail. If it
            // fails, return null.
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            return clip;
        }
        catch (LineUnavailableException e)
        {
            return null;
        }
        catch (IOException e)
        {
            return null;
        }
        catch (UnsupportedAudioFileException e)
        {
            return null;
        }
    }

    /**
     * Plays a clip one time through
     */
    public void playClip (Clip c)
    {
        // We "rewind" the clip and play it.
        if (c != null)
        {
            if (c.isRunning())
            {
                c.stop();
            }
            c.setFramePosition(0);
            c.start();
        }
    }

    /**
     * Plays the clip on a continuous loop
     */
    public void loopClip (Clip c)
    {

        if (c != null)
        {
            if (c.isRunning())
            {
                c.stop();
            }
            c.setFramePosition(0);
            c.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
/**
 * Stops a clip that is currently running.
 */
    public void stopClip (Clip c)
    {
        if (c != null)
        {
            if (c.isRunning())
            {
                c.stop();
            }
            c.setFramePosition(0);
        }
    }

    public Clip getFireClip ()
    {
        return fireClip;
    }

    public void setFireClip (Clip fireClip)
    {
        this.fireClip = fireClip;
    }

    public Clip getSmallSaucerClip ()
    {
        return smallSaucerClip;
    }

    public void setSmallSaucerClip (Clip smallSaucerClip)
    {
        this.smallSaucerClip = smallSaucerClip;
    }

    public Clip getBangAlienClip ()
    {
        return bangAlienClip;
    }

    public void setBangAlienClip (Clip bangAlienClip)
    {
        this.bangAlienClip = bangAlienClip;
    }

    public Clip getBangMediumClip ()
    {
        return bangMediumClip;
    }

    public void setBangMediumClip (Clip bangMediumClip)
    {
        this.bangMediumClip = bangMediumClip;
    }

    public Clip getBangShipClip ()
    {
        return bangShipClip;
    }

    public void setBangShipClip (Clip bangShipClip)
    {
        this.bangShipClip = bangShipClip;
    }

    public Clip getBeat1Clip ()
    {
        return beat1Clip;
    }

    public void setBeat1Clip (Clip beat1Clip)
    {
        this.beat1Clip = beat1Clip;
    }

    public Clip getBeat2Clip ()
    {
        return beat2Clip;
    }

    public void setBeat2Clip (Clip beat2Clip)
    {
        this.beat2Clip = beat2Clip;
    }

    public Clip getBigSaucerClip ()
    {
        return bigSaucerClip;
    }

    public void setBigSaucerClip (Clip bigSaucerClip)
    {
        this.bigSaucerClip = bigSaucerClip;
    }

    public Clip getThrustClip ()
    {
        return thrustClip;
    }

    public void setThrustClip (Clip thrustClip)
    {
        this.thrustClip = thrustClip;
    }

    public Clip getBangSmallClip ()
    {
        return bangSmallClip;
    }

    public void setBangSmallClip (Clip bangSmallClip)
    {
        this.bangSmallClip = bangSmallClip;
    }

    public Clip getBangLargeClip ()
    {
        return bangLargeClip;
    }

    public void setBangLargeClip (Clip bangLargeClip)
    {
        this.bangLargeClip = bangLargeClip;
    }
}