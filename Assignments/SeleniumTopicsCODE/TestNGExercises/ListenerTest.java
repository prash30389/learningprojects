package TestNGExercises;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.AssertJUnit;

import org.testng.annotations.Listeners;


@Listeners(Listener.class)
public class ListenerTest 
{
	@Test
	public void login()
	{
		System.out.println("login Test");
	}

	@Test
	public void Second()
	{
		System.out.println("Second Test");
		AssertJUnit.assertTrue(true);
	}
	
	@Test
	public void Third()
	{
		System.out.println("Third Test");
		SoftAssert sa = new SoftAssert();
		sa.assertEquals("Third Test", "Third Test");
		sa.assertAll();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
