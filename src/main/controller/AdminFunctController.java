package main.controller;

public class AdminFunctController {
	CommunicationController comCon;
	
	public AdminFunctController(CommunicationController comCon) {
		this.comCon = comCon;
	}
	
	public void viewCourses() {
		comCon.makeRequest("course.viewAll");
	}
	
	public void removeStudent(int id) {
		comCon.makeRequest("student.remove", id);
	}
	
	public void createStudent(String name, int id) {
		comCon.makeRequest("student.create", new Object[] { name, id });
	}
	
	public void removeCourse(String name, int id) {
		comCon.makeRequest("course.remove", new Object[] { name, id });
	}

	public void createCourse(String name, int id) {
		comCon.makeRequest("course.create", new Object[] {name, id});
	}

	public void addOffering(int num, int cap, String courseName, int courseNum) {
		comCon.makeRequest("course.addOffering", new Object[] {num, cap, courseName, courseNum});
	}
}
