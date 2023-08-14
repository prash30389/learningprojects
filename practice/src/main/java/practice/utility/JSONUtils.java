package practice.utility;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class JSONUtils {
	static JSONObject jsonObj = null;
	static JSONObject jsonFile= null;

    /**
     * @author Ashish
     * @param fileName
     * @return
     * @throws Exception
     */
    public static Object readJsonDataFromDefaltFolder(String fileName) throws Exception {
        if (fileName.isEmpty() || fileName == null)
            throw new Exception("Please provide valid file name");
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return obj;
    }
    
    public static JSONObject readJsonData(String folderName, String fileName) throws Exception {
	    if (fileName.isEmpty() || fileName == null) {
	      throw new Exception("Please provide valid file name");
	    }
	    JSONParser parser = new JSONParser();
	    JSONObject jsonObject = null;
	    Object obj;
	    try {
	      obj = parser.parse(new FileReader(folderName + "/" + fileName + ".json"));
	      jsonObject = (JSONObject) obj;
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    } catch (ParseException e) {
	      e.printStackTrace();
	    }
	    return jsonObject;
	}
    
    /**
     * Verify that response contains json value
     * @param response : response object
     * @param path : josn node path 
     * @param key : node name for which we required to check value
     * @param value : Node value
     * @return : It will return true if json node value exist and return false if value not exist
     * @throws Exception
     * @author Ashish
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static boolean verifyValueInResponse(Response response, String path, String key, Object value) throws Exception {
        boolean isAvailable = false;
        JsonPath jsonPath = new JsonPath(response.asString());
        Object object = jsonPath.getJsonObject(path);
         if (object instanceof HashMap) {
            isAvailable = verifyValueInMap((HashMap<String, Object>) object, key, value);
        } else if (object instanceof ArrayList) {
            isAvailable = verifyValueInList((ArrayList) object, key, value);
        } else {
            if (object.equals(value)) {
                isAvailable = true;
            }
        }
        Assert.assertTrue(isAvailable,"Value "+value+" not found for node "+key );
        return isAvailable;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static boolean verifyValueInMap(HashMap<String, Object> map, String key, Object value) throws Exception {
        boolean isAvailable = false;
        for (Map.Entry<String, Object> m : map.entrySet()) {
            Object object = m.getKey();
            if (object instanceof HashMap) {
                isAvailable = verifyValueInMap((HashMap<String, Object>) object, key, value);
                if (isAvailable) {
                    break;
                }
            } else if (object instanceof ArrayList) {
                isAvailable = verifyValueInList((ArrayList) object, key, value);
                if (isAvailable) {
                    break;
                }
            } else {
                if (m.getKey().equals(key)) {
                    if (m.getValue() instanceof ArrayList) {
                        isAvailable = verifyValueInList((ArrayList) m.getValue(), key, value);
                        if (isAvailable) {
                            break;
                        }
                    } else if (m.getValue() instanceof HashMap) {
                        isAvailable = verifyValueInMap((HashMap<String, Object>) m.getValue(), key, value);
                        if (isAvailable) {
                            break;
                        }
                    } else {
                        if (value == null && m.getValue() == null) {
                            isAvailable = true;
                            break;
                        } else if (m.getValue().equals(value)) {
                            isAvailable = true;
                            break;
                        }
                    }
                }
            }
        }
        return isAvailable;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static boolean verifyValueInList(ArrayList list, String key, Object value) throws Exception {
        boolean isAvailable = false;
        for (int i = 0; i < list.size(); i++) {
            Object object = list.get(i);
            if (object instanceof HashMap) {
                isAvailable = verifyValueInMap((HashMap<String, Object>) object, key, value);
                if (isAvailable) {
                    break;
                }
            } else if (object instanceof ArrayList) {
                isAvailable = verifyValueInList((ArrayList) object, key, value);
                if (isAvailable) {
                    break;
                }
            } else {
                if (object != null) {
                    if (object.equals(value)) {
                        isAvailable = true;
                        break;
                    }
                }
            }
        }
        return isAvailable;
    }

    /**
     * @author Ashish
     * @param jsonObject
     * @param nodePath
     * @param key
     * @param value
     * @return
     */
    public static String replaceValueInArray(JSONObject jsonObject,String nodePath,String key,String value){
        JSONArray arrayJson = (JSONArray) jsonObject.get(nodePath);
        if (arrayJson != null) {
            int len = arrayJson.size();
            for (int i=0;i<len;i++){
                JSONObject jsonObject1= (JSONObject) arrayJson.get(i);
                jsonObject1.replace(key,value);
            }
        }
        return jsonObject.toJSONString();
    }

    /**
     * @author Ashish
     * @param anyDate
     * @return
     * @throws java.text.ParseException
     */
    public static String getNextDate(String  anyDate) throws java.text.ParseException {
        int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        final Date date = format.parse(anyDate);
        String nextDay = format.format(date.getTime() - MILLIS_IN_DAY);
        return nextDay;
    }

    /**
     * @author Ashish
     * @param anyDate
     * @return
     * @throws java.text.ParseException
     */
    public static String getNewDateOfNextYear(String  anyDate) throws java.text.ParseException {
        int MILLIS_IN_DAY = 1000 * 60 * 60 * 24 * 365;
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        final Date date = format.parse(anyDate);
        String nextDay = format.format(date.getTime() + MILLIS_IN_DAY);
        return nextDay;
    }
    
    public static String getValueFromConstant(String filename, String path) throws Exception {
    	jsonFile = readJsonData("constant", filename);
    	JsonPath jsonPath = new JsonPath(jsonFile.toString());
    	if (jsonPath.get(path) instanceof String) {
    		return jsonPath.get(path);
    	} else {
    		ArrayList<String> list = jsonPath.get(path);
    		String value = list.get(0);
    		return value;
    	}
    }
}
