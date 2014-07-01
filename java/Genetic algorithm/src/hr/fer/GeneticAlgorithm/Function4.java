package hr.fer.GeneticAlgorithm;

import java.util.Vector;

public class Function4 implements Function {
	private double ll;
	private double ul;
	
	public Function4(double ll, double ul) {
		this.ll = ll;
		this.ul = ul;
	}
	public double getValueAt(Vector<Double> T) {
		//f4(x,y) = |(x-y)*(x+y)| + (x^2+y^2)0.5
		double x = T.get(0);
		double y = T.get(1);
		return (Math.abs((x-y) * (x+y)) + Math.pow(x*x + y*y, 0.5));
	}
	public int getVarNum(){
		return 2;		
	}
	
	public double getLowerLimit() {
		return ll;
	}
	public double getUpperLimit() {
		return ul;
	}
}
