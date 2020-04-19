package main.controller;

import javax.swing.JOptionPane;

import main.model.Student;

/**
 * The login function controller.
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class LoginFunctController {
	private CommunicationController comCon;
	
	/**
	 * The constructor for LoginFunctController
	 * @param comCon the communication controller
	 */
	public LoginFunctController(CommunicationController comCon) {
		this.comCon = comCon;
	}
	
	/**
	 * Logs into a student account using a student id
	 * @param id the students id
	 * @return returns true if the student id matches, returns false if the student id does not exist
	 */
	public boolean loginStudent(int id) {
		Student student = (Student)comCon.makeRequest("student.login", id);
		
		if (student == null) {
			JOptionPane.showMessageDialog(null, "That student does not exist", "Error", JOptionPane.OK_OPTION);
			return false;
		}
		
		System.out.println("Logged in as: " + student.getId() + " - " + student.getName());
		return true;
	}
}
