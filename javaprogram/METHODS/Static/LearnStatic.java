package METHODS.Static;

class LearnStatic 
{
	String name; 															// IDM
	int age;															
	static String CoName = "QSP";											//SDM
	void print()														//INSTANCE METHOD
		{
			System.out.println(name); 									// IDM use in Instance method DIRECTLY
			System.out.println(age);	
			System.out.println(CoName); 								// SDM use in Instance Method DIRECTLY
		}
	static void display(LearnStatic l)									//STATIC METHOD
		{
			LearnStatic ls = new LearnStatic();
			System.out.println(l.name);
			System.out.println(l.age);
			System.out.println(  CoName);
			l.print();
		}
//	public static void main(String[] args) 
//	{
//			LearnStatic ls = new LearnStatic();
//			ls.name = "amit";
//			ls.age = 24;
//			ls.CoName= "JSP";
//			display(ls); 												// DIRECTLY IN SAME CLASS
	public static void main(String[] args) 
	{
		LearnStatic ls = new LearnStatic();								// BY CREATING OBJECT of CLASS
			ls.name = "amit"; 											// INITIALIZE IDM IN STATIC METHOD
			ls.age = 28;	 
			ls.print();													// CALL INSTANCE METHOD
			System.out.println("***********************************************");
			StaticLearn sl = new StaticLearn();  						// BY CREATE OBJECT OF OTHER CLASS
			sl.print(ls.name, ls.age, ls.CoName); 						// CALLING STATIC METHOD OF OTHER CLASS DIRECTLY
		}
}
