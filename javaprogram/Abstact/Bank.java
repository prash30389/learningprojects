package Abstact;

class SBI extends Bank
{
public double getroi()
	{
	System.out.println("GETTING ROI OF SBI");
	return 6.5;
	}
}
class HDFC extends Bank
{
public double getroi()
	{
	System.out.println("\nGETTING ROI OF HDFC");
	return 7.2;
	}
}
class AXIS extends Bank
{
public double getroi()
	{
	System.out.println("\nGETTING ROI OF AXIS");
	return 5.2;
	}
}
public abstract class Bank 
	{
	public abstract double getroi();
	}

