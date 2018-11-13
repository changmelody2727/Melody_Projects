package Files;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class FolderExplorerModelTest {
	private File f = new File ("cs test case");

	@Test
	public void numberOfFiles() 
	{		
		assertEquals(5, FolderExplorerModel.numberOfFiles(f));
		
	}
	@Test
	public void LargestFile() 
	{
		File g = f;
		assertEquals(g, FolderExplorerModel.LargestFile(f));
	}
	@Test
	public void filesWithSizes() 
	{
		assertEquals(0, FolderExplorerModel.filesWithSizes(f));
	}
	@Test
	public void modified() 
	{
		File g = f;
		assertEquals(g, FolderExplorerModel.modified(f));
	}

}
