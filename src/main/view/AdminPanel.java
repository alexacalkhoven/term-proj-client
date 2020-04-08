package main.view;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class AdminPanel extends JPanel {
	private JButton back;
	private JButton viewAllCourses;
	private JButton createCourse;
	private JButton	removeCourse;
	private JButton createCourseOffering;
	private JButton createStudent;
	private JButton removeStudent;

	private JPanel title;
	private JPanel display;
	private JPanel buttons;
	private JTable table;

	public AdminPanel() {
		setupPanels();
		setupDisplay();
		setupButtons();

	}

	private void setupButtons() {
		back = new JButton("Back");
		viewAllCourses = new JButton("View All Courses");
		createCourse = new JButton("Create Course");
		removeCourse = new JButton("Remove Course");
		createCourseOffering = new JButton("Create Course Offering");
		createStudent = new JButton("Create Student");
		removeStudent = new JButton("remove Student");

		//back.addActionListener();
		//viewAllCourses.addActionListener();
		//createCourse.addActionListener();
		//removeCourse.addActionListener();
		//createCourseOffering.addActionListener();
		//createStudent.addActionListener();
		//removeStudent.addActionListener();
		

		buttons.add(back);
		buttons.add(viewAllCourses);
		buttons.add(createCourse);
		buttons.add(removeCourse);
		buttons.add(createCourseOffering);
		buttons.add(createStudent);
		buttons.add(removeStudent);
	}

	private void setupDisplay() {
		table = new JTable();
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(false);

		// table.setColumnModel

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(350, 175));
		display.add(scrollPane);

		// table.setTableHeader();

	}

	private void setupPanels() {
		title = new JPanel();
		display = new JPanel();
		display.setPreferredSize(new Dimension(400, 200));
		buttons = new JPanel();
		this.add(title, BorderLayout.NORTH);
		this.add(display, BorderLayout.CENTER);
		this.add(buttons, BorderLayout.SOUTH);

	}

}
