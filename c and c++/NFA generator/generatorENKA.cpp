#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <map>

using namespace std;

int brojac = 0;
typedef struct _automat
{

	map<int,int> stanje;
	map<pair<int,char>, pair<int,int> > prijelazi;
    
	int pocetno;
	int prihvatljivo;

} automat;

void ispis(automat X);

automat epsilon (void){
        automat X;
        char c;
        map<int,int>::iterator it1;
       	map<pair<int,char>, pair<int,int> >::iterator it2;
       	
        X.stanje[brojac++]=0; 
        X.stanje[brojac++]=1; 
     
        X.prijelazi[pair<int,char>(brojac-2,'E')] = pair <int, int> (brojac-1, brojac-1);
        X.pocetno = brojac-2;
        X.prihvatljivo = brojac-1;
        cout << "Podautomat za ulaz epsilon (E):" << endl<< endl;
        c = getchar();
        ispis(X);
        
        return X;
        }
        
automat znak (char a){
        automat X;
        char c;
        X.stanje[brojac++] = 0;
        X.stanje[brojac++] = 1; 
       
        X.prijelazi[pair<int,char>(brojac-2,a)] = pair <int, int> (brojac-1, brojac-1);
        X.pocetno = brojac-2;
        X.prihvatljivo = brojac-1;
        
        cout << "Podautomat za ulaz " << a << ":" << endl << endl;
        c = getchar();
        ispis(X);
        
        return X;
        }
              
automat zbroj (automat Y, automat Z){ 
        automat X;
        char c;
        map<int,int>::iterator it1;
       	map<pair<int,char>, pair<int,int> >::iterator it2;    
        
        for (it1 = Y.stanje.begin(); it1 != Y.stanje.end(); it1++)
            X.stanje[(*it1).first] = 0; 
            
        for (it1 = Z.stanje.begin(); it1 != Z.stanje.end(); it1++)
            X.stanje[(*it1).first] = 0; 
            
        X.stanje[brojac++] = 0;    
        X.stanje[brojac++] = 1; 
        
        for (it2 = Y.prijelazi.begin(); it2!=Y.prijelazi.end(); it2++)   
            X.prijelazi[pair<int,char>((*it2).first.first, (*it2).first.second)] = pair <int, int> ((*it2).second.first,(*it2).second.second);
        for (it2 = Z.prijelazi.begin(); it2!=Z.prijelazi.end(); it2++)
            X.prijelazi[pair<int,char>((*it2).first.first, (*it2).first.second)] = pair <int, int> ((*it2).second.first,(*it2).second.second);
            
        X.prijelazi[pair<int,char>(brojac-2, 'E')] = pair <int, int> (Y.pocetno, Z.pocetno);
        X.prijelazi[pair<int,char>(Y.prihvatljivo, 'E')] = pair <int, int >(brojac-1, brojac-1);
        X.prijelazi[pair<int,char>(Z.prihvatljivo, 'E')] = pair <int, int> (brojac-1, brojac-1);
            
        X.pocetno = brojac-2;
        X.prihvatljivo = brojac-1;
        
        cout << "Podautomat za zbroj:" << endl << endl;
        c=getchar();
        ispis(X);
        
        return X;
        }
          
automat konkaten (automat Y, automat Z){
        automat X;
        char c;
        map<int,int>::iterator it1;
        map<pair<int,char>, pair<int,int> >::iterator it2;
       
        for (it1 = Y.stanje.begin(); it1 != Y.stanje.end(); it1++)
            X.stanje[(*it1).first]= 0;
        for (it1 = Z.stanje.begin(); it1 != --Z.stanje.end(); it1++)
            X.stanje[(*it1).first]= 0;
            
            X.stanje[(*it1).first] = 1;
        
        for (it2 = Y.prijelazi.begin(); it2 != Y.prijelazi.end(); it2++)
            X.prijelazi[pair<int,char>((*it2).first.first, (*it2).first.second)] = pair <int, int> ((*it2).second.first,(*it2).second.second);
        for (it2 = Z.prijelazi.begin(); it2 != Z.prijelazi.end(); it2++)
            X.prijelazi[pair<int,char>((*it2).first.first, (*it2).first.second)] = (*it2).second;
            
        X.prijelazi[pair<int,char>(Y.prihvatljivo, 'E')] = pair<int,int>(Z.pocetno,Z.pocetno);
            
        X.pocetno = Y.pocetno;
        X.prihvatljivo = Z.prihvatljivo;
        
        cout << "Podautomat za konkatenaciju:" << endl << endl;
        c=getchar();
        ispis(X);
        
        return X;
        }
       
automat klin (automat Y){
        automat X;
        char c;
        map<int,int>::iterator it1;
        map<pair<int,char>, pair<int,int> >::iterator it2;
        
        X.stanje[brojac++]=0;
        for (it1 = Y.stanje.begin(); it1 != Y.stanje.end(); it1++)
            X.stanje[(*it1).first]=0;
        
        X.stanje[brojac++]=1;

        for (it2 = Y.prijelazi.begin(); it2 != Y.prijelazi.end(); it2++)
            X.prijelazi[pair<int,char>((*it2).first.first, (*it2).first.second)] = pair <int, int> ((*it2).second.first,(*it2).second.second);
        
        X.prijelazi[pair<int,char>(brojac-2, 'E')] = pair <int, int> (Y.pocetno, brojac-1);
        
        X.prijelazi[pair<int,char>(Y.prihvatljivo, 'E')] = pair <int, int> (Y.pocetno, brojac-1); 
    
        X.pocetno = brojac-2;
        X.prihvatljivo = brojac-1;
        
        cout << "Podautomat za Kleeneov operator:" << endl << endl;
        c=getchar();
        ispis(X);
        
        return X;
        }       
        
automat potp (string linija) {
        string r1, r2;
        automat A1, A2;
        int i = 0, j = 0, br = 0, br2 = 0, br3 = 0;
        
        if (linija.size() == 1){
                   if (linija[0] == 'E') return epsilon();
                   else return znak(linija[0]);}
        else {
             while ((j == 0) && (i < linija.size() )) {
				 if (linija[i] == '(') {
					 br ++;
					 br2++;
				 }
				 if (linija[i] == ')') {
					 br --;
					 br3++;
				 }
                   if ((linija[i] == '+') && (br == 0)) j = 1;
                   i++;
                   }

			if (linija[0]=='(' && linija[linija.size()-1]==')' && br2==1 && br3==1) {
				r1 = linija.substr (1, linija.size()-2);
				A1=potp(r1);
				return A1;
			}
             
             if (j == 1) {
                   r1 = linija.substr( 0, i-1 );
                   r2 = linija.substr (i, linija.size()); 
                   A1 = potp (r1);
                   A2 = potp (r2);
                   return zbroj (A1, A2);    
             }
                          
             br = 0; i = 0; j = 0;
             while ((j == 0) && (i<linija.size())) {     
                   if (linija[i] == '(') br ++;       
                   if (linija[i] == ')') br --;
                   if (br == 0) j = 1;
                   i++;
             }      
             
             if (j == 1) { 
                  
                   while (linija [i] == '*' ) 
                   i = i+1; 
                    
                   r1 = linija.substr( 0, i );
                   if (linija.size() == r1.size()) {
                      if ((linija[0] == '(')&& (linija[linija.size()-1] == ')')){ 
                         r1 = r1.substr (1, r1.size()-2) ;
                         A1 = potp (r1);
                         }
                         
                      else{
                                 r1 = r1.substr (0, i-1); 
                                 A1 = potp (r1); 
                                 return klin(A1); 
                      }            
                   }
                                 
                   else {
                        r2 = linija.substr (i , linija.size()); 
                                      
                   A1 = potp (r1);
                   A2 = potp (r2);
                   return konkaten (A1, A2);
                   }    
             
             }
       }
   }
   
 void ispis (automat X) {        
   
    map<int,int>::iterator it1;
    map<pair<int,char>, pair<int,int> >::iterator it2;
	    
    cout<< " Stanje  |" << "  Prihvatljivost" << endl << endl;
       
    for (it1 = X.stanje.begin(); it1 != X.stanje.end() ; it1++)
        printf ("   Q%-3d  |        %d\n\n",(*it1).first,(*it1).second);
       
    cout << "Pocetno stanje:  Q" << X.pocetno << endl << endl << endl;
    cout << "Prijelazi:" <<endl << endl;
       
     
    cout << "Trenutno stanje |" << "   Ulazni znak   |" << " Konacno stanje" << endl;
       
       
    for (it2 = X.prijelazi.begin(); it2 != X.prijelazi.end(); it2++ ){
        printf("      Q%-3d      |        %c        |       Q%d",(*it2).first.first, (*it2).first.second, (*it2).second.first);
        if ((*it2).second.first != (*it2).second.second)
        cout <<", Q" << (*it2).second.second << endl;
        else cout << endl;
        }  
     cout << endl   << endl << endl;
}

int main()
{
	map<int,int>::iterator it1;
    map<pair<int,char>, pair<int,int> >::iterator it2;
    ifstream datU;
    automat X;
	string linija;
	char c;

	datU.open("UlazG.txt");
	
	datU.clear();
	datU.seekg(0, ios::beg);

    datU >> linija;
 
	getline(datU, linija); 
	if (linija.size() == 0){ 
         
           cout << " Stanje " << "Prihvatljivost" << endl << endl;
           cout << "   Q0   " << "      0"  << endl;
           cout << "   Q1   " << "      1"  << endl << endl;
           cout << " Nema funkcija prijelaza!" <<endl;
        } 
	else {
             X = potp (linija);
             cout << endl << endl;
             cout << "Konacni automat:" << endl<<endl;
             c=getchar();
             ispis (X);
      }
           
  datU.close();          
             
  c = getchar();
  return 0;
  }
