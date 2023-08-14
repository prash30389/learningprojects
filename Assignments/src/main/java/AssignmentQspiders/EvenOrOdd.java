package AssignmentQspiders;

import java.util.Scanner;

public class EvenOrOdd {
	
	
	//-----------------------------------------------To check whether no is even and odd-----------------------------------------------
	 static boolean isEven(int n) 
	    { 
	        boolean isEven = true; 
	          
	        for (int i = 1; i <= n; i++)  
	            isEven = !isEven; 
	              
	        return isEven; 
	    } 

	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);

		System.out.println("Enter the number:");
		
		int val = s.nextInt();
		s.close();
        if(isEven(val)) 
            System.out.println("Even"); 
        else
            System.out.println("Odd"); 
		
		
	}

}
