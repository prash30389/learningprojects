package IDM;

class Theature
{
	final String theaturename = "INOX";
	int price;
	int seatno;
	float seatwidth;
	char row;
	String moviename;
public static void main(String[] args) 
	{
	System.out.println("hi! please book your first ticket details");
	
	Theature t = new Theature();
	t.price=380;
	t.moviename="john wick 3";
	t.seatno=10;
	t.row= 'A';
	t.seatwidth = 12.5f;
	System.out.println(t.theaturename);
	System.out.println(t.moviename);
	System.out.println(t.row);
	System.out.println(t.seatno);
	System.out.println(t.price);
	System.out.println(t.seatwidth);
	System.out.println("thank you ! enjoy the movie");
	
	Theature t1 = new Theature();
	System.out.println("please check your second ticket details also");
	t1.price=480;
	t1.moviename="bharat";
	t1.seatno=11;
	t1.row= 'X';
	t.seatwidth = 12.5f;
	System.out.println(t1.theaturename);
	System.out.println(t1.moviename);
	System.out.println(t1.row);
	System.out.println(t1.seatno);
	System.out.println(t1.price);
	System.out.println(t1.seatwidth);
	System.out.println("thank you ! enjoy the movie");
	}
}