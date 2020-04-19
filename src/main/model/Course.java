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
	private ArrayList<CourseOffering> offeringList;

	/**
	 * Gets a course offering for the course.
	 * 
	 * @param section Section number.
	 * @return CourseOffering found.
	 */
	public CourseOffering getCourseOffering(int section) {
		for (CourseOffering o : offeringList) {
			if (o.getSecNum() == section)
				return o;
		}

		return null;
	}

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

	public ArrayList<CourseOffering> getOfferingList() {
		return offeringList;
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

	@Override
	public String toString() {
		String s = String.format("Course: %s\nAll course sections:", getFullName());

		for (CourseOffering c : offeringList) {
			s += c + "\n";
		}

		return s;
	}
}
