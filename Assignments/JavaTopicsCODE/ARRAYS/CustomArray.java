package ARRAYS;

import java.util.Scanner;

public class CustomArray 
{
	public static void main(String[] args) 
	{
		int sumeven=0;
		int sumodd=0;
		int evencount = 0;
		int oddcount = 0;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter size of Array : \n");
		int Size = sc.nextInt();
		int arr[] = new int[Size];
		System.out.println("enter the value of array: \n");
		for  (int i = 0; i<Size; i++)
		{
			arr[i] = sc.nextInt();
		}
		for (int i = 0; i<Size; i++)
		{
			
			if (arr[i] % 2== 0)
			{
				System.out.println(arr[i] + ": is even \n");
				evencount++;
				sumeven = sumeven + arr[i];
			}
			else
			{
				oddcount++;
			System.out.println(arr[i] + ": is odd \n");	
				sumodd = sumodd + arr[i];
			}
		}
		sc.close();
		System.out.println("evencount" + evencount);
		System.out.println("oddcount" + oddcount);
		System.out.println("sum of even numbers" + sumeven);
		System.out.println("sum of odd numbers " + sumodd);

	}
}
