class Marker
{
	final String brand = "luxar";
	int price ;
	float tipsize;
	String color;
public static void main( String args[] )
	{
	System.out.println("Hello ! world");
	Marker m = new Marker();
	m.color = "red";
	m.tipsize = 0.05f;
	m.price=20;
	System.out.println(m.color);
	System.out.println(m.price);
	System.out.println(m.tipsize);
	System.out.println(m.brand);
	System.out.println("bye ! world");
	}
}