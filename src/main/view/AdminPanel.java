package main.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import main.controller.AdminFunctController;
import main.controller.CommunicationController;
import main.controller.PanelController;

/**
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

	public AdminPanel(PanelController panMan, CommunicationController comCon) {
		super(panMan);
		adCon = new AdminFunctController(comCon);
		
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new BorderLayout());
		setupPanels();
	}

	@Override
	public void onViewChanged(JFrame frame) {
		coursePanel.onViewChanged(frame);
		studentPanel.onViewChanged(frame);
	}

	private void setupPanels() {
		tabs = new JTabbedPane();
		
		coursePanel = new AdminCoursePanel(panMan, adCon);
		tabs.addTab("Courses", coursePanel);
		
		studentPanel = new AdminStudentPanel(panMan, adCon);
		tabs.addTab("Students", studentPanel);
		
		add(tabs, BorderLayout.CENTER);
	}
}
