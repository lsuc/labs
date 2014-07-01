package hr.fer;

import java.util.Vector;

public class Function3 implements Function {

	@Override
	public double getValueAt(Vector<Double> T) {
		
		double x1 = T.get(0);
		double x2 = T.get(1);
		double x3 = T.get(2);
		double x4 = T.get(3);
		double x5 = T.get(4);
		double p1 = 2;
		double p2 = 3;
		double p3 = 0;
		double p4 = 8;
		double p5 = -1;
		
		//f3(x1,x2,x3,x4,x5) = (x1-p1)2 + (x2-p2)2 + ... + (x5-p5)2 	
		
		return (Math.pow(x1 - p1, 2)+Math.pow(x2 - p2, 2) + Math.pow(x3 - p3, 2) + Math.pow(x4 - p4, 2) + Math.pow(x5 - p5, 2));
	}

}
