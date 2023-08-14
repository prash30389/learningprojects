package INTERFACES;

public interface Mouse {
	String color = "black";
	boolean leftclick();
	boolean rightclick();
	void Scroll();
	default public void testmouse()
	{
		System.out.println(" Its a mouse work in Windows Platform ");
	}
	public static void main(String[] args) 
	{
		System.out.println("\n mouse in Windows \n");
		Mouse m = (Mouse)new Window();
		m.leftclick();
		m.rightclick();
		m.Scroll();
		m.testmouse();
		System.out.println("\n mouse in Linux \n");
		m = new Linux();
		m.leftclick();
		m.rightclick();
		m.Scroll();
		System.out.println("Its a mouse work in Linux Platform");
	}
}