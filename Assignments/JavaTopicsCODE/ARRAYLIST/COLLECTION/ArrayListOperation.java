package ARRAYLIST.COLLECTION;

import java.util.ArrayList;
import java.util.Iterator;

public class ArrayListOperation 
{
public static void main(String[] args) 
	{
	ArrayList<Integer> a = new ArrayList<Integer>();
	a.add(1);
	a.add(2);
	a.add(3);
	a.add(4);
	a.add(5);
	Object J[] =a.toArray();
	// HOw to get element one by one from ARRAYLIST
	
	for (int i=0; i < a.size(); i++)				//BY FOR LOOP
	{
		System.out.println(a.get(i));
	}
	
	
	Iterator<Integer> I = a.iterator();
	while (I.hasNext())								//BY WHILE LOOP WITH ITERATOR
	{
		Integer i = (Integer) I.next();
		System.out.println(i);
	}
	
	for(Object K : J)								//BY FOR EACH LOOP
		{
			System.out.println(K);
		}
	
	}
}
