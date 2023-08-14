package ARRAYS;
import java.util.Scanner;
public class StringOps 
{
public static void main(String[] args) 
	{
		Scanner s = new Scanner(System.in);
		System.out.println(" Enter a string : ");
		String str = s.nextLine();
		StringOps so = new StringOps(); // creating object of class
		String r = so.reverse(str); // for calling reverse string method
		if (r.equals(str)) // for check palindrome condition
		{
			System.out.println("Its Palindrome");
		}
		else
		{
			System.out.println("its not palindrome");
		}
		s.close();
	}	
private String reverse(String str) {
	char[] chArr= str.toCharArray(); // convert user defined string to char
	String rev = ""; // blank String
	for(int i =chArr.length-1; i>=0; i-- ) // reverse loop of for loop
	{
		rev = rev + chArr[i]; // adding char in rev and make string reverse output
		
	}
	System.out.println(rev); // print reverse string 
	return rev; // return value of rev
}
}
