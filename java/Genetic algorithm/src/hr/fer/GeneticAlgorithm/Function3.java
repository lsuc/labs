package hr.fer.GeneticAlgorithm;

import java.util.Vector;

public class Function3 implements Function {
	private double ll;
	private double ul;
	private Vector<Double> f3 = new Vector<Double>();
	
	public Function3(double ll, double ul, Vector<Double> f) {
		this.ll = ll;
		this.ul = ul;
		this.f3 = f;
	}
	
	public double getValueAt(Vector<Double> T) {
		//f3(x1,x2,x3,x4,x5) = (x1-p1)2 + (x2-p2)2 + ... + (x5-p5)2 
		double value = 0;
		for(int i = 0; i < T.size(); i++){
			value += Math.pow(T.get(i) - f3.get(i), 2);
		} 
		return value;	
	}
	
	public int getVarNum(){
		return f3.size();		
	}
	
	public double getLowerLimit() {
		return ll;
	}
	public double getUpperLimit() {
		return ul;
	}

	public void setF3(Vector<Double> f3) {
		this.f3 = f3;
	}

	public Vector<Double> getF3() {
		return f3;
	}

}
