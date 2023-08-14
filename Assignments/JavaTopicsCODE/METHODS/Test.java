package METHODS;

public class Test 
{
	
	public static void main(String[] args) 
	{
		Calc c = new Calc();
		int d = c.add(20, 40);
		System.out.println(d);
		double percent = c.percentage(d);
		System.out.println("your percent" + percent);
	}
}
