package AssignmentQspiders;

import java.util.HashSet;

public class DuplicateNoInAnArray {
	
	static int findRepeating(int arr[], int n)  
	{  
	    HashSet<Integer> s = new HashSet<Integer>(); 
	    for (int i = 0; i < n; i++)  
	    {  
	        if (s.contains(arr[i]))  
	        {
	        	return arr[i];  
	        }
	        else {
	            
	        s.add(arr[i]);
	        }
	    }  
	      
	    // If input is correct, we should  
	    // never reach here  
	    return -1;  
	}  

	public static void main(String[] args) {
		
		 int arr[] = { 9, 8, 9, 9, 3, 3, 0, 3, 9, 9 };  
		    int n = arr.length; 
		    System.out.println(findRepeating(arr, n));; 
		} 
		

	}


