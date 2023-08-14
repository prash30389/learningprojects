package Abstact;

public class Test 
{
	public static void main(String[] args)
	{
	Bank b = new SBI();// DE- REFFERED AFTER HDFC
	double rate = b.getroi();
	System.out.println("SBI rate:" + rate);
	b = new HDFC(); // DE- REFFERED AFTER AXIS
	rate = b.getroi();
	System.out.println("HDFC rate:" + rate);
	b= new AXIS();
	rate = b.getroi();
	System.out.println("AXIS rate : " + rate);
	}
}
