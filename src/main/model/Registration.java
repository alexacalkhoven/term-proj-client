package main.model;

import java.io.Serializable;

/**
 * Holds information about a Registration.
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class Registration implements Serializable {
	private static final long serialVersionUID = 1L;

	private int registrationId;
	private Student student;
	private CourseOffering offering;
	private char grade;

	public int getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(int registrationId) {
		this.registrationId = registrationId;
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

	@Override
	public String toString() {
		return String.format("Course: %s, Section: %s, Grade: %s", offering.getCourse().getFullName(),
				offering.getSecNum(), getGrade());
	}
}
