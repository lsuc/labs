package hr.fer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Matrix {
	int rows;
	int cols;
	double [][] data;
	Matrix P;
	
	public Matrix (int rows, int cols){
		this.rows = rows;
		this.cols = cols;
		this.data = new double[rows][cols];
	}
	public Matrix(){
	}
	
	public void resize (int rows, int cols){ 
		this.rows = rows;
		this.cols = cols;
		this.data = new double[rows][cols];
	}
	
	public Matrix (Matrix m){
		pridruzi(m);
	}
	public void printMatrix(){ 
		System.out.println();
		for (int i = 0; i < rows; i++){ 			
			for (int j = 0; j < cols; j++){
				System.out.print(data[i][j] + "   ");
								
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void printMatrixToFile(){ 
		
		FileWriter writer = null;
		
			try {
				writer = new FileWriter("leMatrica.txt");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		PrintWriter toFile = new PrintWriter(writer); 
		for (int i = 0; i < rows; i++){ 			
			for (int j = 0; j < cols; j++){
					toFile.print(data[i][j] + "   ");
				}
				toFile.println();
		}
		toFile.flush();
	}
	
	public Matrix pridruzi (Matrix m){
		this.rows = m.rows;
		this.cols = m.cols;
		this.data = new double[rows][cols];
		for (int i = 0; i < rows; i++){ 
			for (int j = 0; j < cols; j++){
				this.data[i][j] = m.data[i][j];
			}
		}
		return this;
	}
	public boolean loadMatrixFromFile (String path){ //uèitavanje matrice iz datoteke
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
		ArrayList<String> lines = new ArrayList<String>();
		String line;
		while(true){
			try {
				line = input.readLine();
				
				if(line == null){
					break;
				}
				lines.add(line);
			} catch (IOException e) {
				return false;
			}	
		}
		if(!lines.isEmpty()){
			int rows = lines.size();
			//System.out.println("retci " + rows);
			
			
			int cols = lines.get(0).split("[\\t ]").length;
			//System.out.println("stupci " + cols);	
			
			resize(rows, cols);
			
			for (int i = 0; i < rows; i++){ 
				String[] redak = lines.get(i).split(" ");
		
				for (int j = 0; j < cols; j++){
					data[i][j] = Double.parseDouble(redak[j]);
				}
			}
			return true;
		}	
		return false;
	}
	
	public double getElementAt(int i, int j){
		return data[i][j];
	}
	public void setElementAt(int i, int j, double elem){
		data[i][j] = elem;
	}


	public Matrix plusEquals (Matrix mat){
		if(mat.rows == this.rows && mat.cols == this.cols){
			for (int i = 0; i < rows; i++){ 
				for (int j = 0; j < cols; j++){
					data[i][j] += mat.data[i][j];
				}
			}
		}
		return this;
	}
	
	public Matrix minusEquals (Matrix mat){
		if(mat.rows == this.rows && mat.cols == this.cols){
			for (int i = 0; i < rows; i++){ 
				for (int j = 0; j < cols; j++){
					data[i][j] -= mat.data[i][j];
				}
			}
		}
		return this;
	}
	
	public Matrix plus (Matrix mat){
		if(mat.rows == this.rows && mat.cols == this.cols){
			Matrix newMat = new Matrix(rows, cols);
			for (int i = 0; i < rows; i++){ 
				for (int j = 0; j < cols; j++){
					newMat.data[i][j] = this.data[i][j] + mat.data[i][j];
				}
			}
			return newMat;
		}
		System.out.println("Matrice nemaju isti broj redaka i stupaca");
		return null;
	}
	
	public Matrix minus (Matrix mat){
		if(mat.rows == this.rows && mat.cols == this.cols){
			Matrix newMat = new Matrix(rows, cols);
			for (int i = 0; i < rows; i++){ 
				for (int j = 0; j < cols; j++){
					newMat.data[i][j] = this.data[i][j] - mat.data[i][j];
				}
			}
			return newMat;
		}
		System.out.println("Matrice nemaju isti broj redaka i stupaca");
		return null;
	}
	
	public Matrix multiply (Matrix mat){
		if(this.cols == mat.rows){
			Matrix newMat = new Matrix(this.rows, mat.cols);
			for (int i = 0; i < this.rows; i++){ 
				for (int j = 0; j < mat.cols; j++){
					for (int k = 0; k < this.cols; k++){
						newMat.data[i][j] += this.data[i][k] * mat.data[k][j];
					}
				}
			}
			return newMat;
		}
		System.out.println("Matrice nemaju isti broj redaka i stupaca");
		return null;
	}
	
	public Matrix transpose (){
		Matrix newMat = new Matrix(this.cols, this.rows);
		for (int i = 0; i < this.rows; i++){ 
			for (int j = 0; j < this.cols; j++){
				newMat.data[j][i] = this.data[i][j];
			}
		}
		return newMat;
	}
	public Matrix multiply (int n){
		Matrix newMat = new Matrix(this.rows, this.cols);
		for (int i = 0; i < this.rows; i++){ 
			for (int j = 0; j < this.cols; j++){
				newMat.data[i][j] = this.data[i][j]*n;
			}
		}
		return newMat;
	} 
	
	public Matrix substituteForward (Matrix b){
//L*y = b
		
//		za j = 0 do n-1
//	    za i = j+1 do n
//	        b[i] -= A[i,j] * b[j];
		
		if(b.rows != this.rows || b.cols != 1){
			System.out.println("Dimenzije matrice b nisu dobre.");
			return null;
		}
		
		Matrix y = new Matrix(b);
		
		if(P != null){
			y = P.multiply(b);
		}
		for (int i = 0; i < this.rows - 1; i++){
			for (int j = i+1; j < this.rows; j++){
				y.setElementAt(j, 0, y.getElementAt(j, 0) - this.data[j][i] * y.getElementAt(i, 0));
			}
			
		}
		return y;
	}
	
	public Matrix substituteBackward (Matrix y){
		
//		Supstitucija unatrag (U * x = y )
//
//		za i = n do 1
//		    b[i] /= A[i,i];
//		    za j = 1 do i-1
//		        b[j] -= A[j,i] * b[i];
		
		if(y.rows != this.rows || y.cols != 1){
			System.out.println("Problem, dimenzije matrice y nisu dobre");
			return null;
		}
		Matrix x = new Matrix(y);
		for (int i = this.rows - 1; i >= 0; i--){
			if(isEqual(this.data[i][i], 0)){
				System.out.println("Nije pristojno dijeliti s nulom.");
				System.exit(-1);
			}
			x.data[i][0] = x.data[i][0]/this.data[i][i]; 
			
			for (int j = 0; j < i; j++){ //ili i-1
				x.setElementAt(j, 0, x.getElementAt(j, 0) - this.data[j][i] * x.getElementAt(i, 0));
			}			
		}
		return x;
	}
	public Matrix LUDek(){
//		Algoritam LU dekompozicije 
		//
//			za i = 1 do n-1
//			    za j = i+1 do n
//			        A[j,i] /= A[i,i];
//			        za k = i+1 do n
//			            A[j,k] -= A[j,i] * A[i,k];
		P = null;
		Matrix A = new Matrix(this);
		for (int i = 0; i < this.rows-1; i++){
			for (int j = i+1; j < this.rows; j++){
				if(isEqual(A.data[i][i], 0)){
					System.out.println("Nije pristojno dijeliti s nulom.");
					System.exit(-1);
				}
				A.data[j][i] = A.data[j][i] / A.data[i][i];
				for (int k = i+1; k < this.rows; k++){
					A.data[j][k] -= A.data[j][i] * A.data[i][k];
				}
			}
		}
		return A;
	}

	public boolean isEqual(double d1, double d2){
		if(Math.abs(d2-d1) < 0.0001){
			return true;
		}
		return false;
	}
	
	public Matrix LUPDek(){
//Algoritam LUP dekompozicije
//		za i = 1 do n
//	    P[i] = i;
//	za i = 1 do n-1
//	    pivot = i;
//	    za j = i+1 do n
//	        ako ( abs(A[P[j],i]) > abs(A[P[pivot],i) )
//	            pivot = j;
//	    zamijeni(P[i],P[pivot]);
//	    za j = i+1 do n
//	        A[P[j],i] /= A[P[i],i];
//	        za k = i+1 do n
//	            A[P[j],k] -= A[P[j],i] * A[P[i],k];
//

		Matrix A = new Matrix(this);
		A.allocIdentity();
		
		for (int i = 0; i < this.rows-1; i++){
			int indexPivota = i;
			for (int j = i+1; j < this.rows; j++){ //traži najveæi elem
				if(Math.abs(A.data[j][i]) > Math.abs(A.data[indexPivota][i])){
					indexPivota = j;
				}
			}
			A.switchRows(i, indexPivota);
			A.P.switchRows(i, indexPivota);
			
			for (int j = i+1; j < this.rows; j++){
				if(isEqual(A.data[i][i], 0)){
					System.out.println("Nije pristojno dijeliti s nulom.");
					System.exit(-1);
				}
				A.data[j][i] = A.data[j][i] / A.data[i][i];
				for (int k = i+1; k < this.rows; k++){
					A.data[j][k] -= A.data[j][i] * A.data[i][k];
				}
			}
		}
		//A.P = new Matrix(P);
		return A;
	}
	public void switchRows(int row1, int row2){
		//if() bla
		for(int i = 0; i < this.cols; i++){
			double temp = data[row1][i];
			data[row1][i] = data[row2][i];
			data[row2][i] = temp;
		}
	}
	
	public Matrix allocIdentity (){
		this.P = new Matrix(this.rows, this.rows);
		for(int i = 0; i < this.rows; i++){
			this.P.data[i][i] = 1;
		}
		return this;
	}
}
