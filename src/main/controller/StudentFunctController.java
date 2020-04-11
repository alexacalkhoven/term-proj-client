package main.controller;

import java.util.ArrayList;

import main.model.Course;

//WRITE FUNCTIONS FOR STUDENT BUTTONS HERE
public class StudentFunctController {

	CommunicationController comCon;

	public StudentFunctController(CommunicationController comCon) {
		this.comCon = comCon;
	}

	public Course search(String[] s) {

		Course result = (Course) comCon.makeRequest("searchCourse", s[0]);
		return result;
	}
	public ArrayList<Course> view(){
		ArrayList<Course> result = (ArrayList<Course>) comCon.makeRequest("course.get");
		return result;
	}
	public ArrayList<Course> viewReg(){
		ArrayList<Course> result = (ArrayList<Course>) comCon.makeRequest("student.regList");
		return result;
	}
	public void regForCourse(String[] s) {
		comCon.makeRequest("student.addRegCourse");
	}
	public void deleteCourse(String[] s) {
		comCon.makeRequest("student.deleteCourse");
	}
}
