package practice.dynamodb;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.asserts.SoftAssert;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

/**###########################################
 * 
 * @author SHREYAS PATIL
 * 
 *##############################################
 */
public final class CommonUtility {
	
	static SoftAssert softAssert = new SoftAssert();
	public static Properties prop;
	
	public static final void loadConfigFile() throws IOException
	{
		prop = new Properties();
		FileInputStream inputstream1 = new FileInputStream(System.getProperty("user.dir") + "/config.properties");
		prop.load(inputstream1);
	}
	
	public static final void establishConnectionToTable(String pno)
	{
		DynamoDB dynamoDB = null;
		Table table = null;
		QuerySpec spec = null;
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());

		try {
			BasicAWSCredentials credentials = new BasicAWSCredentials(prop.getProperty("AWS_AccessKey"), prop.getProperty("AWS_SecretKey"));
			AmazonDynamoDBClient client = new AmazonDynamoDBClient(credentials).withRegion(Regions.AP_SOUTH_1);
			dynamoDB = new DynamoDB(client);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	   
		try {
			table = dynamoDB.getTable("AegonEvent_QA");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	   
		try {
			spec = new QuerySpec()
			        .withKeyConditionExpression("launchedDate = :LaunchDate")
			        .withFilterExpression("#policyNumber = :PolicyNumber")
			        .withNameMap(new NameMap().with("#policyNumber","Context-policyNumber"))
			        .withValueMap(new ValueMap()
			                .withString(":LaunchDate",date)
			                .withString(":PolicyNumber",pno));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
							e1.printStackTrace();
			}

	    		
	    String jsonData="[";
	    try {
			ItemCollection<QueryOutcome> items = table.query(spec);
			Iterator<Item> iterator = items.iterator();
			Item item = null;
			while (iterator.hasNext()) {
				    item = iterator.next();
				    jsonData= jsonData+item.toJSONPretty()+",";
				}
			jsonData = jsonData.replaceAll(",$","")+"]";
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    
	    try {
	         FileWriter file = new FileWriter(System.getProperty("user.dir")+"\\output.json");
	         file.write(jsonData);
	         file.close();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	}
	
	public static final ArrayList getDatafromJsonFile() throws  ParseException, IOException
	{
		JSONParser jParser  = new JSONParser();
		FileReader reader   = new FileReader(System.getProperty("user.dir")+"\\output.json");
		JSONArray jsonArray = (JSONArray) jParser.parse(reader);
		return jsonArray;
	}
	
	public static final void compareEvents() throws IOException, ParseException
	{
		for (int i = 0; i <= getDatafromJsonFile().size()-1; i++)
		{
			JSONObject jsonType = (JSONObject) getDatafromJsonFile().get(i);
			String type = String.valueOf(jsonType.get("type"));
			EventSets.dynamodbEvents.add(type);
		}
		
		//System.out.println("===dynamodb Events==="+ getDatafromJsonFile().size());
		for (Object printEvents : EventSets.dynamodbEvents)
		 //   System.out.println(printEvents);
	//	System.out.println("=====================");
		
		
		for (String nameOfStandardEvent : EventSets.standardEvents) { 
            Integer countOfStandardEvent = Events.hashmapStandard.get(nameOfStandardEvent); 
            Events.hashmapStandard.put(nameOfStandardEvent, (countOfStandardEvent == null) ? 1 : countOfStandardEvent + 1); 
        } 
		
        for (String nameOfDynamodbEvent : EventSets.dynamodbEvents) { 
            Integer countOfDynamodbEvent = Events.hashmapDynamodb.get(nameOfDynamodbEvent); 
            Events.hashmapDynamodb.put(nameOfDynamodbEvent, (countOfDynamodbEvent == null) ? 1 : countOfDynamodbEvent + 1); 
        } 
	}
	
	
	public static final void compareFreekookAndEndorsementEvents() throws ParseException, IOException {
		SoftAssert st = new SoftAssert();
		String eventsFound = "Yes";

		for (int i = 0; i <= getDatafromJsonFile().size() - 1; i++) {
			JSONObject jsonType = (JSONObject) getDatafromJsonFile().get(i);
			String type = String.valueOf(jsonType.get("type"));
			EventSets.dynamodbEvents.add(type);
		}
		int flag = 0;
		String events = null;
		for (int i = 0; i <= EventSets.standardEvents.size() - 1; i++) {
			if(EventSets.dynamodbEvents.size()==0){
				eventsFound="No";
				break;
			}
			for (int j = 0; j <= EventSets.dynamodbEvents.size() - 1; j++) {
				
				flag = 0;

				//System.out.println("****"+EventSets.standardEvents.get(i));
				if (EventSets.standardEvents.get(i).equals(EventSets.dynamodbEvents.get(j))) {
					
					//System.out.println("1: "+EventSets.standardEvents.get(i));
					EventSets.commonEvents.add(EventSets.dynamodbEvents.get(j));
					
					break;
				} else {
					events = EventSets.standardEvents.get(i);
					
					flag = 1;
				}
			}

			if (flag == 1) {
				st.fail(events + " | events not found !!!");
				System.out.println("Events not equal");
			}
		}
		if(eventsFound.equals("No")){
			System.out.println("--------------------------------");
			st.fail(events + " | events not found..Please check your working environment DT or QA ");
		}
		st.assertAll();

	}
}	
	
	
	
	
	
	
	

