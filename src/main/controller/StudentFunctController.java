package main.controller;

import java.util.ArrayList;

import main.model.Course;
import main.model.CourseOffering;
import main.model.Registration;
import main.model.Student;

/**
 * Controls the button functions on the student side of the UI.
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

	/**
	 * gets the courseId from the selected row
	 * 
	 * @param row the selected row
	 * @return
	 */
	public int getCourseIdFromRow(int row) {
		return courseList.get(row).getCourseId();
	}

	public StudentFunctController(CommunicationController comCon) {
		this.comCon = comCon;
	}

	public Student getStudent() {
		Student result = (Student) comCon.makeRequest("student.getStudent");
		return result;
	}

	/**
	 * Searches for a course
	 * 
	 * @param name the name of the course
	 * @param num  the number of the course
	 * @return returns the target course
	 */
	public Course search(String name, int num) {
		Course result = (Course) comCon.makeRequest("course.search", new Object[] { name, num });
		return result;
	}

	/**
	 * gets the offerings of a course
	 * 
	 * @param id the id of the course
	 * @return returns an array list of the offerings.
	 */
	public ArrayList<CourseOffering> getOfferings(int id) {
		ArrayList<CourseOffering> result = (ArrayList<CourseOffering>) comCon.makeRequest("course.getOfferings", id);
		offeringList = result;
		return result;
	}

	/**
	 * views all of the courses
	 * 
	 * @return returns an Arraylist of all the courses
	 */
	public ArrayList<Course> view() {
		ArrayList<Course> result = (ArrayList<Course>) comCon.makeRequest("course.get");
		courseList = result;
		return result;
	}

	/**
	 * gets the registration list
	 * 
	 * @return returns an array list of the registrations for the student
	 */
	public ArrayList<Registration> getRegistrationList() {
		return (ArrayList<Registration>) comCon.makeRequest("student.regList");
	}

	/**
	 * registers the student for a CourseOffering
	 * 
	 * @param offeringId the offeringId
	 */
	public void regForCourse(Integer offeringId) {
		comCon.makeRequest("student.addRegCourse", offeringId);
	}

	/**
	 * drops a courseOffering for a student.
	 * 
	 * @param number the offeringId
	 */
	public void dropCourse(Integer number) {
		comCon.makeRequest("student.dropCourse", number);
	}

	/**
	 * Gets the offeringId from the selected row.
	 * 
	 * @param row the selected row
	 * @return returns the offeringId from the selected row
	 * @throws IndexOutOfBoundsException Thrown when no row is selected or there are
	 *                                   no offerings
	 */
	public int getOfferingIdFromRow(int row) throws IndexOutOfBoundsException {
		if (offeringList.size() == 0) {
			throw new IndexOutOfBoundsException("No Offerings!");
		}
		return offeringList.get(row).getOfferingId();
	}
}
