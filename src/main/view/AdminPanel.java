package main.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.controller.AdminFunctController;
import main.controller.CommunicationController;
import main.controller.PanelController;

/**
 * Manages the admin view.
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class AdminPanel extends Panel {
	private static final long serialVersionUID = 1L;

	private AdminFunctController adCon;
	private AdminCoursePanel coursePanel;
	private AdminStudentPanel studentPanel;
	private JTabbedPane tabs;
	private JPanel headerPanel;
	private JTextField titleText;

	public AdminPanel(PanelController panMan, CommunicationController comCon) {
		super(panMan);
		adCon = new AdminFunctController(comCon);
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new BorderLayout());
		setupPanels();
		setupHeader();
	}

	/**
	 * when the view is changed, the coursePanel and studentPanel are updated
	 */
	@Override
	public void onViewChanged(JFrame frame) {
		coursePanel.onViewChanged(frame);
		studentPanel.onViewChanged(frame);
	}

	/**
	 * sets up the panels
	 */
	private void setupPanels() {
		tabs = new JTabbedPane();

		coursePanel = new AdminCoursePanel(panMan, adCon);
		tabs.addTab("Courses", coursePanel);

		studentPanel = new AdminStudentPanel(panMan, adCon);
		tabs.addTab("Students", studentPanel);

		add(tabs, BorderLayout.CENTER);
	}

	/**
	 * Sets up the welcome header.
	 */
	private void setupHeader() {
		headerPanel = new JPanel(new GridLayout(1, 0));
		headerPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

		titleText = new JTextField("Welcome, Admin!");
		titleText.setEditable(false);
		headerPanel.add(titleText);

		add(headerPanel, BorderLayout.NORTH);
	}
}
