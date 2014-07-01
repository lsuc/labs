package hr.fer.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import org.w3c.dom.ranges.Range;

public class GeneticMinimizer {
	
	private Function f;
	private ArrayList<Chromosome> population;
	private double maxValue = Double.MIN_VALUE;
	private int eliteIndex;
		
	public GeneticMinimizer(Function f, double precision, int populationSize){
		this.setF(f);
		int varSize = calcBitNum(precision); //racuna broj bitova jedne varijable
		int varNum = f.getVarNum(); //broj varijabli
		createPopulation(populationSize, varNum, varSize);
	}
	
	public Vector<Double> minimizeFun(int iterNum, double p){	
		for(int i = 0; i < iterNum; i++){
			ArrayList<Integer> list = threeTournamentSelect();
			Chromosome c = uniformCross(list);
			mutate(c, p);
			int index = list.get(2);
			this.population.set(index, c);
			calcMaxValue(index); //racuna najgoru (u ovom slucaju najvecu) vrijednost funkcije
			int temp = eliteIndex;
			calcEliteIndex(index); //racuna index jedinke koja ima najmanju vrijednost funkcije
			if (temp != eliteIndex){
				System.out.print("Iteracija " + (i+1) + ": ");
				Chromosome best = this.population.get(eliteIndex);		
				System.out.println("Novo najbolje rjesenje je " + best.getVarsScaledToInterval(f.getLowerLimit(), f.getUpperLimit())); 
				
			}
		}
		Chromosome c = this.population.get(eliteIndex);		
		return c.getVarsScaledToInterval(f.getLowerLimit(), f.getUpperLimit()); //vraæa pronaðeno najbolje rješenje
	}
	
	public int calcBitNum(double precision){ //racuna potreban broj bitova na temelju preciznosti i intervala pretrazivanja
		return (int) Math.ceil((Math.log((f.getUpperLimit()-f.getLowerLimit())*Math.pow(10,precision)+1))/Math.log(2));
	}
	
	public void createPopulation(int populationSize, int varNum, int varSize){ 
		this.population = new ArrayList<Chromosome>();
		for(int i = 0; i < populationSize; i++){	
			Chromosome c = new Chromosome(varNum, varSize);	
			this.population.add(c);
			calcMaxValue(i);
			calcEliteIndex(i);
		}
	}
	
	public void calcMaxValue(int index){
		double value = this.f.getValueAt(this.population.get(index).getVarsScaledToInterval(f.getLowerLimit(), f.getUpperLimit()));
		if(value > this.maxValue){
			this.maxValue = value;			
		}
	}
	
	public void calcEliteIndex(int index){
		double value = this.f.getValueAt(this.population.get(index).getVarsScaledToInterval(f.getLowerLimit(), f.getUpperLimit()));
		double eliteValue = this.f.getValueAt(this.population.get(this.eliteIndex).getVarsScaledToInterval(f.getLowerLimit(), f.getUpperLimit()));		
		if(value < eliteValue){
			this.eliteIndex = index;			
		}
	}

	public double getFitness(Chromosome c){
		double value = this.f.getValueAt(c.getVarsScaledToInterval(f.getLowerLimit(), f.getUpperLimit()));
		double fitness = this.maxValue - value;
		return fitness;
	}

	public ArrayList<Integer> threeTournamentSelect(){ //radi troturnirski odabir
		
		Random rg = new Random();
		if(this.population.size() < 3){
			System.out.println("Nije moguce napraviti troturnirski odabir jer je velicina populacije manja od 3");
			System.exit(1);
		}
		ArrayList<Integer> selected = new ArrayList<Integer>(3); 
 		int i = 0;
		while(i < 3){
			int index = (int) Math.round((rg.nextDouble()*(this.population.size()-1)));
			if(!selected.contains(index)){
				selected.add(index);	
				i++;
			}
		}
		
		int indexMax = 0;
		
		for(int j = 1; j < 3; j++){
			if(getFitness(this.population.get(selected.get(j))) < getFitness(this.population.get(selected.get(indexMax)))){
				indexMax = j;
			}					
		}
		int max = selected.get(indexMax);
		selected.remove(indexMax);
		selected.add(max);
		
		return selected;		
	}

	public Chromosome uniformCross(ArrayList<Integer> list){	//radi uniformno krizanje	
		
		Chromosome p1 = population.get(list.get(0));
		Chromosome p2 = population.get(list.get(1));		
		int varNum = p1.getVarsNum();
		int varSize = p2.getVarSize();
		int length = varNum * varSize;
		
		Chromosome template = new Chromosome(varNum, varSize);	
	
//		Chromosome c1 = new Chromosome(varNum, varSize);
//		Chromosome c2 = new Chromosome(varNum, varSize);
		Chromosome c = new Chromosome(varNum, varSize);	
		
		
		
		for(int i = 0; i < length; i++){
		if(p1.getData().get(i)== p2.getData().get(i)){
			c.getData().set(i, p1.getData().get(i));
		}
		else{
			c.getData().set(i, template.getData().get(i));
		}
	}
//direktno uvrštavanje, AB+R(A xor B)		
//		BitSet pom1 = new BitSet(length);
//		pom1 = (BitSet) p1.getData().clone();		
//		BitSet pom2 = new BitSet(length);
//		pom2 = (BitSet) p2.getData().clone();
//		pom1.and(pom2);		
//		BitSet pom3 = new BitSet(length);
//		pom3 = (BitSet) p1.getData().clone();
//		pom3.xor(pom2);
//		BitSet t = (BitSet) template.getData().clone();	
//		t.and(pom3);	
//		pom1.or(t);

//drugaèii oblik uniformnog križanja, c1 = A*R + (Not B)*R, c2 = B*R + (Not A)*R		
//		for(int i = 0; i < length; i++){
//			if(template.getData().get(i) == true){
//				c1.getData().set(i, p1.getData().get(i));
//				c2.getData().set(i, p2.getData().get(i));
//			}
//			else{
//				c1.getData().set(i, p2.getData().get(i));
//				c2.getData().set(i, p1.getData().get(i));
//			}
//		}		
//		int index = list.get(2);
//		if(getFitness(c1) > getFitness(c2)){
//			return c1;
//		}
//		else{
//			return c2;
//			
		return c;
	}
	
	public void setPopulation(ArrayList<Chromosome> population) {
		this.population = population;
	}

	public ArrayList<Chromosome> getPopulation() {
		return population;
	}

	public void setF(Function f) {
		this.f = f;
	}

	public Function getF() {
		return f;
	}

	public void mutate(Chromosome c, double p){		
		
		Random rg = new Random();
		int length = c.getVarSize()*c.getVarsNum();
		for(int i = 0; i < length; i++){
			if(rg.nextDouble() < p){
				c.getData().flip(i);
			}		
		}	
	}
	
}