package LightsOut;

import static org.junit.Assert.*;
import java.util.Random;
import org.junit.Test;

public class LightsOutModelTest
{

    @Test
    public void newGame ()
    {
        LightsOutModel b = new LightsOutModel(5, 5);

        b.newGame();
        b.moveTo(0, 3);
        b.moveTo(1, 2);
        b.moveTo(2, 1);
        b.moveTo(3, 3);

        assertEquals(4, b.getClickCount());
        
    }

    @Test
    public void moveTo ()
    {
        LightsOutModel b = new LightsOutModel(5, 5);
        b.moveTo(0, 3);
        b.moveTo(1, 2);
        b.moveTo(2, 1);
        b.moveTo(3, 3);

        assertEquals(1, b.getOccupant(3, 3));

    }

    @Test
    public void getOccupant ()
    {
        LightsOutModel b = new LightsOutModel(5, 5);
        b.moveTo(0, 3);

        assertEquals(1, b.getOccupant(0, 3));

    }

    @Test
    public void getClickCount ()
    {
        LightsOutModel b = new LightsOutModel(5, 5);

        b.moveTo(0, 3);
        b.moveTo(1, 2);
        b.moveTo(2, 1);
        b.moveTo(3, 3);

        assertEquals(4, b.getClickCount());
    }

    @Test
    public void getLevelCount ()
    {
        LightsOutModel b = new LightsOutModel(5, 5);

        b.moveTo(0, 3);
        b.moveTo(1, 2);
        b.moveTo(2, 1);
        b.moveTo(3, 3);

        assertEquals(0, b.getLevelCount());
    }


    @Test
    public void clear ()
    {
        LightsOutModel b = new LightsOutModel(5, 5);

        b.moveTo(0, 3);
        b.moveTo(1, 2);
        b.clear();
        
        
    }
    
    @Test
    public void clear2 ()
    {
        LightsOutModel b = new LightsOutModel(5, 5);

        b.moveTo(0, 3);
        b.moveTo(1, 2);
        b.enterManualMode();
        b.clear();               

        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                assertEquals(0, b.getOccupant(i, j));
            }
        }
    }
    @Test
    public void getWin ()
    {
        LightsOutModel b = new LightsOutModel(5, 5);
        b.moveTo(0, 3);
        b.moveTo(0, 3);
        
        assertEquals(true, b.getWin());
    }

    @Test
    public void onOffSwitch ()
    {
        LightsOutModel b = new LightsOutModel(5, 5);

         b.moveTo(0, 3);
         b.moveTo(0, 3);
         b.onOffSwitch(0,3);
        
         assertEquals(1, b.getOccupant(0,3));
    }

}
