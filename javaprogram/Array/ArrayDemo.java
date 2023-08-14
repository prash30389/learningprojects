package Array;

public class ArrayDemo 
{
	public static void main(String[] args) 
	{
	int arr[] = new int[5];
		arr[0] = 12;
		arr[1] = 2;
		arr[2]=25;
		arr[3]=30;
		arr[4]= 47;
	int oddcount =0;
	int evencount =0;
	for(int i=0; i < arr.length; i++)
		{
		int value = arr[i];
		if(value% 2==0)
				{
					System.out.println(value + " : is even");
					evencount++;
				}
				else
				{
					oddcount++;
					System.out.println(value + " : is odd");
				}
		}
	System.out.println("\nevencount = " + evencount);
	System.out.println("\noddcount = " + oddcount);
	}
}