package dataDriven;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIConversion.User;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class jsonNxmlDriven 
{
	
	@Test
	public static void propertyFile() throws IOException
		{
			Properties prop =  new Properties();
			String path ="E:/eclipse-workspace/sample/src/main/study/dataDriven/restAPICodes.properties";
			
			//to read
			FileInputStream  ip = new FileInputStream(path);
			prop.load(ip);
			
			//to update
			prop.setProperty("location", "Noida Sector-85");
			
			//to save the update
			FileOutputStream op = new FileOutputStream(path);
			prop.store(op, "properties file saved");
			op.close();
		}

	@Test
	public static void JSONFile() throws Exception
		{
			String Jsonpath = "E:/eclipse-workspace/sample/src/main/study/dataDriven";
			String JsonFile = Jsonpath + "/ProposalRequest.json";
			
			JSONParser parser = new JSONParser();
			FileReader fr = new FileReader(JsonFile);
			Object obj = parser.parse(fr);

			JSONObject js = (JSONObject) obj;
           
           // String path = "healthInfo.smokerType" ;
			System.out.println(js);
        	JSONObject jsonObject= (JSONObject) js.get("healthInfo");
        	System.out.println(jsonObject);
        	jsonObject.remove("smokerType");
        	System.out.println(jsonObject);
        	jsonObject.put("smokerType","SMOKER");
        	System.out.println(jsonObject);
        
		}
	
	@Test
	public static void XmlFile() throws Exception {
		 int id;
		    String name;
		    String email;
		    String[] roles;
		    boolean admin;
	}
	
	
	@Test
	public static void XmlFile1() throws Exception {
	
		File file = new File("user.xml");

    // create an instance of `JAXBContext`
    JAXBContext context = JAXBContext.newInstance(User.class);

    // create an instance of `Unmarshaller`
    Unmarshaller unmarshaller = context.createUnmarshaller();

    // convert XML file to user object
    User user = (User) unmarshaller.unmarshal(file);

    // print user object
    System.out.println(user);
	}
}
