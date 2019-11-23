package Map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoadFile {
	
	private ArrayList<String> fileText;
	private File file;
	private BufferedReader br;
	
	/**
	 * 
	 * @param path for .txt file
	 */
	//loads file and puts all text line by line into an array
	public LoadFile(String path) {
		fileText = new ArrayList<String>();
		file = new File(path);
		
		
		br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(file));
			try {
				while ((line = br.readLine()) != null){//reads a line at a time
					fileText.add(line);
				}
				//System.out.println(file + " loaded");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getFileText(){
		return fileText;
	}	
}