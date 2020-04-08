package main.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class StudentPanel extends JPanel {
	private JButton viewAllCourses;
	private JButton searchCourseCatalogue;
	private JButton viewRegisteredCourses;
	private JButton registerForCourse;
	private JButton dropCourse;
	private JButton back;

	private JPanel title;
	private JPanel display;
	private JPanel buttons;
	private JTable table;

	public StudentPanel() {
		setupPanels();
		setupDisplay();
		setupButtons();
	}

	private void setupButtons() {
		viewAllCourses = new JButton("View All Courses");
		searchCourseCatalogue = new JButton("Search Course Catalogue");
		viewRegisteredCourses = new JButton("View Registered Courses");
		registerForCourse = new JButton("Register For Course");
		dropCourse = new JButton("Drop Course");
		back = new JButton("Back");

		// viewAllCourses.addActionListener();
		// searchCourseCatalogue.addActionListener();
		// viewReigseredCourses.addActionListener();
		// registerForCourse.addActionListener();
		// dropCourse.addActionListener();
		// back.addActionListener();

		buttons.add(viewAllCourses);
		buttons.add(searchCourseCatalogue);
		buttons.add(viewRegisteredCourses);
		buttons.add(registerForCourse);
		buttons.add(dropCourse);
		buttons.add(back);
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
