package INTERFACES;

public class Window implements Mouse{
	@Override
	public boolean rightclick() 
	{
		System.out.println("right clicked");
		return false;
	}
	@Override
	public boolean leftclick() 
	{
		System.out.println("left clicked");
		return false;
	}
	@Override
	public void Scroll() {
		System.out.println("Scrolled");
	}
}
