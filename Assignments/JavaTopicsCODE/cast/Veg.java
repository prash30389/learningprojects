package cast;
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
class Tamater extends Veg
{
	void prepsauce()
	{
	System.out.println("prepared SAUCE");
	}
}
class TestCasting 	//UPCASTING AND DOWNCASTING 
{
public static void main(String[] args) 
	{
	Veg v = new Aloo(); //up casting: cast Aloo Datatype in reference variable of Veg with Veg Data type
	System.out.println(v.price);
	System.out.println(v.name);
	Aloo x = (Aloo) v ; // down casting : converting reference of super class into sub class after up casting
	x.prepdumaloo();
	//v.prepdumaloo();
	Veg v1= (Veg) new Tamater();
	System.out.println(v1.price);
	System.out.println(v1.name);
	Tamater t = (Tamater) v1;
	t.prepsauce();
	//v1.prepsauce();
	}
}