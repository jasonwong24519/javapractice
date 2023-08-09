package enrollmentSystem;

import java.util.Scanner;

public class main {
	public static void main(String[] args){
		// call enrollment system and read the json files
		enrollSystem enrollSystem = new enrollSystem();
		
		// call scanner
		Scanner input = new Scanner(System.in);
		Scanner scanner = new Scanner(System.in);
		int controler;
		
		do {
			System.out.println("**********************************************************************");
			System.out.println("enter 1 to enter student menu.");
			System.out.println("enter 2 to inquire course inforamtion.");
			System.out.println("enter 3 to exit.");
			System.out.println("**********************************************************************");
			controler = input.nextInt();
			
			// break while loop
			if (controler == 3) {
				break;
			}
			
			switch(controler) {
				case(1): {
					// get the student class
					System.out.println("enter the student id: (1001-1008)");
					String sid = input.next();
					studentData student = enrollSystem.getstudent(sid);
					if (student == null) {
						System.out.println("error: unkown student");
						System.out.println("return to main menu");
						break;
					}
					do {
						System.out.println("**********************************************************************");
						System.out.println("enter 1 to inquire student information.");
						System.out.println("enter 2 to modify student name.");
						System.out.println("enter 3 to enroll course.");
						System.out.println("enter 4 to cancel course.");
						System.out.println("enter 5 to exit.");
						System.out.println("**********************************************************************");
						int controler1 = input.nextInt();
					
						// break while loop
						if (controler1 ==5) {
							break;
						}
					
						switch(controler1) {
							case(1): {
							
								// return student info
								enrollSystem.studentString(student);
								break;
							}
							case(2): {
							
								//rewrite student info 
								enrollSystem.studentString(student);
								System.out.println("name: " + student.getName());
								System.out.println("enter new name to modify name, if dont want to modify, enter 1");
								String newName = input.next();
								if (newName.equals("1")) {
									;
								} else {
									student.setName(newName);
								}
								System.out.println("gender: " + student.getGender());
								System.out.println("enter new name to modify name, if dont want to modify, enter 1");
								String newgender = input.next();
								if (newgender.equals("1")) {
									;
								} else {
									student.setGender(newgender);
								}
								break;
							}
							case(3): {
							
								//add course
								System.out.println("enter the course code for enrollment.");
								String cid = input.next();
								enrollSystem.enrollment(student, cid);
								break;
							}
							case(4): {
							
								//remove course
								System.out.println("enter the course code for cancellation.");
								String cid = input.next();
								enrollSystem.removeCourse(student, cid);
								break;
							}
							default: {
								System.out.println("error: unknown command");
								break;
							}
					
						} 
					} while (true);
					break;
				}
				
				case(2): {
					do {
						System.out.println("**********************************************************************");
						System.out.println("enter course code to inquire course infromation.");
						System.out.println("enter \"all\" to inquire all course inforamtion.");
						System.out.println("enter \"exit\" to exit.");
						System.out.println("**********************************************************************");
						String controler2 = input.next();
						
						//display all course info
						if (controler2.equals("all")) {
							enrollSystem.allCourseString();
						} else if (controler2.equals("exit")) {
							break;
						} else {
							
							// get the course info by hashmap key
							courseData course = enrollSystem.getCourse(controler2);
							if (course == null) {
								
								//message for null return 
								System.out.println("error: unknown course code \"" + controler2 + "\"");
								continue;
							}
							System.out.println(course.getCourseString());
						}
					} while (true);
					break;
				}
				
				// return for unexpected command
				default: {
					System.out.println("error: unknown command");
					break;
				}
				
			}
		} while (true);
		
		//update json file
		enrollSystem.updataFile();
	}
}
