package main.model;

import java.io.Serializable;

/**
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class Registration implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Student student;
	private CourseOffering offering;
	private char grade;

	public void addRegistration() {
		if (offering == null || student == null) {
			System.err.println("Error, cannot register without setting student or course offering");
			return;
		}

		if (offering.isFull()) {
			System.err.println("Error, section cap already reached");
			return;
		}

		if (student.isRegistered(offering.getCourse())) {
			System.err.println("Error, student already registered for an offering in this course");
			return;
		}

		student.addRegistration(this);
		offering.addRegistration(this);

		System.out.println(
				"Registered for " + offering.getCourse().getFullName() + " in section " + offering.getSecNum());
	}

	public void removeRegistration() {
		offering.removeRegistration(this);
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public CourseOffering getOffering() {
		return offering;
	}

	public void setOffering(CourseOffering offering) {
		this.offering = offering;
	}

	public char getGrade() {
		return grade;
	}

	public void setGrade(char grade) {
		this.grade = grade;
	}

	public String toString() {
		String s = "Course: " + offering.getCourse().getFullName() + "\n";
		s += "Section: " + offering.getSecNum() + "\n";
		s += "Grade: " + getGrade();

		return s;
	}
}
