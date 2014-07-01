package hr.fer.GeneticAlgorithm;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class GenDataLoading {
	
	
	private double mutationProb; //vjerojatnost mutacije
	private int populationSize; //broj clanova populacije
	private int iterNum; //broj iteracija
	private double precision; //preciznost
	private double upperLimit; //gornja granica intervala pretrazivanja
	private double lowerLimit; //donja granica intervala pretrazivanja	
	private Function fun;
	private Vector<Double> f3 = new Vector<Double>();
	
	
	public boolean loadGenData (String path){
		BufferedReader input;
		try {
			input = new BufferedReader(
									new InputStreamReader(
										new BufferedInputStream(
											new FileInputStream(path)), "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			return false;
		} catch (FileNotFoundException e1) {
			return false;
		}
		
		String line;
		
		while(true){
			ArrayList <String> list = new ArrayList<String>();
			try {
				line = input.readLine();
				
				if(line == null){
					break;
				}
				
				list.addAll(Arrays.asList(line.split("[\\t ]")));
				
				

				if(list.get(0).equals("n")){
					this.populationSize = Integer.parseInt(list.get(1)); 										
				}
				else if(list.get(0).equals("m")){
					this.mutationProb = Double.parseDouble(list.get(1)); 										
				}
				else if(list.get(0).equals("i")){
					this.iterNum = Integer.parseInt(list.get(1)); 										
				}
				else if(list.get(0).equals("p")){
					this.precision = Double.parseDouble(list.get(1)); 										
				}
				else if(list.get(0).equals("u")){
					this.upperLimit = Double.parseDouble(list.get(1)); 										
				}
				else if(list.get(0).equals("l")){
					this.lowerLimit = Double.parseDouble(list.get(1)); 										
				}
				else if(list.get(0).equals("f")){
					
					
					if(list.get(1).equals("3")){
						for(int i = 2; i < list.size(); i++){
							this.f3.add(i-2,Double.parseDouble(list.get(i)));
						}	
						this.fun = new Function3(this.lowerLimit, this.upperLimit, this.f3);
					}
					else if(list.get(1).equals("4")){
						this.fun = new Function4(this.lowerLimit, this.upperLimit);
					}
					else if(list.get(1).equals("6")){
						int varNum = Integer.parseInt(list.get(2));
						this.fun = new Function6(this.lowerLimit, this.upperLimit, varNum);
					}
					else if(list.get(1).equals("7")){
						int varNum = Integer.parseInt(list.get(2));
						this.fun = new Function7(this.lowerLimit, this.upperLimit, varNum);
					}
								
			}
			} catch (IOException e) {
				return false;
			}	
		}
		return true;
		
	}

	public int getPopulationSize() {		
		return this.populationSize;
	}

	public int getIterNum() {
		return this.iterNum;
	}

	public double getMutationProb() {
		return this.mutationProb;
	}

	
	public double getPrecision() {
		return precision;
	}


	public double getUpperLimit() {
		return upperLimit;
	}


	public double getLowerLimit() {
		return lowerLimit;
	}

	public Function getFun() {
		return fun;
	}
	
	
}
