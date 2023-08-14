package POLYMORPHISM; // A class having multiple methods same name but with different parameters is called METHOD OVERLOADING

public class Calc {
	
int Add (int b, int a ) // sequence
	{
		return (a+b);
	}
int add( int a, int b, int c) // number
	{
		return (a+b+c);
	}
double add (int a , double b) // datatype
	{
		return (a+b);
	}
public static void main(String[] args) {
	Calc c = new Calc();
	System.out.println(c.add(10,  20));
	System.out.println(c.add(10,  10.5));
	System.out.println(c.add(10,  20, 30));
	//System.out.println(c.add(10,  true)); // wrong argument passes
} // two parameters with same name and same parameters are considered as Duplicates.
}
