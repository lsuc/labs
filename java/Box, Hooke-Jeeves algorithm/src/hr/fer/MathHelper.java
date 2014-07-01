package hr.fer;

import java.util.Vector;

public class MathHelper {

	public static boolean isGreater(Vector<Double> a, Vector<Double> b){
		
		for(int i = 0; i < a.size(); i++){
			if(a.get(i) < b.get(i)){
				return false;
			}
		}
		return true;
	}
	public static Vector<Double> add(Vector<Double> a, Vector<Double> b){
		Vector<Double> c = new Vector<Double>();
		for(int i = 0; i < a.size(); i++){
			c.add(a.get(i) +  b.get(i));
		}
		return c;
	}
	public static Vector<Double> sub(Vector<Double> a, Vector<Double> b){
		return add(a, multiply(b, -1));
	}
	public static Vector<Double> multiply(Vector<Double> a, double b){
		Vector<Double> c = new Vector<Double>();
		for(int i = 0; i < a.size(); i++){
			c.add(a.get(i) * b);
		}
		return c;
	}
	public static Vector<Double> absolute(Vector<Double> a){
		Vector<Double> c = new Vector<Double>();
		for(int i = 0; i < a.size(); i++){
			c.add(Math.abs(a.get(i)));
		}
		return c;
	}
	public static void printVector(Vector<Double> v){
		
		for(double d : v){
			System.out.print(d + " ");
		}
		System.out.println();
		System.out.println();
	}
	
}
