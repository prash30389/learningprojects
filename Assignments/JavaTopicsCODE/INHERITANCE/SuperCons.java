package INHERITANCE; // sub class constructor always calls super class constructor at runtime)implicitly/explicitily)

public class SuperCons {
	String name;
	SuperCons(String name)
	{
		System.out.println("person class constructor");
		this.name = name;
	}
	public static void main(String[] args) 
	{
	Employee E = new Employee(852);
	System.out.println(E.name);
	System.out.println(E.id);
	}
}
class Employee extends SuperCons
{
	int id;
	Employee(int id)
	{	//super class must used in sub class constructor and must be in first line in sub class constructor.
	super("rohit"); // sub class is having user-defined constructor then provide super() with valid arguments. otherwise no default constructor 
	this.id= id;
	System.out.println("emp class constructor");
	}
}