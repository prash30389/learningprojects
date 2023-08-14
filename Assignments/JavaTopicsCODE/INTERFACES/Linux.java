package INTERFACES;

public class Linux implements Mouse
{

	@Override
	public boolean leftclick() {
		System.out.println("left clicked : ");
		return false;
	}

	@Override
	public boolean rightclick() {
		System.out.println("right clicked :");
		return false;
	}

	@Override
	public void Scroll() {
		System.out.println("Scrolled Down/UP");
		
	}

}
