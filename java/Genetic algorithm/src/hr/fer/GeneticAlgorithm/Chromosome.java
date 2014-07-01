package hr.fer.GeneticAlgorithm;

import java.util.BitSet;
import java.util.Random;
import java.util.Vector;

public class Chromosome {
	private BitSet data;
	private int varNum;
	private int varSize;
	
	public Chromosome(int varNum, int varSize){
		this.varNum = varNum;
		this.varSize = varSize;
		
		int length = varNum * varSize; //ukupna duljina kromosoma jednaka je broj bitova pojedine varijable * broj varijabli
		this.data = new BitSet(length);
		Random rg = new Random();  
		for(int i = 0; i < length; i++){
			if(rg.nextDouble() >= 0.5){ //nasumièan odabir 0 i 1 
				this.data.set(i);
			}
		}	
	}
	
	public void printChromosome(){
	int length = varNum * varSize;
	for(int i = 0; i < length; i++){
		if(this.data.get(i) == true){
			System.out.print(1);
		}
		else{
			System.out.print(0);
		}
	}
	System.out.println();
	}
	
	public int getVarValue(int n){		
		int index = n * this.varSize;
		BitSet var = this.data.get(index, index + this.varSize);
			
		int varValue = 0;
		
		for(int i = 0; i < this.varSize; i++){
			if(var.get(i)){
				varValue += Math.pow(2, i);
			}
		}
		return varValue;		
	}
	
	public Vector<Double> getVarValues(){ 
		Vector<Double> v = new Vector<Double>();
		
		for(int i = 0; i < this.varNum; i++){
			int var = getVarValue(i);
			v.add((double) var);
		}
		return v;		
	}
	
//dekodiranje- proces pretvaranja binarnog broja u realan (u potencijalno rješenje),u granicama dg,gg
	public Vector<Double> getVarsScaledToInterval(double dg, double gg){ 
		Vector<Double> vars = getVarValues();
		for(int i = 0; i < vars.size(); i++){

			double val = dg + vars.get(i)/(Math.pow(2, this.varSize) - 1)*(gg-dg);
			vars.set(i, val);
		}		
		return vars;	
	}
	
	public BitSet getData() {
		return data;
	}
	
	public int getVarsNum() {
		return varNum;
	}
	
	public int getVarSize() {
		return varSize;
	}

}