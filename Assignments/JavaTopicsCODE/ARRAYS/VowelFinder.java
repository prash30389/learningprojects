package ARRAYS;

import java.util.Scanner;

public class VowelFinder 
{
public static void main(String[] args) 
	{
		Scanner s = new Scanner(System.in);
		System.out.println("Enter string : ");
		String str = s.nextLine();
		s.close();
		int vocount = 0;
		for(int i =0; i < str.length(); i++)
		{
			char c= str.charAt(i);
			if(c=='a'|| c=='e' || c=='i'||c=='o'|| c=='u')
			{
				vocount++;
			}
		}
		System.out.println("no of vowels : " + vocount);
		System.out.println("no of components : " + (str.length() - vocount));
	}
}
