package main.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.controller.PanelController;

@SuppressWarnings("serial")
public class LoginPanel extends Panel {

	private static final String TITLE_TEXT = "Welcome! What would you like to log in as?";
	private JButton loginStudent;
	private JButton loginAdmin;
	private JButton quit;
	private JPanel title;
	private JPanel buttons;
	
	public LoginPanel(PanelController panMan){
		super(panMan);
		setLayout(new BorderLayout());
		setupButtons();
		setupPanels();
	}

	private void setupButtons() {
		setupStudent();
		setupAdmin();
		setupQuit();
	}
	
	private void setupPanels() {
		setupTitlePanel();
		setupButtonPanel();
	}

	private void setupButtonPanel() {
		buttons = new JPanel();
		addButtons();
		this.add(buttons, BorderLayout.SOUTH);
	}

	//How to make this actual large header text?
	private void setupTitlePanel() {
		title = new JPanel();
		JTextField titleText = new JTextField(TITLE_TEXT);
		titleText.setEditable(false);
		title.add(titleText);
		this.add(title, BorderLayout.NORTH);
	}

	private void addButtons() {
		buttons.add(loginStudent);
		buttons.add(loginAdmin);
		buttons.add(quit);
	}

	private void setupQuit() {
		quit = new JButton("Quit");
		quit.addActionListener((ActionEvent e) -> {
			System.exit(0);
		});
	}

	private void setupAdmin() {
		loginAdmin = new JButton("Admin");
		loginAdmin.addActionListener((ActionEvent e) -> {
			String[] stuff = {"ID: "};
			String[] inputs = getInputs(stuff);
			changeView("admin");
		});
	}

	private void setupStudent() {
		loginStudent = new JButton("Student");
		loginStudent.addActionListener((ActionEvent e) -> {
			changeView("student");
		});
	}

}
