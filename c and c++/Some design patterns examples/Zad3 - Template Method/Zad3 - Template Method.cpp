// Zad3 - Template Method.cpp : Defines the entry point for the console application.
//

//#include "stdafx.h"
#include <iostream>

class Beverage
{
public:
	void boil_water()
	{
         std::cout << "boiling water" << endl;
	}

	virtual void brew() = 0;

	void pour_to_cup()
	{
         cout << "pouring to cup" << endl; 
	}

	virtual void addCondiment() = 0;

	void prepare()
	{
		boil_water();
		brew();
		pour_to_cup();
		addCondiment();
	}

	// ako želimo omoguæiti da korisnik prilikom pripreme piæa može definirati koliko šeæera želi dodati, to je najlakše definiranjem nove verzije metode prepare i dodavanjem parametra funkciji addCondiment
	void prepare(int gramsOfSugar)
	{
		boil_water();
		brew();
		pour_to_cup();
		addCondiment();
	}

	virtual void addCondiment(int gramsOfSugar) = 0;
};

class Tea : public Beverage
{
	void brew()
	{
         cout << "putting tea bag"<< endl;
	}
	void addCondiment()
	{
         cout << "adding lemon"<< endl;
	}
	void addCondiment(int gramsOfSugar)
	{
	}
};

class Coffee : public Beverage
{
	void brew()
	{
         cout << "mixin coffee" << endl;
	}
	void addCondiment()
	{
         cout << "adding sugar milk" << endl;
	}
	void addCondiment(int gramsOfSugar)
	{
	}
};

int _tmain(int argc, _TCHAR* argv[])
{
	Tea cupOfTea;
	Coffee cupOfCoffee;

	cupOfTea.prepare();

	cupOfCoffee.prepare();

	return 0;
}

