package TYPECASTING; // upcasting : CONVERTING ONE DATATYPE TO ANOTHER DATATYPE IS CALLED TYPE CASTING
//WE CANNOT PERFORM CASTING IN SIBLING CLASSES OF SUB CLASSES.JVM THROWS CLASSCASTEXCEPTION : ALOO A= NEW TAMATOR()

class Veg
	{
	String name;
	int price;
	}
class Aloo extends Veg
{
void prepdumaloo()
	{
	System.out.println("prepared DUM ALOO");
	}
}
class tamater extends Veg
{
void prepsauce()
	{
	System.out.println("prepared SAUCE");
	}
}

public class TestCasting 	//UPCASTING AND DOWNCASTING 
{
public static void main(String[] args) 
	{
	Veg v = new Aloo(); //up casting: cast Aloo Datatype in reference variable of Veg with Veg Data type
	System.out.println(v.price);
	System.out.println(v.name);
	Aloo x = (Aloo) v ; // down casting : converting reference of super class into sub class after up casting
	x.prepdumaloo();
	//v.prepdumaloo();
	Veg v1= (Veg) new tamater();
	System.out.println(v1.price);
	System.out.println(v1.name);
	tamater t = (tamater) v1;
	t.prepsauce();
	//v1.prepsauce();
	}
}
