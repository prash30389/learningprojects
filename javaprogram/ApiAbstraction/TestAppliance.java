package ApiAbstraction;
import java.util.Scanner;
//main method
public class TestAppliance {
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Appliancename: ");
		String app = sc.nextLine();
		sc.close();
		ApplianceManager a = new ApplianceManager();
		Switch r = a.giveappliance(app);
		if(r!=null)
		{
			r.switchon();
			r.switchoff();
			r.testmethod();
		}
	}
}
