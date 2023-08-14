package TYPECASTING; // converting lower Data type into higher data type i called widening

public class Widening 
{
static void fun(double d)
{
	System.out.println("double value" + d);
}
static void fun(float f)
{
	System.out.println("double value" + f);
}
public static void main(String[] args)
{
	fun(10.5);
	fun ( 10.5f);
	fun (10); // IMPLICIT WIDENING DONE BY COMPILER
	fun ((double)20); // EXPLICIT WIDENING : DONE BY PROGRAMMER USING CASTING.
}
}
