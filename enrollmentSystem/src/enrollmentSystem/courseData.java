package enrollmentSystem;

import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class courseData{
	private String code;
	private String title;
	private String day;
	private long start;
	private long end;
	
	public String getCode() {
		return code;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDay() {
		return day;
	}
	
	public long getStart() {
		return start;
	}
	
	public long getEnd() {
		return end;
	}
	
	public courseData(String id, String title, String day, long start, long end) {
		super();
		this.code = id;
		this.title =title;
		this.day = day;
		this.start = start;
		this.end = end;
	}
	
	public String getCourseString() {
		return "Course code: " + code + "  Title: " + title + "\n" + 
				"Start time: " + String.format("%04d", start) + "  End time: " + String.format("%04d", end);
	}
	
	public courseData() {
		super();
	}
	
	public JSONObject toJson() {
		
		//return json object
		JSONParser parser = new JSONParser();
		try {
			String data = "{\"code\":\"" + code + "\",\"title\":\"" + title + "\",\"day\":\"" + day + "\",\"start\":" + start +  ",\"end\":" + end + "}";
			Object course = parser.parse(data);
			JSONObject courseData =  (JSONObject) course;
			return courseData;
		}
		catch (ParseException e) {
            e.printStackTrace();
        }
		return null; 
	}
}
