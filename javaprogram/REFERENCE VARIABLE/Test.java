class Test
{
	public static void main(String[] args) //MAIN METHOD
	{
		McD m = new McD(); 
		Burger b = m.Servefood("mcegg", 50); // CALLING METHOD WITH GIVING ARGUMENTS
		System.out.println("burger recieved:" + b.name);
		System.out.println("costing:" + b.price + "INR");
	}
}