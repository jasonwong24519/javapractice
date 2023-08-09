package enrollmentSystem;

import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class studentData {
	private String id;
	private String name;
	private String gender;
	private ArrayList<String> courses;
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getGender() {
		return gender;
	}
	
	public ArrayList<String> getCourse() {
		return courses;
	}
	
	public String courseString() {
		String courseString = "";
		if (courses.size() > 0) {
			courseString = "\"" + String.join("\",\"", courses) + "\"";
		}
		return "["+ courseString + "]";
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public void addCourse(String c) {
		this.courses.add(c);
	}
	
	public studentData(String id, String name, String gender, ArrayList<String> courses) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.courses = courses;
	}
	
	public void removeCourse(String course) {
		for (int i = 0; i < this.courses.size(); i++) {
			
			// find the course want to remove
			if (this.courses.get(i).equals(course)) {
				this.courses.remove(i);
				System.out.println("cancel enrollment success");
				return;
			}
		}
		System.out.println("course dont exist");
	}
	
	public JSONObject toJson() {
		
		//return json object
		JSONParser parser = new JSONParser();
		try {
			String data = "{\"id\":\"" + id + "\"\"name\":\"" + name + "\",\"gender\":\"" + gender + "\",\"course\":" + courseString() + "}";
			Object student = parser.parse(data);
			JSONObject studentData =  (JSONObject) student;
			return studentData;
		}
		catch (ParseException e) {
            e.printStackTrace();
        }
		return null; 
	}
}

