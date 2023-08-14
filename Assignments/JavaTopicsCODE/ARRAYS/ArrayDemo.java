package ARRAYS;

public class ArrayDemo 
{
	public static void main(String[] args)
	{
		int[] arr = new int[5];
		arr[0] = 12;
		arr[1] = 24;
		arr[2]=23;
		arr[3]=30;
		arr[4]=47;
		int evencount = 0;
		int oddcount = 0;
		for (int i = 0; i<=arr.length; i++)
		{
		int value = arr[i];
		if (value % 2== 0)
		{
			System.out.println(value + ": is even");
			evencount++;
		}
		else
		{
			oddcount++;
		System.out.println(value + ": is odd");	
		}
		}
		System.out.println("evencount" + evencount);
		System.out.println("oddcount" + oddcount);
	}
}
