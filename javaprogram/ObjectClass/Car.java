package ObjectClass;

public class Car {
	String modelname;
	int engcap;
	int seatcap;
	public Car(String modelname, int engcap, int seatcap)
	{
		super();//call constructor of object class
		this.modelname = modelname;
		this.engcap = engcap;
		this.seatcap = seatcap;
	}
	@Override
	public String toString()//Pre-defined instance methods of object class
	{
		return "model:" + modelname + "\n engcap : " + engcap + "\n seatcap : " + seatcap;// return type as you changed
	}
	@Override
	public int hashCode()//pre-defined instance methods of object class
	{
		return 10;//  return type as you changed
	}
	public static void main(String[] args) 
	{
	Object c = new Car("baleno", 1200,5);
	//System.out.println(c.modelname);
	String str = c.toString(); 
	System.out.println("value returned by To string() : " + str);// externally call to string()
	System.out.println(c); //another way of call to string internally
	
	
	//HASH CODE EXAMPLE
	int hs = c.hashCode(); // Pre defined instance methods of object class
	System.out.println(hs);
	
	Car c1= new Car("figo", 1400, 4);
	int hs1 = c1.hashCode(); // Pre defined instance methods of object class
	System.out.println(hs1);
	
	
	
	//Examples of other pre defined classess
	String S = "jspiders"; //Pre defined sub class of object class
	System.out.println(S);
	
	Thread t = new Thread();// Pre defined sub class of object class
	System.out.println(t);
	}

}
