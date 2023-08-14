/*ONE CONSTRUCTOR CALLING ANOTHER CONSTRUCTER IS CALLED CHAINING
 * IT CAN BE DONE USING SUPER () KEYWORD OR THIS() KEYWORD[IN SAME CLASS WITH VALID ARGUMENTS]
 * THIS(): it used to call constructor in same class and must be used explicitly, in Constructor body.
 */
package INHERITANCE;

public class Conspen 
{
String color;
Conspen()
	{
		this("blue"); // call constructor of same class with string arguments
	}
Conspen(String color)
	{
		this.color = color; // SUPER() AND THIS() CANNOT BE USED TOGETHER.
	}
public static void main(String[] args) {
	Conspen cp = new Conspen();
	System.out.println(cp.color);
}
}
