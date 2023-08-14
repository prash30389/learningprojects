package StringOperation;abstract
public class string 
{
static String s4 ="";
	public static void main(String[] args) 
	{
		String s= new  String("vikas paliwal");
		String[] s1= s.split(" ");
		for(int i=0; i<=1;i++)
		{
		String s2 = s1[i];
		for(int j=s2.length()-1;j>=0;j--)
		{
			s4=s4+s2.charAt(j);
		}
		}
		System.out.print(s4);
		
	}
}


