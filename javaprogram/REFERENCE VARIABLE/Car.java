class Car
{
String Modelname;
public static void main(String[] args) {
	Car c = new Car();
	c.Modelname = "swift";
	System.out.println(c.Modelname);
	Car y = c;
	System.out.println(y.Modelname);
	y.Modelname = "zen";
	System.out.println();
	System.out.println(c.Modelname);
	System.out.println(y.Modelname);
	}

}