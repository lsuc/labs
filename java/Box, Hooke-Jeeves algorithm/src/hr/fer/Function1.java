package hr.fer;

import java.util.Vector;

public class Function1 implements Function {

	@Override
	public double getValueAt(Vector<Double> T) {		
//		f1(x,y) = 10*(x^2-y)^2+(1-x)^2
		double x = T.get(0);
		double y = T.get(1);
		return 10*Math.pow((Math.pow(x, 2)-y), 2) + Math.pow((1-x), 2);
	}

}
