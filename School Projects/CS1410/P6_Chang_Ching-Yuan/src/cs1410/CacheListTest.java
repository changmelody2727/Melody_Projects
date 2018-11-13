package cs1410;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.Test;

public class CacheListTest
{
    @Test
    public void test2 () throws IOException
    {
        CacheList clist = new CacheList(new Scanner("GCABCD\tAs The World Turns\tbunny\t1\t1\tN40 45.875\tW111 48.986\nGCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045\n"));
        clist.setTitleConstraint("Turns");
        ArrayList<Cache> selected = clist.select();
        assertEquals(1, selected.size());
        assertEquals("GCABCD", selected.get(0).getGcCode());
    }

    @Test
    public void testOwnerConstraint () throws IOException
    {
        String cache1 = "GCRQWK\tA\tgeocadet1\t3.5\t3\tN40 45.850\tW111 48.045\n";
        String cache2 = "GCRQWK\tB\tgeocadet2\t3.5\t3\tN40 45.850\tW111 48.045\n";
        String cache3 = "GCRQWK\tC\tgeocadet1\t3.5\t3\tN40 45.850\tW111 48.045\n";
        CacheList caches = new CacheList(new Scanner(cache1 + cache2 + cache3));
        caches.setOwnerConstraint("geocadet1");
        ArrayList<Cache> result = caches.select();
        assertEquals(2, result.size());
        assertEquals("A", result.get(0).getTitle());
        assertEquals("C", result.get(1).getTitle());
    }

    @Test
    public void testTitleConstraint () throws IOException
    {
        String cache1 = "GCRQWK\tA\tgeocadet1\t3.5\t3\tN40 45.850\tW111 48.045\n";
        String cache2 = "GCRQWK\tB\tgeocadet2\t3.5\t3\tN40 45.850\tW111 48.045\n";
        String cache3 = "GCRQWK\tC\tgeocadet1\t3.5\t3\tN40 45.850\tW111 48.045\n";
        CacheList caches = new CacheList(new Scanner(cache1 + cache2 + cache3));
        caches.setTitleConstraint("A");
        ArrayList<Cache> result = caches.select();
        assertEquals(1, result.size());
        assertEquals("A", result.get(0).getTitle());
    }
    
    @Test
    public void testDifficultyConstraints () throws IOException
    {
        String cache1 = "GCRQWK\tA\tgeocadet1\t3.5\t3\tN40 45.850\tW111 48.045\n";
        String cache2 = "GCRQWK\tB\tgeocadet2\t3.5\t3\tN40 45.850\tW111 48.045\n";
        String cache3 = "GCRQWK\tC\tgeocadet1\t3.5\t3\tN40 45.850\tW111 48.045\n";
        CacheList caches = new CacheList(new Scanner(cache1 + cache2 + cache3));
        caches.setDifficultyConstraints(1,5);
        ArrayList<Cache> result = caches.select();
        assertEquals(3, result.size());
        assertEquals("A", result.get(0).getTitle());
        assertEquals("B", result.get(1).getTitle());
        assertEquals("C", result.get(2).getTitle());
    }
    
    @Test
    public void testTerrainConstraints () throws IOException
    {
        String cache1 = "GCRQWK\tA\tgeocadet1\t3.5\t3\tN40 45.850\tW111 48.045\n";
        String cache2 = "GCRQWK\tB\tgeocadet2\t3.5\t3\tN40 45.850\tW111 48.045\n";
        String cache3 = "GCRQWK\tC\tgeocadet1\t3.5\t3\tN40 45.850\tW111 48.045\n";
        CacheList caches = new CacheList(new Scanner(cache1 + cache2 + cache3));
        caches.setTerrainConstraints(1,2);
        ArrayList<Cache> result = caches.select();
        assertEquals(0, result.size());
    }
    
    @Test
    public void select () throws IOException
    {
        String cache1 = "GCRQWK\tA\tgeocadet1\t3.5\t3\tN40 45.850\tW111 48.045\n";
        String cache2 = "GCRQWK\tB\tgeocadet2\t3.5\t3\tN40 45.850\tW111 48.045\n";
        String cache3 = "GCRQWK\tC\tgeocadet1\t3.5\t3\tN40 45.850\tW111 48.045\n";
        CacheList caches = new CacheList(new Scanner(cache1 + cache2 + cache3));
        ArrayList<Cache> cacheList = caches.select();
        assertEquals("GCRQWK", cacheList.get(0).getGcCode());
        
    }
    
    @Test
    public void testGetOwners () throws IOException
    {
        String cache1 = "GCRQWK\tA\tgeocadet1\t3.5\t3\tN40 45.850\tW111 48.045\n";
        String cache2 = "GCRQWK\tB\tgeocadet2\t3.5\t3\tN40 45.850\tW111 48.045\n";
        String cache3 = "GCRQWK\tC\tgeocadet1\t3.5\t3\tN40 45.850\tW111 48.045\n";
        CacheList caches = new CacheList(new Scanner(cache1 + cache2 + cache3));
        ArrayList<String> owners = caches.getOwners();
        assertEquals("geocadet1", owners.get(0));
        assertEquals("geocadet2", owners.get(1));
        
    }

}
