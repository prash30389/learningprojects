package ARRAYS;

import java.util.Scanner;

public class ArrayReverseWord {
	
	public static void main(String[] args) 
	{
	Scanner s = new Scanner(System.in);
	System.out.println("Enter the String: ");
	String str = s.nextLine();
	if (str.contains(" "))
	{
	ArrayReverseWord arw = new ArrayReverseWord();
	arw.reversestring(str);
	s.close();
	}
	else
	{
		System.out.println("please enter more than one word");
	}
	}
	public void reversestring(String str) 
	{
		String[] rev = str.split(" ");
		/* The split() method of String class splits
		 * a string in several strings based on the
		 * delimiter passed as an argument to it
		 */
		String reversedString = ""; // blank String
		for(int i =0; i < rev.length ; i++  ) 
		{
			String word = rev[i];
			String reverseword ="";
			for(int j = word.length()-1; j>=0; j--)
			{
				reverseword = reverseword + word.charAt(j);
				/* The charAt() function returns the character
				 * at the given position in a string
				 */
			}
			reversedString = reversedString + reverseword + " ";
		}
		System.out.println(str);
		System.out.println(reversedString);
	}
}
