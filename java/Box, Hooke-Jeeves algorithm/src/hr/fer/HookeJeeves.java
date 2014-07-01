package hr.fer;

import java.util.Vector;

public class HookeJeeves {
	
	private Vector<Double> dx = new Vector<Double>();
	private Vector<Double> eps = new Vector<Double>();
	
	HookeJeeves(Vector<Double> dx, Vector<Double> eps){
		this.setDx(dx);
		this.setEps(eps);
	}
	public Vector<Double> optimize (Function f, Vector<Double> X0){
		Vector<Double> Xp = new Vector<Double>();
		Vector<Double> Xb = new Vector<Double>();
		Vector<Double> Xn = new Vector<Double>();
		Xp.addAll(X0);
		Xb.addAll(X0);
		
		while(MathHelper.isGreater(this.getDx(), this.getEps())){ //dok nije ispunjen uvjet zaustavljanja, tj. dok je Dx > e
			Xn = explore(Xp, this.getDx(), f);
			if(f.getValueAt(Xn) < f.getValueAt(Xb)){ // prihvaæamo baznu toèku
				Xp.clear();
				Xp.addAll(MathHelper.sub(MathHelper.multiply(Xn, 2), Xb));//xP = 2xN – xB; 
				Xb.clear();
				Xb.addAll(Xn);
			}
			else {				
				this.setDx(MathHelper.multiply(this.getDx(), 0.5));
				Xp.clear();
				Xp.addAll(Xb);
			}
		}
		
		return Xb;		
	}
	

//	x0 - pocetna tocka
//	xB - bazna tocka 
//	xP - pocetna tocka pretrazivanja
//	xN - tocka dobivena pretrazivanjem
//
//	postavi x0;
//	xP = xB = x0;
//	ponavljaj
//	{  xN = istraži(xP, Dx);   // definiran je potprogram
//	   ako F(xN)<F(xB)    // prihvaæamo baznu toèku
//	   {  xP = 2xN – xB;  // definiramo novu tocku pretrazivanja
//	      xB = xN;
//	   }
//	   inaèe
//	   {  smanji Dx;
//	      xP = xB;        // vracamo se na zadnju baznu tocku
//	   }
//	}
//	dok nije zadovoljen uvjet zaustavljanja
//	rješenje = xB;

	public Vector<Double> explore (Vector <Double> Xp, Vector<Double> Dx, Function f){
		Vector<Double> X = new Vector<Double>();
		X.addAll(Xp); 	//x = xP;
		
		for (int i = 0; i < X.size();  i++){
			double P = f.getValueAt(X);	//P = F(x);
			X.setElementAt(X.get(i) + Dx.get(i), i); //xi = xi + Dxi;   
			double N = f.getValueAt(X); //N = F(x);
			if(N > P){	// ne valja pozitivni pomak
				X.setElementAt(X.get(i) - 2*Dx.get(i), i); //xi = xi - 2Dxi;
				N = f.getValueAt(X);
				if(N > P){	// ne valja ni negativni
					X.setElementAt(X.get(i) + Dx.get(i), i);	 // vratimo na staro
				}
			}
		}		
		return X;
	}
	
//	procedura istraži(xP, Dx)
//	{  x = xP;
//	   za i=1 do n
//	   {  P = F(x);
//	      xi = xi + Dxi;       // povecamo za Dx
//	      N = F(x);
//	      ako N>P             // ne valja pozitivni pomak
//	      {  xi = xi - 2Dxi;  // smanjimo za Dx
//	         N = F(x);
//	         ako N>P          // ne valja ni negativni
//	           xi = xi + Dx;  // vratimo na staro
//	      }
//	   }
//	   vrati x;
//	}
	


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


	

//	Hooke-Jeeves postupak
//	Potrebno je omoguæiti da se bez prevoðenja programa mogu definirati: vektor pomaka dx, 
//	vektor graniène preciznosti e te poèetna toèka pretraživanja 
//	(sva tri npr. uèitavanjem iz odreðenih datoteka). 
//	Pretpostavljene vrijednosti: 0.5 za sve elemente dx te 10-9 za sve elemente e.
//	Postupak primijeniti na funkcije f1, f2, f3, f4
	

}
