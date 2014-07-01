package hr.fer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

public class DataLoading {

	
	
	public Graph loadGraph (String path){
		
		BufferedReader input;
		try {
			input = new BufferedReader(
									new InputStreamReader(
										new BufferedInputStream(
											new FileInputStream(path)), "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			return null;
		} catch (FileNotFoundException e1) {
			return null;
		}
		
		Graph g = new Graph();
		String line;
		
		while(true){
			ArrayList <String> list = new ArrayList<String>();
			try {
				line = input.readLine();
				
				if(line == null){
					break;
				}
				
				list.addAll(Arrays.asList(line.split("[\\t ]")));
				
				g.connect(list.get(0), list.get(1), Integer.parseInt(list.get(2)));
				
			
			} catch (IOException e) {
				return null;
			}	
		}
		return g;
		
	}

}
