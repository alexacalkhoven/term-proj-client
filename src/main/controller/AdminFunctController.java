package main.controller;

public class AdminFunctController {
	CommunicationController comCon;
	
	public AdminFunctController(CommunicationController comCon) {
		this.comCon = comCon;
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
}
