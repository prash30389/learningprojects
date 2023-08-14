package POJO;
/*
 * POJO (Plain Old Java Objects) are normal reusable java objects with several rules like,
Having getters and setters per each private property
Only inherits methods from default Object class
 * 
 * */
import org.apache.juneau.annotation.BeanConstructor;

public class Person {
	private String name;
	private String city;
	private int age;
	private String[] nickNames;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	@BeanConstructor(properties = "name,city,age,nickNames")
/*
 * Here @BeanConstructor annotation is required for parsing back to objects and it is optional for serialization.
 * */
	public Person(String name, String city, int age, String[] nickNames) {
		super();
		this.name = name;
		this.city = city;
		this.age = age;
		this.nickNames = nickNames;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String[] getNickNames() {
		return nickNames;
	}

	public void setNickNames(String[] nickNames) {
		this.nickNames = nickNames;
	}
	
}