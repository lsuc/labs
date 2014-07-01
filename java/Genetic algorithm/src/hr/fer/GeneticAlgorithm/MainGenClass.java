package hr.fer.GeneticAlgorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

public class MainGenClass {

	//private final static double lowerLimit = -100;
	//private final static double upperLimit = 100;
	//private final static double precision = 2;
	//private final static int populationSize = 100;
	//private final static int iterationNum = 10000;
	//private final static double mutationProb = 0.01;
	
	public static void main(String[] args) {
		
		
		GenDataLoading data = new GenDataLoading();
		data.loadGenData("C:\\apr3.txt");
		
		Function f = data.getFun();
			
		GeneticMinimizer GA = new GeneticMinimizer(f, data.getPrecision(), data.getPopulationSize());
		
		Vector<Double> v = GA.minimizeFun(data.getIterNum(), data.getMutationProb());
		
		System.out.println("Pronadjeno najbolje rjesenje je "+v);
		
	}

}
