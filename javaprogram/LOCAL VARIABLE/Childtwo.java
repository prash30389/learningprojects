class Childtwo
{
	 int x = 10;//same name of instance variable
	void fun()
	{
	int x = 20;//same name of instance variable
	System.out.println(x);
System.out.println(this.x); //It behave as an reference variable and refers to cuurent live object (the object whose method we have called)
	}
	public static void main(String[] args)
	{
		Childtwo c = new Childtwo();
				c.fun(); // calling line
		Childtwo c1 = new Childtwo();
				c1.x = 30; // when local and instance variable having same identifier
				c1.fun(); // calling line
	}
}