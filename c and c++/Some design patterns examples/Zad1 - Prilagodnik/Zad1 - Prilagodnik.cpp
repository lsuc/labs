// Zad1 - Prilagodnik.cpp : Defines the entry point for the console application.
//

//#include "stdafx.h"

#include <string>
#include <vector>
#include<iostream>

using std::string;
using std::vector;
using std::cout;
using std::endl;

class Rectangle
{
};

class Shape
{
  std::string id_;
  Rectangle rect_;

public:
	Shape(std::string inID) : id_(inID) {}

	const std::string& id() const { return id_; }
	const Rectangle& rect() const { return rect_; }

public:
  virtual void rotate(double angle) =0;
};

class Drawing
{
public:
  virtual void addShape(const Shape* pShape)=0;
  virtual void removeShape(const std::string& id)=0;
  virtual const Shape& getShape(int i)=0;
  virtual int nShapes()=0;
};

class DrawingImpl : public Drawing
{
public:
  void addShape(const Shape* pShape)  {	  _shapes.push_back(pShape);  }

  void removeShape(const std::string& id)
  {
	  for(int i=0; i<_shapes.size(); i++ )
		  if( _shapes[i]->id() == id )
			_shapes.erase(_shapes.begin() + i);
  }

  const Shape& getShape(int i) { return *_shapes[i];  }

  int nShapes()  { return _shapes.size();  }

private:
	vector<const Shape *> _shapes;
};

// ova klasa nam treba za testiranje
class TestShape : public Shape
{
public:
  TestShape(string inID) : Shape(inID) {}

  virtual void rotate(double angle)
  {
  }
};

int main(int argc, char* argv[])
{
	TestShape a("a"), b("b"), c("c");

	DrawingImpl drawing;

	cout << "Adding shape" << endl;
	drawing.addShape(&a);
	cout << "Number of shapes - " << drawing.nShapes() << endl;

	cout << "Adding two more shapes" << endl;
	drawing.addShape(&b);
	drawing.addShape(&c);
	cout << "Number of shapes - " << drawing.nShapes() << endl;

	cout << "Deleting shape" << endl;
	drawing.removeShape("b");
	cout << "Number of shapes - " << drawing.nShapes() << endl;
    system("PAUSE");
	return 0;
}

