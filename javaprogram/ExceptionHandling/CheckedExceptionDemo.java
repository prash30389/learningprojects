package ExceptionHandling;

public class CheckedExceptionDemo 
{
public static void main(String[] args) 
	{
		System.out.println("Main Started");
		//Thread.sleep(10000);
		System.out.println("Main Ended");
	}
}

 /* System.out.println("Main Started");
		try
		{
			Thread.sleep(10000);
		}
		catch(InterruptedException IO)
		{
			System.out.println("Exception occurred and handled");
		}
		System.out.println("Main Ended");
*/
class UncheckedException
{
	public static void main(String[] args) 
	{
		System.out.println("Main Started");
		String Str = null;
		try{
			Str.length(); // RUNTIME EXCEPTION
			}
		catch(NullPointerException e)
		{
			System.out.println("Exception handled");
		}
		System.out.println("Main Ended");
	}
}
//Cannot be handled at compile time , use try catch to handle it on runtime