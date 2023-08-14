package METHODS.Static;

public class StaticLearn 
{
String email; 
	static void print(String name, int age, String CoName) // IDM CANNOT USE IN STATIC METHOD
	{
		System.out.println(name);
		System.out.println(LearnStatic.CoName);
		System.out.println(CoName);
		System.out.println(age);
	}
}
