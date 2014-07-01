package hr.fer;

import java.util.Vector;

public class Function2 implements Function {

	@Override
	public double getValueAt(Vector<Double> T) {
		//f2(x,y) = (x-4)^2 + 4(y-2)^2
		double x = T.get(0);
		double y = T.get(1);
		return Math.pow(x-4, 2) + 4*Math.pow(y-2, 2);
	}

}
