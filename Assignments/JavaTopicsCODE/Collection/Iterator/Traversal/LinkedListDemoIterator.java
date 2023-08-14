package Collection.Iterator.Traversal;

import java.util.Iterator;
import java.util.LinkedList;

public class LinkedListDemoIterator 
{
	public static void main(String[] args) 
	{
		LinkedList<String> list = new LinkedList<String>();
		list.add("ABC");
		list.add("DEF");
		list.add("GHI");
		System.out.println("linkedList: " + list);
		list.add(1, "JKL");
		list.add("mno");
		System.out.println("linkedList: " + list);
		Iterator<String> i = list.iterator();//helper method
		while (i.hasNext())
		{
			String str = (String) i.next();
			System.out.println(str);
			StringBuilder sb = new StringBuilder(str);
			System.out.println(str + "------>" + sb.reverse());
		}
	}
}
