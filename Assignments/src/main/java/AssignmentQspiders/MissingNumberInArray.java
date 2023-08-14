package AssignmentQspiders;

public class MissingNumberInArray {
	
	
	//-------------------Program to find missing no in an array------------------------
	
	 static int getMissingNo(int a[], int n) 
	    { 
	        int i, total; 
	        total = (n + 1) * (n + 2) / 2; 
	        for (i = 0; i < n; i++) 
	            total =total- a[i]; 
	        return total; 
	    } 

	
	  
	    /* program to test above function */
	    public static void main(String args[]) 
	    { 
	        int a[] = { 1, 2, 3, 4, 5, 6 ,7,9,10}; 
	        int miss = getMissingNo(a, 9); 
	        System.out.println(miss); 
	    } 
	 

	}


