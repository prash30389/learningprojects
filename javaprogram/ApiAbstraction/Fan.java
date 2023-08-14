package ApiAbstraction;
//implementation class
public class Fan implements Switch 
{

	@Override
	public boolean switchon() {
		System.out.println("fan on");
		return true;
	}

	@Override
	public boolean switchoff() {
		System.out.println("fan off");
		return true;
	}

}
