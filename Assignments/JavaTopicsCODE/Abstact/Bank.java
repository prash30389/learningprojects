package Abstact;

class SBI extends Bank
{
public double getroi()
	{
	System.out.println("GETTING Rate of Interest OF SBI");
	return 6.5;
	}
}
class HDFC extends Bank
{
public double getroi()
	{
	System.out.println("\nGETTING Rate of Interest OF HDFC");
	return 7.2;
	}
}
class AXIS extends Bank
{
public double getroi()
	{
	System.out.println("\nGETTING Rate of Interest OF AXIS");
	return 5.2;
	}
}
public abstract class Bank 
	{
	public abstract double getroi();
	}

