package c4;

import static org.junit.Assert.*;
import org.junit.Test;

public class C4BoardTest
{

    @Test
    public void newGame ()
    {
        C4Board b = new C4Board(6, 7);

        b.moveTo(0);
        b.moveTo(1);
        b.moveTo(2);
        b.moveTo(3);
        b.moveTo(4);
        b.moveTo(5);
        b.moveTo(6);

        b.newGame();

        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                assertEquals(0, b.getOccupant(i, j));
            }
        }
    }

    @Test
    public void newGame2 ()
    {
        C4Board b = new C4Board(4, 5);

        b.moveTo(0);
        b.moveTo(1);
        b.moveTo(2);
        b.moveTo(3);
        b.moveTo(4);

        b.newGame();

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                assertEquals(0, b.getOccupant(i, j));
            }
        }
    }

    @Test
    public void newGamePlayer ()
    {
        C4Board b = new C4Board(6, 7);

        assertEquals(1, b.getToMove());
    }

    @Test
    public void newGamePlayer2 ()
    {
        C4Board b = new C4Board(6, 7);

        b.newGame();

        assertEquals(2, b.getToMove());
    }

    @Test
    public void newGamePlayer3 ()
    {
        C4Board b = new C4Board(6, 7);

        b.newGame();
        b.newGame();

        assertEquals(1, b.getToMove());
    }

    @Test(expected = IllegalArgumentException.class)
    public void C4BoardException ()
    {

        new C4Board(4, 3);

    }

    @Test (expected = IllegalArgumentException.class)
    public void moveTo ()
    {
        C4Board b = new C4Board(6, 7);
        b.moveTo(8);

    }

    @Test(expected = IllegalArgumentException.class)
    public void getOccupantRows ()
    {
        C4Board b = new C4Board(6, 7);
        b.getOccupant(7,3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getOccupantCols ()
    {
        C4Board b = new C4Board(6, 7);
        b.getOccupant(3,-1);
    }
    
    @Test
    public void getToMove ()
    {
        C4Board b = new C4Board(6, 7);
        b.newGame();
        b.moveTo(0);
        assertEquals(1, b.getToMove());

    }

    @Test
    public void getWinsForP1 ()
    {
        C4Board b = new C4Board(6, 7);
        b.moveTo(0);
        b.moveTo(1);
        b.moveTo(0);
        b.moveTo(1); 
        b.moveTo(0);
        b.moveTo(1);
        b.moveTo(0);
        b.moveTo(1);
        
        assertEquals(1, b.getWinsForP1());

    }

    @Test
    public void getWinsForP2 ()
    {
        C4Board b = new C4Board(6, 7);
      
        b.moveTo(0);
        b.moveTo(1);
        b.moveTo(0);
        b.moveTo(1); 
        b.moveTo(0);
        b.moveTo(1);
        b.moveTo(2);
        b.moveTo(1);

        
        assertEquals(1, b.getWinsForP2());

    }

    @Test
    public void getTies ()
    {
        C4Board b = new C4Board(4,4);
        b.moveTo(0);
        b.moveTo(1);
        b.moveTo(0);
        b.moveTo(1); 
        b.moveTo(0);
        b.moveTo(1);
        b.moveTo(2);
        b.moveTo(3);
        b.moveTo(2);
        b.moveTo(3);
        b.moveTo(2);
        b.moveTo(3);
        b.moveTo(3);
        b.moveTo(2);
        b.moveTo(1);
        b.moveTo(0);
        
        assertEquals(1, b.getTies());

    }

}
