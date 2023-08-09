package enrollmentSystem;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileWriter;

public class enrollSystem {
	//private ArrayList<String> sid = new ArrayList<>();
	private HashMap<String, studentData> students = new HashMap<>();
	private ArrayList<String> cid = new ArrayList<>();
	private HashMap<String, courseData> courses = new HashMap<>();
	
	public enrollSystem() {
		getData();
	}
	
	public void updataFile() {
		
		JSONArray studentArray = new JSONArray(); 
		for (Entry<String, studentData> set: students.entrySet()) {
			//add all student data
			studentArray.add(set.getValue().toJson());
		}
		JSONObject studentData = new JSONObject();
		studentData.put("students", studentArray);
		try {
			//rewrite json file
			FileWriter studentFile = new FileWriter("student.json", false);
			studentFile.write(studentData.toString());
			studentFile.close();
		}
		catch (IOException e) {
            e.printStackTrace();
        } 
	}
	
	public void getData() {
		JSONParser parser = new JSONParser();
		try{
			//read course data
			 Object course = parser.parse(new FileReader("course.json"));
			 JSONObject courseData =  (JSONObject) course;
			 JSONArray c = (JSONArray) courseData.get("course");
			 Iterator<JSONObject> course_iterator = c.iterator();
			 while (course_iterator.hasNext()) {
				 JSONObject tem = course_iterator.next();
				 String code = (String) tem.get("code");
				 String title = (String) tem.get("title");
				 String day = (String) tem.get("day");
				 long start = (long) tem.get("start");
				 long end = (long) tem.get("end");
				 cid.add(code);
				 courseData course_tem = new courseData(code, title, day, start, end);
				 courses.put(code, course_tem);
			 }
			 
			//get student data
			 Object student = parser.parse(new FileReader("student.json"));
			 JSONObject studentData =  (JSONObject) student;
			 JSONArray s = (JSONArray) studentData.get("students");
			 Iterator<JSONObject> student_iterator = s.iterator();
			 while (student_iterator.hasNext()) {
				 JSONObject tem = student_iterator.next();
				 String id = (String) tem.get("id");
				 String name = (String) tem.get("name");
				 String gender = (String) tem.get("gender");
				 ArrayList<String> courselist = (ArrayList) tem.get("course");
				 //sid.add(id);
				 studentData student_tem = new studentData(id, name, gender, courselist);
				 students.put(id, student_tem);
			 }

		} 
		catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
		catch (IOException e) {
            e.printStackTrace();
        } 
		catch (ParseException e) {
            e.printStackTrace();
        } 
	}
	
	private boolean timeCheck(String enrolled, String enrolling) {
		courseData enrolledCourse = getCourse(enrolled);
		courseData enrollingCourse = getCourse(enrolling);
		// check the courses are in the same day
		if (enrollingCourse.getDay() != enrolledCourse.getDay()) {
			return true;
		}
		// check the courses are in the same time
		if (enrollingCourse.getStart() < enrolledCourse.getEnd() && enrollingCourse.getStart() > enrolledCourse.getStart()) {
			return false;
		}
		if (enrollingCourse.getEnd() < enrolledCourse.getEnd() && enrollingCourse.getEnd() > enrolledCourse.getStart()) {
			return false;
		}
		return true;
	}
	
	public void removeCourse(studentData student, String course) {
		student.removeCourse(course);
	}
	
	public void enrollment(studentData student, String course) {
		if (getCourse(course) == null) {
			System.out.println("enrollment error: unknown course");
			return;
		}
		for (String c: student.getCourse()) {
			// find if the course in the student's course list
			if (course.equals(c)) {
				System.out.println("enrollment error: replicate enrollment");
				return;
			}
			// check the available time 
			if (!timeCheck(c, course)){
				System.out.println("enrollment error: time conflict");
				return;
			}
		}
		student.addCourse(course);
	}
	
	public void studentString(studentData student) {
		// display student information
		System.out.println("SID: " + student.getId() + "\n" + "Name: " + student.getName() + "\n" + 
							"Gender: " + student.getGender() + "\nCourse: \n");
		
		// display enrolled course information
		for (String c: student.getCourse()) {
			courseData course = getCourse(c);
			System.out.println(course.getCourseString());
		}
	}
	
	public void allCourseString() {
		for (Entry<String, courseData> set: courses.entrySet()) {
			System.out.println(set.getValue().getCourseString() + "\n");
		}
	}
	
	public courseData getCourse(String code) {
		return courses.getOrDefault(code, null);
	}
	
	public studentData getstudent(String id) {
		return students.getOrDefault(id, null);
	}
	
}

