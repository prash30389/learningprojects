package POJO;

import java.util.Arrays;

import org.apache.juneau.json.JsonParser;
import org.apache.juneau.json.JsonSerializer;
import org.apache.juneau.xml.XmlSerializer;

public class PersonSerialize {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		JsonSerializer jsonSerializer = JsonSerializer.DEFAULT_READABLE;

		String[] nickNames = {"Jil", "Bil", "Rock"};
		Person pojo = new Person("Jackson", "Colombo", 24, nickNames);
		String json = jsonSerializer.serialize(pojo);
		System.out.println(json);
		
		XmlSerializer xmlSerializer = XmlSerializer.DEFAULT_SQ_READABLE;
		String xml = xmlSerializer.serialize(pojo);
		System.out.println(xml);
		
		JsonParser jsonParser = JsonParser.DEFAULT;
		String json1 = "{\"name\":\"Jackson\",\"age\":24,\"nickNames\":[\"Jil\",\"Bil\",\"Rock\"],\"city\":\"Colombo\"}";
		Person p = jsonParser.parse(json1, Person.class);
    
		System.out.println(Arrays.toString(p.getNickNames()));
	}

}
