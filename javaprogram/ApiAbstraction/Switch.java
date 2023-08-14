package ApiAbstraction;
//interface
public interface Switch 
{
String Color = "white";
boolean switchon();
boolean switchoff();
default public void testmethod()
{
	System.out.println("its a test method");
}
}
