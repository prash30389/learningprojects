package ClassDatatype;

public class Vehicle {
	static String modelname;
	String modeltype;
	Vehicle()
	{
		modelname = "car";
		this.modelname= modelname; 
		System.out.println("this is \n" + modelname);
	}
	Vehicle(String Modelname)
	{
		modelname = "bus";
		this.modelname= modelname;
		System.out.println("this is \n" + modelname);
	}
	static Vehicle Hyundai()
	{
		modelname = "grandi10";
		System.out.println("this is hyundai\n"+ modelname);
		int modelnumber = 123456;
		System.out.println(modelnumber);
		return null;
	}
	Vehicle TATA()
	{
		modelname = "tata taigo";
		System.out.println("this is Tiago\n" + modelname);
		int modelnumber = 123456;
		System.out.println(modelnumber);
		return null ;
	}
}
class Test
{
	public static void main(String[] args) 
	{
	Vehicle c = new Vehicle();
	Vehicle.Hyundai();
	c.TATA();
	
	}
}
