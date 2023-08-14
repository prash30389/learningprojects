package INTERFACES;

public abstract class Ankur implements Prashant 
{
	public void play()
	{
	System.out.println("ankur friends prashant");	
	}
}
class Vikas extends Ankur
	{
public void play()
	{
	System.out.println("ankur implements vikas");
	}
public static void main(String[] args) 
	{
		Ankur A = new Vikas();
		A.play();
		
	}

	}



