package hr.fer;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;


public class Box {
//	postupak po Boxu za probleme s ograničenjima
//	Potrebno je omogućiti da se bez prevođenja programa mogu definirati: preciznost e , 
//	koeficijent refleksije a te početna točka pretraživanja (npr. učitavanjem iz određenih datoteka).
//	Pretpostavljene vrijednosti:e = 10-9, a = 1.3
//	Postupak primijeniti na funkcije f2 i f3 uz implicitna ograničenja: 
//	(x1-x2 <= 0), (x1-2 <= 0) i eksplicitna ograničenja prema kojima su sve varijable u 
//	intervalu [-100, 100].  
	private final double XD = -100;
	private final double XG = 100;
	
	
	public Vector<Double> optimize(Vector<Double> X0, Function f, Vector<Double> e, double a){
		if(isExplicitOK(X0) && isImplicitOK(X0)){ //x1-x2 <= 0, x1-2 <= 0
			
			Vector<Double> Xc = new Vector<Double>();
			Xc.addAll(X0);
			Random generator = new Random();
			ArrayList<Vector<Double>> X = new ArrayList<Vector<Double>>();
			int n = X0.size();
			
			for(int t = 1; t <= 2*n; t++){	// generiranje skupa 2n tocaka
				Vector<Double> T = new Vector<Double>();
				
				for(int i = 0; i < n; i++){
					double R = generator.nextDouble();
					T.add(XD + R*(XG - XD));
				}
				while(!isImplicitOK(T)){
					T = MathHelper.multiply(MathHelper.add(T, Xc), 0.5); // pomakni prema centroidu prihvacenih tocaka
				}
				X.add(T);
				
				Xc = getCentroid(X);
			}
			int xhIndex = getBadPoints(X, f).get(0), xh2Index;
			Vector <Double> Xh = X.get(xhIndex), Xh2;
			
			while(!isStopTime(Xh, Xc, e)){
				xhIndex = getBadPoints(X, f).get(0);
				xh2Index = getBadPoints(X, f).get(1);
				Xh = X.get(xhIndex);
				Xh2 = X.get(xh2Index);
				
				Xc = getCentroid(X, xhIndex);
				
				Vector<Double> Xr = MathHelper.add(MathHelper.multiply(Xc, 1+a), MathHelper.multiply(Xh, -a));

				for(int i = 0; i < n; i++) {
					if (Xr.get(i) < XD) {
						Xr.set(i, XD);
					} else if (Xr.get(i) > XG) {
						Xr.set(i, XG);
					}
				}
				
				while(!isImplicitOK(Xr)) {
					Xr = MathHelper.multiply(MathHelper.add(Xr, Xc), 0.5);
				}
				
				if(f.getValueAt(Xr) > f.getValueAt(Xh2)) { //ako je to i dalje najlošija točka
					Xr = MathHelper.multiply(MathHelper.add(Xr, Xc), 0.5);
				}
				
				X.set(xhIndex, Xr);
			}
			
			return Xc;
		}

		System.out.println("Pocetna tocka ne zadovoljava ogranicenja!");
		return null;		
	}
//	provjeri da li za X0 vrijedi (Xd <= X0 <= Xg) te (g(X0) >= 0)
//	Xc = X0;
//	za t = 1 do 2*n // generiranje skupa 2n tocaka
//	{ za i = 1 do n
//	      R = random[0,1];
//	      Xi[t] = Xdi + R*(Xgi - Xdi); // nova tocka je unutar eksplicitnih ogr.
//	  dok postoji gi(X) za koji gi(X[t])<0 // nisu zadovoljena implicitna ogr.
//	      X[t] = ½ * (X[t] + Xc); // pomakni prema centroidu prihvacenih tocaka
//	  izracunaj novi Xc (sa novom prihvacenom tockom);
//	}
//	ponavljaj
//	{ odredi indekse h, h2 : F(X[h]) = max, F(X[h2]) = drugi najlosiji;
//	  izracunaj Xc (bez X[h]);
//	  Xr = (1+a)*Xc - a*X[h]; // refleksija
//	  za i = 1 do n
//	  {   ako Xri < Xdi  // pomicemo na granicu ekspl. ogranicenja
//	            Xri = Xdi;
//	      inace ako Xri > Xgi
//	            Xri = Xgi;
//	  }
//	  dok postoji gi(X) za koji gi(Xr)<0  // provjeravamo implicitna ogr.
//	        Xr = ½ * (Xr + Xc);
//	  ako F(Xr) > F(X[h2])  // ako je to i dalje najlosija tocka
//	        Xr = ½ * (Xr + Xc);  // jos jednom pomakni prema Xc
//	  X[h] = Xr;
//	} dok nije zadovoljen uvjet zaustavljanja;

	public Vector<Double> getCentroid(ArrayList<Vector<Double>> X){
		Vector<Double> centroid = new Vector<Double>();		
		for(int i = 0; i < X.get(0).size(); i++){
			double a = 0;
			for(int j = 0; j < X.size(); j++){
				a += X.get(j).get(i);
			}
			a /= X.size();
			centroid.add(a);
		}
		return centroid;
	}
	
	public Vector<Double> getCentroid(ArrayList<Vector<Double>> X, int indexOfWorse) {
		Vector<Double> centroid = new Vector<Double>();		
		for(int i = 0; i < X.get(0).size(); i++){
			
			double a = 0;
			for(int j = 0; j < X.size(); j++){
				if (j == indexOfWorse) {
					continue;
				}
				
				a += X.get(j).get(i);
			}
			a /= (X.size() - 1);
			centroid.add(a);
		}
		return centroid;
	}
	
	public boolean isExplicitOK(Vector<Double> X){
		for(double x : X){
			if(x < XD || x > XG){
				return false;
			}
		}
		return true;
	}
	
	public boolean isImplicitOK(Vector<Double> X){
		if(X.get(0) - X.get(1)<= 0 && (X.get(0)-2 <= 0)){
			return true;
		}
		return false;
	}
	
	public boolean isStopTime(Vector<Double> Xh, Vector<Double> Xc, Vector<Double> e){	
		return !MathHelper.isGreater(MathHelper.absolute(MathHelper.sub(Xh, Xc)), e);
	}
	
	public ArrayList<Integer> getBadPoints(ArrayList<Vector<Double>> X, Function f){
		
		int h = 0, h2 = X.size()-1;
		
		for (int i = 0; i < X.size(); i++) {
			if (f.getValueAt(X.get(i)) > f.getValueAt(X.get(h))) {
				h2 = h;
				h = i;
			} else if (f.getValueAt(X.get(i)) > f.getValueAt(X.get(h))) {
				h2 = 1;
			}
		}
		
		ArrayList<Integer> ret = new ArrayList<Integer>();
		ret.add(h);
		ret.add(h2);
		return ret;
	}
}
