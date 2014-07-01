package hr.fer;

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

public class HJDataLoading {
	
	private Vector<Double> dx = new Vector<Double>();
	private Vector<Double> eps = new Vector<Double>();
	private Vector<Double> X0 = new Vector<Double>();
	private Vector<Double> p = new Vector<Double>();
	private int numFunc;

	public boolean loadHJData (String path){
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
				
				if(list.get(0).equals("dx")){
					for(int i = 1; i < list.size(); i++){
						this.getDx().add(i-1,Double.parseDouble(list.get(i)));
					}					
				}
				else if(list.get(0).equals("e")){
					for(int i = 1; i < list.size(); i++){
						this.getEps().add(i-1,Double.parseDouble(list.get(i)));
					}					
				}	
				else if(list.get(0).equals("x0")){
					for(int i = 1; i < list.size(); i++){
						this.getX0().add(i-1,Double.parseDouble(list.get(i)));
					}					
				}			
				
				else if(list.get(0).equals("p")){
					for(int i = 1; i < list.size(); i++){
						this.getP().add(i-1,Double.parseDouble(list.get(i)));
					}					
				}
				else if(list.get(0).equals("f")){
					this.setNumFunc(Integer.parseInt(list.get(1))); 										
				}
				
			} catch (IOException e) {
				return false;
			}	
		}
		//HookeJeeves HJ = new HookeJeeves(dx, eps);
		return true;
		
	}

	public void setDx(Vector<Double> dx) {
		this.dx = dx;
	}

	public Vector<Double> getDx() {
		return dx;
	}

	public void setEps(Vector<Double> eps) {
		this.eps = eps;
	}

	public Vector<Double> getEps() {
		return eps;
	}

	public void setX0(Vector<Double> x0) {
		X0 = x0;
	}

	public Vector<Double> getX0() {
		return X0;
	}

	public void setP(Vector<Double> p) {
		this.p = p;
	}

	public Vector<Double> getP() {
		return p;
	}

	public void setNumFunc(int numFunc) {
		this.numFunc = numFunc;
	}

	public int getNumFunc() {
		return numFunc;
	}
}
