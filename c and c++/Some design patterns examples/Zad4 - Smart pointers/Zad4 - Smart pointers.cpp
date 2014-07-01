// Zad4 - Smart pointers.cpp : Defines the entry point for the console application.
//

//#include "stdafx.h"

#include <vector>
#include <set>
#include <iostream>
#include <algorithm>

#include <boost/scoped_ptr.hpp>
#include <boost/shared_ptr.hpp>

/**********************************************************************************************************************************/
struct TestObject 
{ 
	~TestObject() { std::cout << "TestObject destructor\n"; } 
};

class MyClassTestScopedPtr
{
    boost::scoped_ptr<int> ptr;

public:
    MyClassTestScopedPtr() : ptr(new int) 
	{ 
		*ptr = 0; 
	}
    int add_one() { return ++*ptr; }
};

// OVO NE MOZE!!!! - jer je scoped_ptr NON-COPYABLE
void func1(boost::scoped_ptr<TestObject> inPtr)
{
}
// ovo æe proæi
void func2(boost::scoped_ptr<TestObject> &inPtr)
{
}

void TestScopedPtr()
{
    boost::scoped_ptr<TestObject> x(new TestObject);

//	func1(x);
	func2(x);

	MyClassTestScopedPtr my_instance;
    
	std::cout << my_instance.add_one() << '\n';
    std::cout << my_instance.add_one() << '\n';
}

/**********************************************************************************************************************************/

struct MyClassShared
{ 
  MyClassShared( int _x ) : x(_x) {}
  ~MyClassShared() { std::cout << "Destructing a Foo with x=" << x << "\n"; }
  int x;
  /* ... */
};

typedef boost::shared_ptr<MyClassShared> MyClassSharedPtr;

struct MyClassSharedOps
{
  bool operator()( const MyClassSharedPtr & a, const MyClassSharedPtr & b )
    { return a->x > b->x; }
  void operator()( const MyClassSharedPtr & a )
    { std::cout << a->x << "\n"; }
};

void TestShared()
{
  std::vector<MyClassSharedPtr>         foo_vector;
  std::set<MyClassSharedPtr,MyClassSharedOps>  foo_set; // NOT multiset!

  MyClassSharedPtr foo_ptr( new MyClassShared( 2 ) );
  foo_vector.push_back( foo_ptr );
  foo_set.insert( foo_ptr );

  foo_ptr.reset( new MyClassShared( 1 ) );
  foo_vector.push_back( foo_ptr );
  foo_set.insert( foo_ptr );

  foo_ptr.reset( new MyClassShared( 3 ) );
  foo_vector.push_back( foo_ptr );
  foo_set.insert( foo_ptr );

  foo_ptr.reset ( new MyClassShared( 2 ) );
  foo_vector.push_back( foo_ptr );
  foo_set.insert( foo_ptr );

  std::cout << "foo_vector:\n";
  std::for_each( foo_vector.begin(), foo_vector.end(), MyClassSharedOps() );
  
  std::cout << "\nfoo_set:\n"; 
  std::for_each( foo_set.begin(), foo_set.end(), MyClassSharedOps() );
  std::cout << "\n";

}
int main()
{
	TestScopedPtr();

	TestShared();

	std::cout << "Program end" << std::endl;
}
