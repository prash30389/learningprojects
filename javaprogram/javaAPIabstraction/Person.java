package javaAPIabstraction;
import java.util.Scanner;
public class Person {
	void purchaseanimal()
	{
	Scanner sc = new Scanner(System.in);
	System.out.println("Welcome to the Petshop : \n");
	System.out.println("Enter Animal Type");
	String type = sc.nextLine();
	sc.close();
	Petshop p = new Petshop();
	Animal a = p.getAnimal(type);
	a.noise();
	System.out.println("Pet Delivered ");
	}
	public static void main(String[] args) {
		Person per = new Person();
		per.purchaseanimal();
	}
}

