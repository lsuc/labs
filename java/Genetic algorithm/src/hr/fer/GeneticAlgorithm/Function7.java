package hr.fer.GeneticAlgorithm;

import java.util.Vector;

public class Function7 implements Function {
	private double ll;
	private double ul;
	private int varNum;
	
	public Function7(double ll, double ul, int varNum) {
		this.ll = ll;
		this.ul = ul;
		this.varNum = varNum;
	}
	public double getValueAt(Vector<Double> T) {
		//max f7(xi) = (sumaxi^2)^0.25*...
		double sum = 0;
		
		for(int i = 0; i < T.size(); i++){
			sum += Math.pow(T.get(i), 2);
		} 
		double value = Math.pow(sum, 0.25)*(Math.pow(Math.sin(50 * Math.pow(sum, 0.1)), 2) + 1.0);
		return value;	
	}
	
	public int getVarNum(){
		return varNum;		
	}
	
	public double getLowerLimit() {
		return ll;
	}
	public double getUpperLimit() {
		return ul;
	}
}
