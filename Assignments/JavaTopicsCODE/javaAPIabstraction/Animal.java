package javaAPIabstraction;

public abstract class Animal 
{
		public abstract void noise();
}
class Dog extends Animal
	{
		@Override
			public void noise() 
			{
				System.out.println(" baeh baeh ");
			}
	}
class Cat extends Animal
	{
		@Override
			public void noise() 
			{
				System.out.println(" meow meow ");
				
			}
	}
class Goat extends Animal
	{
		@Override
			public void noise() 
			{
				System.out.println(" may may ");
			}
	}
