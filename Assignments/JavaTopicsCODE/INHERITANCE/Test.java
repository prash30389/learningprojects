package INHERITANCE;/*ONE CLASS AQUIRING THE DATA MEMBERS AND METHODS OF ANOTHER CLASS BY USING KEYWORD extends IS CALLED INHERITANCE
It establish IS-A relationship b/w two entities. advantage is it increase CODE RE-USABILITY and code extensibility.
SUPER/PARENT/BASE CLASS : the class from which the properties and behavior are inherited.
SUB/CHILD/DERIVED CLASS : the class which inherits the properties and behaviour.	*/	

class Emp 
{
	String name;
	int id;
}
class Dev extends Emp 
{
	String techused;
	void Devapp()
	{
		System.out.println(this.name + " USES " + this.techused + " to Devapp\n" + " ID " + this.id);
	}
}
class Testengg extends Emp {
	String toolused;
	void testapp()
	{
		System.out.println(this.name + " USES " + this.toolused + " to test app\n "+ " ID " + this.id);
	}
}
class Hr extends Emp 
{
	String color;
	void makerangoli()
	{
		System.out.println(this.name + " USES " + this.color + " to make Rangoli\n"+ " ID " + this.id);
	}
}
public class Test {
	public static void main(String[] args) 
	{
		Dev d = new Dev();
		d.name = "Ayush";
		d.id = 324;
		d.techused = "Java";
		d.Devapp();
		System.out.println("XXXXXXX\n");
		Hr h = new Hr();
		h.name = "Deepak";
		h.id = 123;
		h.color = "red and blue";
		h.makerangoli();
		System.out.println("XXXXXXX\n");
		Testengg t = new Testengg();
		t.name = "Rahul";
		t.toolused = "Selenium";
		t.id = 687;
		t.testapp();
		System.out.println("XXXXXXX\n");
	}
}
