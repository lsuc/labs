package hr.fer.GeneticAlgorithm;

import java.util.Vector;

public class Function6 implements Function {
	private double ll;
	private double ul;
	private int varNum;
	
	public Function6(double ll, double ul, int varNum) {
		this.ll = ll;
		this.ul = ul;
		this.varNum = varNum;
	}
	public double getValueAt(Vector<Double> T) {
		//max f6(xi) = 0.5-((sin^korijen(sumaxi^2) - 0.5)/(1.0+0.001*sumaXi^2)^2)
		double sum = 0;
		
		for(int i = 0; i < T.size(); i++){
			sum += Math.pow(T.get(i), 2);
		} 
		double value = -0.5 + (Math.pow(Math.sin(Math.sqrt(sum)), 2)-0.5)/Math.pow((1.0 + 0.001*sum), 2);
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
