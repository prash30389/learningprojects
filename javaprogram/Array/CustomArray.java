package Array;
import java.util.Scanner;

public class CustomArray 
	{
	public static void main(String[] args) 
	{
		Scanner s = new Scanner(System.in); // size of array enter by user using SCANNER CLASS
		System.out.println("Enter Size of ARRAY : ");
		int Size = s.nextInt();
		char[] charr = new char[Size]; // array object creation
		for ( int i = 0; i<charr.length;i++)
		{
			System.out.println("Enter number between 97 to 122");
			int ascii = s.nextInt();
			if(ascii>=97 && ascii<=122)
			{
				char c = (char) ascii;
				charr[i] = c;
			}
			else
			{
				System.out.println("Invalid Ascii");
				break; // for loop disconnection statement
			}
		}
		s.close();
		for (int i =0; i<charr.length;i++)
		{
			System.out.println(charr[i]);
		}
	}
}
