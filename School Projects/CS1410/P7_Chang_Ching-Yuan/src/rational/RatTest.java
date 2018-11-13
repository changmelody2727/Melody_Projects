package rational;

import static org.junit.Assert.*;
import java.math.BigInteger;
import org.junit.Test;

public class RatTest
{
    @Test
    public void testConstructor1 ()
    {
        Rat r = new Rat();
        assertEquals("0", r.toString());
    }

    @Test
    public void testConstructor2 ()
    {
        Rat r = new Rat(5);
        assertEquals("5", r.toString());
    }

    @Test
    public void testConstructor3 ()
    {
        Rat r1 = new Rat(1, 2);
        assertEquals("1/2", r1.toString());

        Rat r2 = new Rat(5, 15);
        assertEquals("1/3", r2.toString());

        Rat r3 = new Rat(-4, -2);
        assertEquals("2", r3.toString());

        Rat r4 = new Rat(6, -8);
        assertEquals("-3/4", r4.toString());

        Rat r5 = new Rat(3, -15);
        assertEquals("-1/5", r5.toString());
    }
    
    @Test
    public void testConstructor4 ()
    {
        Rat r1 = new Rat(BigInteger.valueOf(1), BigInteger.valueOf(2));
        assertEquals("1/2", r1.toString());

        Rat r2 = new Rat(BigInteger.valueOf(5), BigInteger.valueOf(15));
        assertEquals("1/3", r2.toString());

        Rat r3 = new Rat(BigInteger.valueOf(-4), BigInteger.valueOf(-2));
        assertEquals("2", r3.toString());

        Rat r4 = new Rat(BigInteger.valueOf(6), BigInteger.valueOf(-8));
        assertEquals("-3/4", r4.toString());

        Rat r5 = new Rat(BigInteger.valueOf(3), BigInteger.valueOf(-15));
        assertEquals("-1/5", r5.toString());
        
        try {
            new Rat(4, 0);
            fail("No exception thrown");
        }
        catch (IllegalArgumentException e)
        {           
        }
    }
    
    @Test
    public void testConstructor5 ()
    {
        Rat r1 = new Rat(BigInteger.valueOf(137473929), BigInteger.valueOf(238629376));
        assertEquals("137473929/238629376", r1.toString());

        Rat r2 = new Rat(BigInteger.valueOf(123456789), BigInteger.valueOf(12345));
        assertEquals("41152263/4115", r2.toString());

        Rat r3 = new Rat(BigInteger.valueOf(-123456789), BigInteger.valueOf(-123456789));
        assertEquals("1", r3.toString());

        Rat r4 = new Rat(BigInteger.valueOf(-2000000), BigInteger.valueOf(20000000));
        assertEquals("-1/10", r4.toString());

        Rat r5 = new Rat(BigInteger.valueOf(360001), BigInteger.valueOf(-4));
        assertEquals("-360001/4", r5.toString());
        
        try {
            new Rat(4, 0);
            fail("No exception thrown");
        }
        catch (IllegalArgumentException e)
        {           
        }
    }

    @Test
    public void testAdd ()
    {
        Rat r1 = new Rat(2,5);
        Rat r2 = new Rat(3,4);
        assertEquals("23/20", r1.add(r2).toString());
        
        r1 = new Rat(1, 7);
        r2 = new Rat(-1, 5);
        assertEquals("-2/35", r1.add(r2).toString());
    }

    @Test
    public void testSub ()
    {
        Rat r1 = new Rat(2,5);
        Rat r2 = new Rat(3,4);
        assertEquals("-7/20", r1.sub(r2).toString());
        
        r1 = new Rat(1, 7);
        r2 = new Rat(-1, 5);
        assertEquals("12/35", r1.sub(r2).toString());
    }

    @Test
    public void testMul ()
    {
        Rat r1 = new Rat(2,5);
        Rat r2 = new Rat(3,4);
        assertEquals("3/10", r1.mul(r2).toString());
        
        r1 = new Rat(1, 7);
        r2 = new Rat(-1, 5);
        assertEquals("-1/35", r1.mul(r2).toString());
    }

    @Test
    public void testDiv ()
    {
        Rat r1 = new Rat(2,5);
        Rat r2 = new Rat(3,4);
        assertEquals("8/15", r1.div(r2).toString());
        
        r1 = new Rat(1, 7);
        r2 = new Rat(-1, 5);
        assertEquals("-5/7", r1.div(r2).toString());
        
        try {
            r1 = new Rat(3,4);
            r2 = new Rat(0);
            r1.div(r2);
            fail("No exception thrown");
        }
        catch (IllegalArgumentException e)
        {
        }
    }

    @Test
    public void testCompareTo ()
    {
        Rat r1 = new Rat(3,4);
        Rat r2 = new Rat(6,8);
        Rat r3 = new Rat(1,2);
        assertEquals(0, r1.compareTo(r2));
        assertTrue(r1.compareTo(r3) > 0);
        assertTrue(r3.compareTo(r1) < 0);
    }


}
