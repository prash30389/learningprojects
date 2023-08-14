class Test
{
	public static void main(String[] args) {
		
		Calc m = new Calc();
			int var1 = m.add(22,8); //calling line
				System.out.println(var1);
			int var2 = m.add(var1,10); //calling line
				System.out.println(var2);
			var1 = m.add(var1,var2); //calling line
				System.out.println(var1);
	}


}