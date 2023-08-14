package INTERFACES;
// JAVA TOOK STRING AS PRE-DEFINED CLASS IN JAVA.LANG PACKAGE IN JAVA LIBRARY. every string value(" ") in java exist as object. 
public class StringTest {//String is a final class so it is not inherited. its a sub class
	public static void main(String[] args) // String super class is Object class [pre-defined class in java]
	{
		String s1 = "Hi"; // ONCE OBJECT IS CREATED ITS STATE CAN'T BE CHANGED . IT IS IMMUTABLE(NOT INFECTED FROM ANYTHING)
		String s2 = "Hi"; // IT REFERS TO THE SAME OBJECT IN string constant pool memory. IF CONTENT IS SAME.
		String s3 = "hi"; //IT WILL CREATE NEW OBJECT WITH MODIFIED CONTENT. IT ALSO DE-REFEEREED IF YOU WILL ENTER VALUE IN SAME VARIABLE
		String Str1 = new String("Hello");
		String Str2 = new String("Hello");
		String Str3 = new String("Hi");
	System.out.println("Result Of Operator");/* compares string object on the basis of address of an object 
	in reference variable */
	
	System.out.println(s1==s2);
	System.out.println(s1==s3);
	System.out.println(Str1 ==Str2);
	System.out.println(Str3 == s1);
	
	System.out.println("Result of equal method");/* compares string object on the basis of content
	in reference variable */ 
	System.out.println(s1.equals(Str3));
	System.out.println(Str1.equals(Str2));
	System.out.println(s1.equals(s3));
	System.out.println(s1.equalsIgnoreCase(s3));
	}	
}

