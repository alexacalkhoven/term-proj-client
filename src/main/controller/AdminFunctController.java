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
	/**
	 * gets the courseId from the highlighted row
	 * @param row the row that is highlighted
	 * @return returns the course id
	 */
	public int getCourseIdFromRow(int row) {
		return courseList.get(row).getCourseId();
	}
	/**
	 * views all the courses
	 * @return returns an arraylist of all the courses
	 */
	public ArrayList<Course> viewCourses() {
		@SuppressWarnings("unchecked")
		ArrayList<Course> result = (ArrayList<Course>) comCon.makeRequest("course.get");
		courseList = result;
		return result;
	}
	/**
	 * removes a student
	 * @param id the id of the student
	 */
	public void removeStudent(int id) {
		comCon.makeRequest("student.remove", id);
	}
	/**
	 * creates a student
	 * @param name the name of the student
	 * @param id the id of the student
	 */
	public void createStudent(String name, int id) {
		comCon.makeRequest("student.create", new Object[] { name, id });
	}
	/**
	 * removes a course
	 * @param courseId the courseId of the target course to be removed
	 */
	public void removeCourse(int courseId) {
		comCon.makeRequest("course.remove", courseId);
	}
	/**
	 * Creates a course with a course name and a course id
	 * @param name the course name
	 * @param id the course id
	 */
	public void createCourse(String name, int id) {
		comCon.makeRequest("course.create", new Object[] { name, id });
	}
	/**
	 * Adds a course offering 
	 * @param courseId the courseId
	 * @param num the number
	 * @param cap the section cap
	 */
	public void addOffering(int courseId, int num, int cap) {
		comCon.makeRequest("course.addOffering", new Object[] { courseId, num, cap });
	}
	/**
	 * Searches for a course
	 * @param name the course name
	 * @param num the course number
	 * @return the target course
	 */
	public Object search(String name, int num) {
		Course result = (Course) comCon.makeRequest("course.search", new Object[] { name, num });
		return result;
	}
	/**
	 * gets the offerings
	 * @param id the id of the offering
	 * @return returns an array list of the CourseOfferings
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<CourseOffering> getOfferings(Integer id) {
		ArrayList<CourseOffering> result = (ArrayList<CourseOffering>) comCon.makeRequest("course.getOfferings", id);
		return result;
	}
	/**
	 * gets the prerequisites
	 * @param courseId the courseId of the course
	 * @return returns an array list of the prerequisites.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Course> getPrereqs(Integer courseId) {
		ArrayList<Course> result = (ArrayList<Course>) comCon.makeRequest("course.getPreReqs", courseId);
		return result;
	}
}
