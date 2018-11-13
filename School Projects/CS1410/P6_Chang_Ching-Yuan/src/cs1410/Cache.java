package cs1410;

/**
 * Represents a variety of information about a geocache. A geocache has a title, an owner, a difficulty rating, a
 * terrain rating, a GC code, a latitude, and a longitude.
 */
public class Cache
{
    private String GcCode;
    private String Title;
    private String Owner;
    private double Difficulty;
    private double Terrain;
    private String Longitude;
    private String Latitude;

    /**
     * Creates a Cache from a string that consists of these seven cache attributes: the GC code, the title, the owner,
     * the difficulty rating, the terrain rating, the latitude, and the longitude, in that order, separated by single
     * TAB ('\t') characters.
     * 
     * If any of the following problems are present, throws an IllegalArgumentException:
     * <ul>
     * <li>Fewer than seven attributes</li>
     * <li>More than seven attributes</li>
     * <li>A GC code that is anything other than "GC" followed by one or more upper-case letters and/or digits</li>
     * <li>A difficulty or terrain rating that parses to anything other than the doubles 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5,
     * or 5.</li>
     * <li>A title, owner, latitude, or longitude that consists only of white space</li>
     */
    public Cache (String attributes)
    {
     // Fewer than seven attributes or more than seven attributes
        if (attributes.split("\t").length != 7)
        {
            throw new IllegalArgumentException();
        }
        
        String[] elements = attributes.split("\t");
        GcCode = elements[0];
        Title = elements[1];
        Owner = elements[2];
        Difficulty = Double.parseDouble(elements[3]);
        Terrain = Double.parseDouble(elements[4]);
        Latitude = elements[5];
        Longitude = elements[6];
       
        

        // A GC code that is anything other than "GC" followed by one or more upper-case letters and/or digits
        if (GcCode.charAt(0) != 'G' || GcCode.charAt(1) != 'C')
        {
            throw new IllegalArgumentException();
        }
        

        // A difficulty rating that parses to anything other than the doubles 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, or 5.
        if (Difficulty != 1 && Difficulty != 1.5 && Difficulty != 2 && Difficulty != 2.5 && Difficulty != 3
                && Difficulty != 3.5 && Difficulty != 4 && Difficulty != 4.5 && Difficulty != 5)
        {
            throw new IllegalArgumentException();
        }

        // A terrain rating that parses to anything other than the doubles 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, or 5.
        if (Terrain != 1 && Terrain != 1.5 && Terrain != 2 && Terrain != 2.5 && Terrain != 3 && Terrain != 3.5
                && Terrain != 4 && Terrain != 4.5 && Terrain != 5)
        {
            throw new IllegalArgumentException();
        }

        // A title, owner, latitude, or longitude that consists only of white space.
        if (Title.trim().length() == 0 || getOwner().trim().length() == 0 || getLatitude().trim().length() == 0
                || getLongitude().trim().length() == 0)
        {
            throw new IllegalArgumentException();
        }
        
      
    }

    /**
     * Converts this cache to a string
     */
    public String toString ()
    {
        return getTitle() + " by " + getOwner();
    }

    /**
     * Returns the owner of this cache
     */
    public String getOwner ()
    {
        return Owner;

    }

    /**
     * Returns the title of this cache
     */
    public String getTitle ()
    {
        return Title;
    }

    /**
     * Returns the difficulty rating of this cache
     */
    public double getDifficulty ()
    {
        return Difficulty;
    }

    /**
     * Returns the terrain rating of this cache
     */
    public double getTerrain ()
    {
        return Terrain;
    }

    /**
     * Returns the GC code of this cache
     */
    public String getGcCode ()
    {
        return GcCode;
    }

    /**
     * Returns the latitude of this cache
     */
    public String getLatitude ()
    {
        return Latitude;
    }

    /**
     * Returns the longitude of this cache
     */
    public String getLongitude ()
    {
        return Longitude;
    }
}
