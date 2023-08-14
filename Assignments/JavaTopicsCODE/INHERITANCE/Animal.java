package INHERITANCE;

public class Animal {
	String Animalname;
	String Animaltype;
	public static void main(String[] args) {
		Cow c = new Cow();
		c.Animalname = "Cow";
		c.Animaltype = "pet mammal";
		c.Mammalwork();
		System.out.println("XXXXXXX\n");
		Lion li = new Lion();
		li.Animalname = "Lion";
		li.Animaltype = "wild mammal";
		li.Mammalwork();
		System.out.println("XXXXXXX\n");
		Lizard l = new Lizard();
		l.Animalname = "Lizard";
		l.Animaltype = "Poisoness reptile";
		l.Reptilework();
		System.out.println("XXXXXXX\n");
		Snake s = new Snake();
		s.Animalname = "snake";
		s.Animaltype = "Poison reptile";
		s.Reptilework();
	}
}
class Mammal extends Animal
{
}
class Cow extends Mammal {
	void Mammalwork()
	{
		System.out.println(this.Animalname + " is a " + this.Animaltype + ". this is the species \n"+ "it belongs to Animal ");
	}
}
class Lion extends Mammal { 
	void Mammalwork()
	{
		System.out.println(this.Animalname + " is a " + this.Animaltype + ". this is the species Category\n"+ "it belongs to  wild Animal ");
	}
}
class Reptile extends Animal
{
}
class Lizard extends Reptile {
	void Reptilework()
	{
		System.out.println(this.Animalname + " is a " + this.Animaltype + " . this is the species \n"+ "it belongs to Animal ");
	}
}
class Snake extends Reptile {
	void Reptilework()
	{
		System.out.println(this.Animalname + " is a " + this.Animaltype + " . this is the species \n" + "it belongs to Animal ");
	}
}


