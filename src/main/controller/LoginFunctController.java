package main.controller;

import javax.swing.JOptionPane;

import main.model.Student;

/**
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class LoginFunctController {
	private CommunicationController comCon;
	
	public LoginFunctController(CommunicationController comCon) {
		this.comCon = comCon;
	}
	
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
