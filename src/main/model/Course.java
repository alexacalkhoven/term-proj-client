package main.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private int number;
	private ArrayList<Course> preReq;
	private ArrayList<CourseOffering> offeringList;

	public CourseOffering getCourseOffering(int section) {
		for (CourseOffering o : offeringList) {
			if (o.getSecNum() == section)
				return o;
		}

		return null;
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

	public void setNumber(int courseNum) {
		this.number = courseNum;
	}

	public String getFullName() {
		return getName() + " " + getNumber();
	}

	public String toString() {
		String s = "Course: " + getName() + " " + getNumber() + "\nAll course sections:\n";

		for (CourseOffering c : offeringList) {
			s += c + "\n";
		}

		return s;
	}
}
