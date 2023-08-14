class Burger // Mcd Serve [action] {method} Burger{object} only after having order[sting] and money[int]
{
	String name;//IDM
	int price;//IDM
}
class McD
{
	Burger Servefood(String order, int money) //CLASS(AS RETURN TYPE) <METHODNAME> ( PARAMETERS)
	{
	Burger b = new Burger(); // CREATE OBJECT OF BURGER CLASSNAME <BURGER> REFERENCE VARIABLE<B> = KEYWORD<NEW> CONSTRUCTERCALL() BURGER()
			b.name = order;
			b.price = money;
	return b; //RETURN VALUE OF B MEANS BURGER CLASS
	}
}