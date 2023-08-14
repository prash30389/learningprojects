package RuntimeConcept;

public class Runtime 
{
	private static Runtime r ;
	private Runtime()
	{
	
	}
	public static Runtime GetRuntime()
	{
		if(r==null)
		{
			r = new Runtime();
		}
		return r;
	}
	public void exec()
	{
		
	}
	
	}

