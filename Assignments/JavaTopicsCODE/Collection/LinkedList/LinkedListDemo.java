package Collection.LinkedList;
import java.util.LinkedList;
public class LinkedListDemo 
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
			list.remove(3);
			System.out.println("linkedList: " + list);
		}
	}
