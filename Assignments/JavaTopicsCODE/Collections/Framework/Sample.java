package Collections.Framework; /* IT EXIST IN FORM OF OBJECT SO IT HAS DECLARED AS A CLASS.
								EVERY collection is GROWABLE, HETEROGENOUS AND HAS PRE-DEFINED METHODS */

interface Sample 
	{
	int show(double d);
	}

interface Mydummy
	{
		void play();
	}
interface Dummy extends Sample // one interface can acquire abstract method of another interface by inheritance using extends Keyword
{
boolean display(String x);	
}
class Junk implements Dummy,Mydummy // one class can implements more than one Interface
{

	@Override
	public int show(double d) //abstract method came from sample interface which is implementing here
	{
		return 10;
	}

	@Override
	public void play() // abstract method came from Mydummy interface which is implementing here
	{
		//blank                                                                                                                           
		
	}

	@Override
	public boolean display(String x) //abstract method came from Dummy interface which is implementing here
	{
		return true;
	}
	
}