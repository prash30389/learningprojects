package ApiAbstraction;
//implementation class
public class Light implements Switch 
{

	@Override
	public boolean switchon() {
		System.out.println("light on");
		return true;
	}

	@Override
	public boolean switchoff() {
		System.out.println("light off");
		return true;
	}

}
