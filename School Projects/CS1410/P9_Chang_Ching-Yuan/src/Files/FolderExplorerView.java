package Files;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class FolderExplorerView extends JFrame implements ActionListener {

	private FolderExplorerModel model;

	private JLabel numberOfFiles;
	private JLabel largestFile;
	private JLabel filesWithSizes;
	private JLabel modified;

	public FolderExplorerView() {
		setTitle("FolderExplorer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 200);
		setResizable(true);

		JPanel root = new JPanel();
		root.setLayout(new FlowLayout());
		setContentPane(root);
		
		
		numberOfFiles = new JLabel("The total number of files and folders that are contained:");
		largestFile = new JLabel("The name and size in bytes of the largest file or folder:");
		filesWithSizes = new JLabel("The total number of files with sizes between 10,000 and 25,000 bytes:");
		modified = new JLabel("The name, date, and time of the most recently modified file:");

		root.add(numberOfFiles, "North");
		root.add(largestFile);
		root.add(filesWithSizes);
		root.add(modified);
		
		
		
		JButton menu = new JButton("Menu");
		menu.setFont(new Font("SansSerif", Font.BOLD, 20));
		menu.setForeground(Color.BLUE);
		menu.setBackground(Color.DARK_GRAY);
		menu.addActionListener(this);
		root.add(menu);	

	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		try
        {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File (System.getProperty("user.dir")));
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
		{
			File h = fileChooser.getSelectedFile();
			if (h == null)
			{
				h = fileChooser.getCurrentDirectory();
			}
			File selectedFile = fileChooser.getSelectedFile();
			numberOfFiles.setText("The total number of files and folders that are contained:"
					+ FolderExplorerModel.numberOfFiles(selectedFile));
			File lar = FolderExplorerModel.LargestFile(selectedFile);
			largestFile.setText("The name and size in bytes of the largest file or folder:"
					+ lar.getName() + " " + lar.length());
			filesWithSizes.setText("The total number of files with sizes between 10,000 and 25,000 bytes:"
					+ FolderExplorerModel.filesWithSizes(selectedFile));
			File modi = FolderExplorerModel.modified(selectedFile);
			modified.setText("The name, date, and time of the most recently modified file:"
					+ modi.getName() + " " +new Date( modi.lastModified()));
		}		
		}
		catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "No Files");
        }
	}

}
