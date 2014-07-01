package hr.fer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainClass {

	public static void main(String[] args) {
		
		AVLTree tree = new AVLTree(); 
		
		if (args.length == 1){
			System.out.println("Ucitavanje iz datoteke: " + args[0]);
			
			Scanner dat = null;
			try {
				dat = new Scanner(new File(args[0]));
			} catch (FileNotFoundException e) {
				System.out.println("Ne moze se otvoriti datoteka.");
			}
			while (dat.hasNext()){
				tree.insert(dat.nextInt());
			}			
			tree.printAVLTree(tree.getRootElement());
		}

		System.out.println("Unesite 'u' za umetanje u stablo, 'b' za brisanje cvora iz stabla ili 'q' za izlaz.");
		Scanner in = new Scanner(System.in);
		String input = in.next();
		while(true){		
			if (input.equals("u")){
				input = in.next();
				while (isNumber(input)){
					int value = Integer.parseInt(input);
					tree.insert(value);
					tree.printAVLTree(tree.getRootElement());
					
					input = in.next();
				}
				continue;
			}
			if (input.equals("b")){
				input = in.next();
				while (isNumber(input)){
					int value = Integer.parseInt(input);
					tree.delete(value);
					tree.printAVLTree(tree.getRootElement());
					
					input = in.next();
				}
				continue;
			}
			if (input.equals("q")) {
				break;
			}
			
			input = in.next();	
			
		}
	}
	
	public static boolean isNumber(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}		
		return true;
	}
}
