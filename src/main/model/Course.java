package main.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Manages all information associated with a Course.
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class Course implements Serializable {
	private static final long serialVersionUID = 1L;

	private int courseId;
	private String name;
	private int number;
	private ArrayList<Course> preReq;

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getCourseId() {
		return courseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String courseName) {
		this.name = courseName;
	}

	public int getNumber() {
		return number;
	}

	public ArrayList<Course> getPreReq() {
		return preReq;
	}

	public void setNumber(int courseNum) {
		this.number = courseNum;
	}

	public String getFullName() {
		return getName() + " " + getNumber();
	}

	/**
	 * Special toString function - creates a String for a course including offerings and prereqs.
	 * 
	 * @param offeringListForCourse Offerings for the course.
	 * @param preReqList Pre-reqs for the course.
	 * @return Stringified version of the course
	 */
	public String toString(ArrayList<CourseOffering> offeringListForCourse, ArrayList<Course> preReqList) {
		String s = String.format("Course: %s\n\nAll course sections:", getFullName());

		s += "\n";

		if (offeringListForCourse.size() == 0) {
			s += "No offerings.\n";
		} else {
			for (CourseOffering c : offeringListForCourse) {
				s += c + "\n";
			}
		}

		s += "\nAll pre-requisites:";

		s += "\n";

		if (preReqList.size() == 0) {
			s += "No pre-reqs.\n";
		} else {
			for (Course c : preReqList) {
				s += c.getFullName() + "\n";
			}
		}

		return s;
	}
}
