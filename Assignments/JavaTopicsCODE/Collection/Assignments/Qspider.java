package Collection.Assignments;

import java.util.ArrayList;
import java.util.Iterator;

public class Qspider 
{
	Iterator<String> Candidates()
	{
	ArrayList<String> al = new ArrayList<String>(5);
	al.add("Aditya");
	al.add("Aditi");
	al.add("Aditya raj");
	al.add("Aditya dhanraj");
	al.add("Aditya dhanraj Asharfi");
	return null;
	}
}
class Hr extends Qspider
{
	Iterator<String> absentCandidates()
	{
		ArrayList<String> ab = new ArrayList<String>(2);
		ab.add("Aditya");
		ab.add("Aditi");
		return null;
	}
}
class test
{
	public static void main(String[] args) 
	{
	Qspider qs = new Qspider();
	Iterator<String> list = qs.Candidates();
	System.out.println("list: " + list);
	Hr h = new Hr();
	Iterator<String> List2 = h.absentCandidates();
	System.out.println("absent candidate: " + List2);
	}
}
