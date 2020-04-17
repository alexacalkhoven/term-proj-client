package main.controller;

import java.util.ArrayList;
import main.model.Course;
import main.model.CourseOffering;

/**
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class AdminFunctController {
	private CommunicationController comCon;
	private ArrayList<Course> courseList;
	
	public AdminFunctController(CommunicationController comCon) {
		this.comCon = comCon;
	}
	
	public int getCourseIdFromRow(int row) {
		return courseList.get(row).getCourseId();
	}
	
	public ArrayList<Course> viewCourses() {
		@SuppressWarnings("unchecked")
		ArrayList<Course> result = (ArrayList<Course>) comCon.makeRequest("course.get");
		courseList = result;
		return result;
	}
	
	public void removeStudent(int id) {
		comCon.makeRequest("student.remove", id);
	}
	
	public void createStudent(String name, int id) {
		comCon.makeRequest("student.create", new Object[] { name, id });
	}
	
	public boolean removeCourse(int courseId) {
		return (boolean) comCon.makeRequest("course.remove", courseId);
	}

	public void createCourse(String name, int id) {
		comCon.makeRequest("course.create", new Object[] { name, id });
	}

	public void addOffering(int courseId, int num, int cap) {
		comCon.makeRequest("course.addOffering", new Object[] { courseId, num, cap });
	}

	public Object search(String name, int num) {
		Course result = (Course) comCon.makeRequest("course.search", new Object[] { name, num });
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<CourseOffering> getOfferings(Integer id) {
		ArrayList<CourseOffering> result = (ArrayList<CourseOffering>) comCon.makeRequest("course.getOfferings", id);
		return result;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Course> getPrereqs(Integer courseId) {
		ArrayList<Course> result = (ArrayList<Course>) comCon.makeRequest("course.getPreReqs", courseId);
		return result;
	}
}
