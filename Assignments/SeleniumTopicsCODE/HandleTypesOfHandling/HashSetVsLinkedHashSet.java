package HandleTypesOfHandling;

import java.util.*;

public class HashSetVsLinkedHashSet {

	public static void main(String[] args) 
	{
HashSet<String> HS = new HashSet<String>();
	HS.add("XYZ");
	HS.add("AAA");
	HS.add("ABC");
		System.out.println(HS);
		System.out.println(HS.size());
	for(String value:HS)
	{
		System.out.println(value);
	}
	
	
	LinkedHashSet<String> LHS = new LinkedHashSet<String>();
	LHS.add("XYZ");
	LHS.add("AAA");
	LHS.add("ABC");
		System.out.println(LHS);
		System.out.println(LHS.size());
	Iterator<String> ITR = LHS.iterator();
	while(ITR.hasNext())
	{
		String value2= ITR.next();
		System.out.println(value2);
	}

	}

}
