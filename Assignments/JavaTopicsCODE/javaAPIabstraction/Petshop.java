package javaAPIabstraction;
//helper class
public class Petshop
{
public Animal getAnimal(String animaltype)
	{
	Animal a = null;
	if (animaltype.equalsIgnoreCase("Dog"))
		{
			a = new Dog();
		}
	else if (animaltype.equalsIgnoreCase("Cat"))
		{
			a = new Cat();
		}
	else if (animaltype.equalsIgnoreCase("Goat"))
		{
			a = new Goat();
		}
	return a;
	}
}

	



