package main.controller;

import java.util.ArrayList;

import main.model.Course;

public class AdminFunctController {
	CommunicationController comCon;
	
	public AdminFunctController(CommunicationController comCon) {
		this.comCon = comCon;
	}
	
	public ArrayList<Course> viewCourses() {
		ArrayList<Course> result = (ArrayList<Course>) comCon.makeRequest("course.get");
		return result;
	}
	
	public void removeStudent(int id) {
		comCon.makeRequest("student.remove", id);
	}
	
	public void createStudent(String name, int id) {
		comCon.makeRequest("student.create", new Object[] { name, id });
	}
	
	public boolean removeCourse(String name, int id) {
		return (boolean) comCon.makeRequest("course.remove", new Object[] { name, id });
	}

	public void createCourse(String name, int id) {
		comCon.makeRequest("course.create", new Object[] {name, id});
	}

	public void addOffering(int num, int cap, String courseName, int courseNum) {
		comCon.makeRequest("course.addOffering", new Object[] { num, cap, courseName, courseNum });
	}
}
