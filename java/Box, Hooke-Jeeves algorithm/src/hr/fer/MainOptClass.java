package hr.fer;

import java.util.Vector;



public class MainOptClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		HJDataLoading HJ = new HJDataLoading();
		HJ.loadHJData("C:\\AHookeJeeves.txt");
		HookeJeeves hookeJeeves = new HookeJeeves(HJ.getDx(), HJ.getEps());
		Function f = new Function4();
		
		System.out.println("Minimum pronadjen Hooke-Jeeves postupkom:");
		Vector <Double> pom = hookeJeeves.optimize(f, HJ.getX0());	
		MathHelper.printVector(pom);
		
		BoxDataLoading box = new BoxDataLoading();
		box.loadBoxData("C:\\ABox.txt");
		Box boxOpt = new Box();
		
		System.out.println("Minimum pronadjen Box postupkom:");
		pom = boxOpt.optimize(box.getX0(), f, box.getE(), box.getA());		
		if(pom != null){
			MathHelper.printVector(pom);
		}
		
	}

	


}
