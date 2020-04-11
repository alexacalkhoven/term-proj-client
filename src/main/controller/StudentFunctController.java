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
		ArrayList<Course> result = (ArrayList<Course>) comCon.makeRequest("course.list");
		return result;
	}
}
