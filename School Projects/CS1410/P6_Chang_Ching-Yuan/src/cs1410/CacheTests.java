package cs1410;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.Test;

public class CacheTests
{
    /**
     * An example test for Cache objects
     */
    @Test
    public void test1 ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
        assertEquals("GCRQWK", c.getGcCode());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testException ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850");
        assertEquals("GCRQWK", c.getGcCode());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testException2 ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045\t123");
        assertEquals("GCRQWK", c.getGcCode());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testException3 ()
    {
        Cache c = new Cache("GdRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
        assertEquals("GCRQWK", c.getGcCode());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testException4 ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.7\t3\tN40 45.850\tW111 48.045");
        assertEquals("GCRQWK", c.getGcCode());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testException5 ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t0\tN40 45.850\tW111 48.045");
        assertEquals("GCRQWK", c.getGcCode());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testException6 ()
    {
        Cache c = new Cache("GCRQWK\t \tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
        assertEquals("GCRQWK", c.getGcCode());
    }
    
    @Test
    public void testTitle ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
        assertEquals("Old Three Tooth", c.getTitle());
    }
    @Test
    public void testOwner ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
        assertEquals("geocadet", c.getOwner());
    }
    
    @Test
    public void testDifficulty ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
        assertEquals(3.5, c.getDifficulty(), 1e-4);
    }
    
    @Test
    public void testTerrain ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
        assertEquals(3.0, c.getTerrain(), 1e-4);
    }
    
    @Test
    public void testLatitude ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
        assertEquals("N40 45.850", c.getLatitude());
    }
    
    @Test
    public void testLongtitude ()
    {
        Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
        assertEquals("W111 48.045", c.getLongitude());
    }
   
    
}
