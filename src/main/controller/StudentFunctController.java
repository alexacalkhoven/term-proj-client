package main.controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.model.Course;
import main.model.CourseOffering;
import main.model.Registration;

/**
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
@SuppressWarnings("unchecked")
public class StudentFunctController {
	private CommunicationController comCon;
	private ArrayList<Course> courseList;
	private ArrayList<CourseOffering> offeringList;
	
	public ArrayList<Course> viewCourses() {
		ArrayList<Course> result = (ArrayList<Course>) comCon.makeRequest("course.get");
		courseList = result;
		return result;
	}
	
	public int getCourseIdFromRow(int row) {
		return courseList.get(row).getCourseId();
	}

	public StudentFunctController(CommunicationController comCon) {
		this.comCon = comCon;
	}

	public Course search(String name, int num) {
		Course result = (Course) comCon.makeRequest("course.search", new Object[] { name, num });
		return result;
	}
	
	public ArrayList<CourseOffering> getOfferings(int id) {
		ArrayList<CourseOffering> result = (ArrayList<CourseOffering>) comCon.makeRequest("course.getOfferings", id);
		offeringList = result;
		return result;
	}

	public ArrayList<Course> view() {
		ArrayList<Course> result = (ArrayList<Course>) comCon.makeRequest("course.get");
		courseList = result;
		return result;
	}

	public ArrayList<Registration> getRegistrationList() {
		return (ArrayList<Registration>) comCon.makeRequest("student.regList");
	}

	public void regForCourse(Integer offeringId){
		comCon.makeRequest("student.addRegCourse", offeringId);
	}

	public void dropCourse(Integer number) {
		comCon.makeRequest("student.dropCourse", number);
	}
	
	public int getOfferingIdFromRow(int row)  throws IndexOutOfBoundsException{
		if(offeringList.size() == 0) {
			throw new IndexOutOfBoundsException("No Offerings!");
		}
		return offeringList.get(row).getOfferingId();
	}
}
