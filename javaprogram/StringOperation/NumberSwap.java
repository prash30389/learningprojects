package StringOperation;
import java.util.*;
public class NumberSwap 
{
public static void main(String[] args) 
	{
	Scanner ac = new Scanner(System.in);
	System.out.println("Enter First Number:  ");
	int a = ac.nextInt();
	System.out.println("a ="+a);
	System.out.println("Enter Second Number:  ");
	int b = ac.nextInt();
	System.out.println("b ="+b);
	System.out.println();
	System.out.println("Before Swapping\na = "+a+"\ny = "+b);
	int temp = a;
	a = b;
	b = temp;
	System.out.println("After Swapping\na = "+a+"\nb = "+b);	
	}	
}
