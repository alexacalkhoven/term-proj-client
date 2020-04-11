package main.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private int id;
	private ArrayList<Registration> registrationList;

	public Student(String name, int id) {
		registrationList = new ArrayList<Registration>();
		setName(name);
		setId(id);
	}

	public void addRegistration(Registration registration) {
		registrationList.add(registration);
	}

	public boolean isRegistered(Course course) {
		for (Registration reg : registrationList) {
			if (reg.getOffering().getCourse().equals(course))
				return true;
		}

		return false;
	}

	public void removeRegistration(Course course) {
		for (Registration reg : registrationList) {
			if (reg.getOffering().getCourse().equals(course)) {
				System.out.println("Dropped course: " + course.getFullName());
				reg.removeRegistration();
				registrationList.remove(reg);
				return;
			}
		}

		System.err.println("Error, student not registered for " + course.getFullName());
	}

	public String getName() {
		return name;
	}

	public void setName(String studentName) {
		this.name = studentName;
	}

	public int getId() {
		return id;
	}

	public void setId(int studentId) {
		this.id = studentId;
	}

	public String toString() {
		String s = "Student name: " + getName() + ", Student ID: " + getId() + "\n";

		s += "Registered courses:\n";

		for (Registration reg : registrationList) {
			s += "\n\n" + reg;
		}

		return s;
	}
}
