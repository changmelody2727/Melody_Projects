package Files;

import java.io.File;

import javax.swing.JFileChooser;

public class FolderExplorerModel 
{
	/**
	 * The total number of files and folders that are contained 
	 * (directly or indirectly) within the selected folder.
	 */
	public static int numberOfFiles(File f)
	{
		if (f.isFile())
		{
			return 1;
		}
		else 
		{
			int total = 0;
			for (File file: f.listFiles())
			{
				total += numberOfFiles(file);
			}
			return total+1;

		}
				
	}
	
	/**
	*The name and size in bytes of the largest file 
	*or folder that is contained (directly or indirectly) within the selected folder.	 
	*/
	public static File LargestFile (File f)
	{
		if (f.isFile())
		{
			return f;
		}
		else 
		{
			File largest = f;
			for (File file: f.listFiles())
			{
				File g = LargestFile(file);
				if(g.length()>file.length())
				{
					largest = g;
				}
			}
			return largest;

		}
	}
	
	/**The total number of files with sizes between 10,000 
	 * and 25,000 bytes (inclusive) that are contained (directly or indirectly) 
	 * within the selected folder.
	 */
	public static int filesWithSizes (File f)
	{
		if (f.isFile())
		{
			if (f.length()>= 10000 && f.length() <= 25000)
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}
		else
		{
			int count = 0;
			for (File file: f.listFiles())
			{
				count += filesWithSizes(file);
			}
			return count;
		}
	}
	
	/**The name of the file or folder contained 
	 * (directly or indirectly) within the selected folder 
	 * that was most recently modified, along with its date and 
	 * time of modification.  (For displaying a date and time, 
	 * you'll find the java.util.Date class useful.)
	 */
	public static File modified (File f)
	{
		if(f.isFile())
		{
			return f;
		}
		else
		{
			File mostRecent = f;
			for (File file: f.listFiles())
			{
				File g = modified(file);
				if(g.lastModified()>file.lastModified())
				{
					mostRecent = g;
				}
			}
			return mostRecent;
		}
	}
}
