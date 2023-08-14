package ExceptionHandling; // unchecked exception

public class DoubleJunk 
{
static int divide (int num1, int num2) // arithmetic Exception
{
	return num1/num2;
}
public static void main(String[] args) 
{
	divide(10,5);
}
}// Compiler will not force to provide TRY-CATCH for arithmetic Exception as it is UN-CHECKED EXCEPTION
