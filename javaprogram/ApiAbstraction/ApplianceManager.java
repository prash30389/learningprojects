package ApiAbstraction;
//helper class
public class ApplianceManager 
{
public Switch giveappliance(String appname)//helper method
{
	Switch s = null;
	if(appname.equalsIgnoreCase("Fan"))
	{
		s = new Fan();
	}
	else if(appname.equalsIgnoreCase("light"))
	{
		s = new Light();
	}
	return s;

}
}
