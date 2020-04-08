package main.view;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LoginPanel extends JPanel {

	private JButton loginStudent;
	private JButton loginAdmin;
	private JButton quit;
	
	public LoginPanel(){
		setupButtons();
		addButtons();
	}

	private void addButtons() {
		this.add(loginStudent);
		this.add(loginAdmin);
		this.add(quit);
	}

	private void setupButtons() {
		setupStudent();
		setupAdmin();
		setupQuit();
	}

	private void setupQuit() {
		quit = new JButton("Quit");
		quit.addActionListener((ActionEvent e) -> {
			System.out.println("Quit");
		});
	}

	private void setupAdmin() {
		loginAdmin = new JButton("Admin");
		loginAdmin.addActionListener((ActionEvent e) -> {
			System.out.println("Admin");
		});
	}

	private void setupStudent() {
		loginStudent = new JButton("Student");
		loginStudent.addActionListener((ActionEvent e) -> {
			System.out.println("Student");
		});
	}
}
