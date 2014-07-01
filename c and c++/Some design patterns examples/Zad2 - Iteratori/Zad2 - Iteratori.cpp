// Zad2 - Iterator.cpp : Defines the entry point for the console application.
//

//#include "stdafx.h"

#include <string>
#include <vector>
#include <iostream>
#include <map>

using std::string;
using std::vector;
using std::cout;
using std::endl;
using std::map;

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

class ShapeIterator
{
public:
	virtual void first() = 0;
	virtual void next() = 0;
	virtual const Shape& currentItem() = 0;
	virtual bool isDone() = 0;
};

class Drawing
{
public:
  virtual void addShape(const Shape* pShape)=0;
  virtual void removeShape(const std::string& id)=0;
  virtual int  nShapes()=0;
  virtual ShapeIterator* getIter() = 0;
};

class DrawingVectorImpl;

class ShapeVectorIterator : public ShapeIterator
{
public:
	ShapeVectorIterator(vector<const Shape *>& inColl) : _refColl(inColl) {}

	void first()
	{
		_currInd = 0;
	}
	void next()
	{
		_currInd++;
	}
	const Shape& currentItem()
	{
		return *_refColl[_currInd];
	}
	bool isDone()
	{
		if( _currInd < _refColl.size())
			return false;
		else
			return true;
	}

private:
	int _currInd;
	//vector<const Shape *>::iterator iter;
	vector<const Shape *> _refColl;
};

class DrawingVectorImpl : public Drawing
{
public:
  void addShape(const Shape* pShape)  {	  _shapes.push_back(pShape);  }

  void removeShape(const std::string& id)
  {
	  for(int i=0; i<_shapes.size(); i++ )
		  if( _shapes[i]->id() == id )
			_shapes.erase(_shapes.begin() + i);
  }

  int nShapes()  { return _shapes.size();  }

   ShapeIterator* getIter()
   {
	   ShapeVectorIterator* newIter = new ShapeVectorIterator(_shapes);
	   return newIter;
   }

   //friend ShapeVectorIterator;

private:
	vector<const Shape *> _shapes;
};

class DrawingMapImpl;

class ShapeMapIterator : public ShapeIterator
{
public:
	ShapeMapIterator(map<string, const Shape *> inColl) : _refColl(inColl) {}

	void first()
	{
		iter = _refColl.begin();
	}
	void next()
	{
		++iter;
	}
	const Shape& currentItem()
	{
		return *iter->second;
	}
	bool isDone()
	{
		if( iter != _refColl.end() )
			return false;
		else
			return true;
	}
private:
	map<string, const Shape *>::iterator iter; //jer je u vektoru dobro i po indexima iæi samo, al map može bit stablo
	map<string, const Shape *> _refColl;
};
class DrawingMapImpl : public Drawing
{
public:
  void addShape(const Shape* pShape)  
  {
	  _shapes[pShape->id()] = pShape; //pointeri na shapeove key id shapea
  }

  void removeShape(const std::string& id)
  {
	  // TODO - ubaciti kod za izbacivanje elementa iz mape
  }

  int nShapes()  { return _shapes.size();  }

   ShapeIterator* getIter()
   {
	   ShapeMapIterator* newIter = new ShapeMapIterator(_shapes);
	   return newIter;
   }

private:
	map<string, const Shape *> _shapes;
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

void printShapes(Drawing &drawing)
{
	ShapeIterator* iter = drawing.getIter();

	iter->first();
	while( !iter->isDone() )
	{
		cout << iter->currentItem().id() << endl;
		iter->next();
	}

	delete iter;
}

int main(int argc, char* argv[])
{
	TestShape a("a"), b("b"), c("c");

	DrawingVectorImpl	drawingVec;
	DrawingMapImpl		drawingMap;

	drawingVec.addShape(&a);
	drawingVec.addShape(&b);
	drawingVec.addShape(&c);

	drawingMap.addShape(&a);
	drawingMap.addShape(&b);
	drawingMap.addShape(&c);

	cout << "Printing vector impl" << endl;
	printShapes(drawingVec);

	cout << "Printing map impl" << endl;
	printShapes(drawingMap);

    system("PAUSE");
	return 0;
}

