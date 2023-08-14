package ExceptionHandling; // Checked Exception

public class Junk 
{
	static void Dosomething() // throw InterruptedException
	{
		DoMore();
	}

	static void DoMore() // throw InterruptedException
	{
		try 
		{
			Thread.sleep(5000);
		} 
		catch (InterruptedException e) 
		{
			
			e.printStackTrace();
		}	
	}
	public static void main(String[] args) 
	{
		System.out.println("main started");
		Dosomething();	
		System.out.println("main ended");
	}	
}
/*
 * Compiler will force to handle The Interrupted Exception as it is CHECKED EXCEPTION
 */

