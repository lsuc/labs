// Zad5 - Composite.cpp : Defines the entry point for the console application.
//

//#include "stdafx.h"

#include <string>
#include <sstream>
#include <iostream>
#include <vector>

using std::string;
using std::istringstream;


class Component {
	virtual int Operation() = 0;
}

class Leaf : Component {

      int vrijednost;
      
public: 
        Leaf (int value){
		value = value;
	    }
	
	   int Operation() {
		   cout <<"operacija: " << value << endl;
		   return value;
	    }


class Composite : Component {
	std::vector <Component> djeca;
	
public: 
        void addComponent(Component component){
		     djeca.push_back(component);
	      }
	
        int Operation() {
	       int zbroj = 0;
		   for(Component c: djeca){
               zbroj += c.Operation();
		       }
           return zbroj;
	       }

}

	
public static void main(String[] args) {
		
		String izraz = "(2+1)+(1+2+3)";
		Composite c = new Composite();
		
		parse (c, izraz);
		cout << c.Operation();
}




void parseExpression(char *inExpr, Composite &expr)
{
	int i=0;
	while( inExpr[i] != '\n' )
	{
		char c = inExpr[i];
		if( isdigit(c) )
		{
            expr->addComponent(new Leaf(a));
		}
		else if (c == '('){
				int open = 1;
				int j = i+1;
				for (; j < strlen(inExpr); j++){
					if (inExpr[j] == '(') open++;
					else if (inExpr[j] == ')'){
						open--;
						if (open == 0) break;
					}
				}
				Composite d = new Composite();
				parseExpression(izraz.substring(i+1, j), d);
				c.addComponent(d);
			
	}
}

int main(int argc, char* argv[])
{
	char expr[100];

	std::cout << "Enter expression:";
	std::cin.getline(expr, '\n', 100);


	return 0;
}

