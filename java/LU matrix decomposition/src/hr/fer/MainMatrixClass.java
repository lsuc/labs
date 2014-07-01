package hr.fer;

public class MainMatrixClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Matrix A = new Matrix();
		A.loadMatrixFromFile("C:\\mat.txt");
		Matrix b = new Matrix();
		b.loadMatrixFromFile("C:\\b.txt");
		System.out.println("Matrica A: ");
		A.printMatrix();
		System.out.println("Vektor b: ");
		b.printMatrix();
		
	
		System.out.println("LUP matrica: ");
		Matrix LUP = A.LUPDek();
		LUP.printMatrix();
		System.out.println("LUP, forward substitution, y: ");
		Matrix d = LUP.substituteForward(b);
		d.printMatrix();
		System.out.println("LUP, backward substitution, x: ");
		Matrix e = LUP.substituteBackward(d);
		e.printMatrix();
		
		System.out.println("LU matrica: ");
		Matrix LU = A.LUDek();
		LU.printMatrix();		
		System.out.println("LU, forward substitution, y: ");
		Matrix a = LU.substituteForward(b);
		a.printMatrix();
		System.out.println("LU, backward substitution, x: ");
		Matrix c = LU.substituteBackward(a);
		c.printMatrix();
		
		
	}

}
