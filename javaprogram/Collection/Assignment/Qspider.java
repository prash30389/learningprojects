package Collection.Assignment;

import java.util.*;

public class Qspider 
{
	ArrayList<String> Candidates() 
	{
	ArrayList<String> AL = new ArrayList<String>();
	AL.add("Aditya");
	AL.add("Rahul");
	AL.add("Rakesh");
	AL.add("Rohit");
	AL.add("Ajay");
	System.out.println("All Candidates: " + AL);
	return AL;
	}
}
class Hr
{
	String [] arr = {"Aditya","Rohit"};
	void Tally()
	{
		Qspider qs = new Qspider();
		ArrayList<String> arrl = qs.Candidates();
		for(String x: arr)
		{
			if(arrl.contains(x))
			{
				arrl.remove(x);
			}
		}
		System.out.println("List after Absent Candidate removed:" +arrl);
	}
	public static void main(String[] args) 
	{
	Hr h = new Hr();
	h.Tally();
	}
}