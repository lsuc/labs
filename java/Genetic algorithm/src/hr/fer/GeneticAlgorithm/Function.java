package hr.fer.GeneticAlgorithm;

import java.util.Vector;

public interface Function {
	public double getValueAt(Vector<Double> T);
	public int getVarNum();
	public double getLowerLimit();
	public double getUpperLimit();
}
