package dataDriven;



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class jsonc {
	
	public static JSONObject updatePoiJsonInput(JSONObject json, String key, String value) throws Exception {
		if (key.equals("id")) {
			return (JSONObject) updateJsonValue(json, key, value);
		} else {
			JSONObject jsonObj = (JSONObject) updateJsonValue(json, "id", "poi");
			return (JSONObject) updateJsonValue(jsonObj, key, value);
		}
	}
	public static Object updateJsonValue(Object json, String key, String value) throws Exception {
		JSONObject jsonObject = null;
		JSONArray jsonArray = null;
		if (json instanceof JSONArray) {
			// It's an array
			jsonArray = (JSONArray) json;
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject obj = (JSONObject) jsonArray.get(i);
				updateJsonValue(obj, key, value + i);
			}
		} else if (json instanceof JSONObject) {
			// It's an object
			jsonObject = (JSONObject) json;
			return (Object) updateJsonValue(jsonObject, key, value);
		} else {
			throw new Exception("Please provide valid Json");
		}
		return json;
	}

}
