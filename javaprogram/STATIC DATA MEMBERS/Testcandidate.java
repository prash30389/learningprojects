public class Testcandidate
{
	public static void main(String[] args) 
	{
		Candidate c1= new Candidate("ram", "dev");
				c1.name="golu";
			System.out.println(c1.name);
			System.out.println(c1.course);
			System.out.println(Candidate.instname);
			Candidate.instname = "jspiders";
			System.out.println(c1.instname);
		Candidate c2= new Candidate("ram", "dev");
			System.out.println(c2.instname);
			System.out.println(c2.name);
			System.out.println(c2.course);
		Candidate c3= new Candidate("ram", "dev");
				c3.instname="jspiders";
			System.out.println(c3.name);
			System.out.println(c1.instname);
			System.out.println(c2.instname);
	}

}