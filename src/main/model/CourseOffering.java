package main.model;

import java.io.Serializable;

/**
 * 
 * Manages and holds information associated with a CourseOffering.
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class CourseOffering implements Serializable {
	private static final long serialVersionUID = 1L;

	private int offeringId;
	private int secNum;
	private int secCap;
	private int studentAmount;
	private Course course;

	/**
	 * Creates a new course offering with given section number and section capacity
	 * 
	 * @param secNum Section number
	 * @param secCap Section capacity
	 */
	public CourseOffering(int secNum, int secCap) {
		setStudentAmount(0);
		setSecNum(secNum);
		setSecCap(secCap);
	}

	public int getOfferingId() {
		return offeringId;
	}

	public void setOfferingId(int offeringId) {
		this.offeringId = offeringId;
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

	public int getStudentAmount() {
		return studentAmount;
	}

	public void setStudentAmount(int studentAmount) {
		this.studentAmount = studentAmount;
	}

	@Override
	public String toString() {
		return String.format("Offering ID: %d, Section: %d, Spots: %d/%d", getOfferingId(), getSecNum(), getStudentAmount(),
				getSecCap());
	}
}
