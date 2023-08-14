class A
{
	void mA()
	{
		String s = null;
		s.length();
	}
}
class B
{
	void mB()
	{
		A ref = new A();
		ref.mA();
	}	
}
class C
{
	public static void main(String[] args) 
	{
		B ref2 = new B();
		ref2.mB();
	}	
}