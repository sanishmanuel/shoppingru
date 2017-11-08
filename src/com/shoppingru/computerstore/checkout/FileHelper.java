/**
 * 
 */
package com.shoppingru.computerstore.checkout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Sanish
 *
 */
public class FileHelper {

	public static List<String> loadDetailsfromFile(String resourceName) {
		
		List<String> lines = new ArrayList<String>();
		try {
			 InputStream input = FileHelper.class.getResourceAsStream(resourceName);
			 BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			 String line;
			  while ((line = reader.readLine())!=null){
				  lines.add(line);
			  }
			//lines = Files.readAllLines(Paths.get(resourceName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lines;
	}
	
}
