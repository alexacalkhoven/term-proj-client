package main.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CourseOffering implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int secNum;
	private int secCap;
	private int studentAmount;
	private Course course;
	private ArrayList<Registration> registrationList;

	public CourseOffering(int secNum, int secCap) {
		registrationList = new ArrayList<Registration>();
		setStudentAmount(0);
		setSecNum(secNum);
		setSecCap(secCap);
	}

	// ;)))))
	// so
	// LONG
	// ooeoehoeo :^)

	public void addRegistration(Registration registration) {
		registrationList.add(registration);
		setStudentAmount(getStudentAmount() + 1);
	}

	public void removeRegistration(Registration registration) {
		registrationList.remove(registration);
		setStudentAmount(getStudentAmount() - 1);
	}

	public int getSecNum() {
		return secNum;
	}

	public void setSecNum(int secNumber) {
		this.secNum = secNumber;
	}

	public int getSecCap() {
		return secCap;
	}

	public boolean isFull() {
		return studentAmount >= secCap;
	}

	public void setSecCap(int secCap) {
		this.secCap = secCap;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Course getCourse() {
		return this.course;
	}

	public String toString() {
		return "Section: " + getSecNum() + ", Spots: " + getStudentAmount() + " / " + getSecCap();
	}

	public int getStudentAmount() {
		return studentAmount;
	}

	public void setStudentAmount(int studentAmount) {
		this.studentAmount = studentAmount;
	}
}
