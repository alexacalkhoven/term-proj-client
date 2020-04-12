package main.controller;

import java.util.ArrayList;

import main.model.Course;

//WRITE FUNCTIONS FOR STUDENT BUTTONS HERE
public class StudentFunctController {

	CommunicationController comCon;

	public StudentFunctController(CommunicationController comCon) {
		this.comCon = comCon;
	}

	public Course search(String name, int num) {
		Course result = (Course) comCon.makeRequest("course.search", new Object[] { name, num });
		return result;
	}

	public ArrayList<Course> view() {
		ArrayList<Course> result = (ArrayList<Course>) comCon.makeRequest("course.get");
		return result;
	}

	public ArrayList<Course> viewReg() {
		ArrayList<Course> result = (ArrayList<Course>) comCon.makeRequest("student.regList");
		return result;
	}

	public void regForCourse(String name, int number, int section) {
		comCon.makeRequest("student.addRegCourse", new Object[] { name, number, section });
	}

	public void dropCourse(String name, int number) {
		comCon.makeRequest("student.dropCourse", new Object[] { name, number });
	}

	public char checkIfEnrolled(Course c) {
		char result = (char) comCon.makeRequest("student.checkEnroll", new Object[] {c});
		return result;
	}
}
