class Child	
	{
		int x = 10;//instance variable
	void fun()
	{//instance method
		int y = 20;//local variable to fun()
			System.out.println(x);
			System.out.println(y);
	}
	void show()
	{
		int y = 30;//local variable to show()
			System.out.println(x);
			//System.out.println(y);
	}
	public static void main(String[] args) 
	{
		Child c =new Child();
		System.out.println(x);
		//System.out.println(y);
			}		
}