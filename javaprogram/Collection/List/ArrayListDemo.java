package Collection.List;
import java.util.ArrayList;
public class ArrayListDemo 
	{
	ArrayList<Integer> createlist()//instance
	{
			ArrayList<Integer> a = new ArrayList<Integer>(3); // create array list with wrapper class object
			a.add(21);//method of collection<i>
			a.add(72);//method of collection<i>
			a.add(23);//method of collection<i>
			a.add(30);//method of collection<i>
			a.add(30);//method of collection<i>
			System.out.println("Element in list : " + a);
			int Size = a.size();
			System.out.println("Size:" + Size);
			a.add(2,52);// method of list<i>
			System.out.println("Element in list : " + a);
			a.remove(1); // method of list<i>
			System.out.println("Element in list : " + a);
			Integer v = a.get(2);// method of list<i> to fetch value
			System.out.println("Element @index 2 : " + v);
			a.set(4, 35);// method of list<i> to 
			System.out.println("Element in list : " + a);
			return a;
	}
	public static void main(String[] args) 
		{
			ArrayListDemo al  = new ArrayListDemo();
			ArrayList<Integer> List = al.createlist();
			System.out.println("returned list : " + List);
			ArrayList<Integer> k = new ArrayList<Integer>();
			k.addAll(List); //  method of list<i>
			k.add(63);// method of collection<I>
			k.add(85);
			System.out.println("element in List :\n" + k);
			k.removeAll(List); // to remove array list K // method of collection<I>
			System.out.println("element in List :\n" + k);
			k.clear(); // to clear (k) from  collection <I> // method of collection<I>
			System.out.println("element in List :\n" + k.isEmpty());// to check if empty or not from  collection <I>
		}
}
