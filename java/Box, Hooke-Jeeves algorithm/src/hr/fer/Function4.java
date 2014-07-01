package hr.fer;

import java.util.Vector;

public class Function4 implements Function {

	@Override
	public double getValueAt(Vector<Double> T) {
		//f4(x,y) = |(x-y)*(x+y)| + (x^2+y^2)0.5
		double x = T.get(0);
		double y = T.get(1);
		return (Math.abs((x-y) * (x+y)) + Math.pow(x*x + y*y, 0.5));
	}

}
