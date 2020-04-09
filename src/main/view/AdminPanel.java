package main.view;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import main.controller.PanelController;


@SuppressWarnings("serial")
public class AdminPanel extends Panel {
	
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

	public AdminPanel(PanelController panMan) {
		super(panMan);
		setLayout(new BorderLayout());
		setupPanels();
		setupDisplay();
		setupButtons();
	}

	private void setupButtons() {
		setupBack();
		setupView();
		setupCreateCourse();
		setupRemoveCourse();
		setupCreateOffering();
		setupCreateStudent();
		setupRemoveStudent();
	}

	private void setupRemoveStudent() {
		removeStudent = new JButton("Remove Student");
		removeStudent.addActionListener((ActionEvent e) -> {
			System.out.println("Remove a Student");
		});
		buttons.add(removeStudent);
	}

	private void setupCreateStudent() {
		createStudent = new JButton("Create Student");
		createStudent.addActionListener((ActionEvent e) -> {
			System.out.println("Create a Student");
		});
		buttons.add(createStudent);
	}

	private void setupCreateOffering() {
		createCourseOffering = new JButton("Create Course Offering");
		createCourseOffering.addActionListener((ActionEvent e) -> {
			System.out.println("Create Offering");
		});
		buttons.add(createCourseOffering);
	}

	private void setupRemoveCourse() {
		removeCourse = new JButton("Remove Course");
		removeCourse.addActionListener((ActionEvent e) -> {
			System.out.println("Remove a Course");
		});
		buttons.add(removeCourse);
	}

	private void setupCreateCourse() {
		createCourse = new JButton("Create Course");
		createCourse.addActionListener((ActionEvent e) -> {
			System.out.println("Create a Course");
		});
		buttons.add(createCourse);
	}

	private void setupView() {
		viewAllCourses = new JButton("View All Courses");
		viewAllCourses.addActionListener((ActionEvent e) -> {
			System.out.println("View");
		});
		buttons.add(viewAllCourses);
	}

	private void setupBack() {
		back = new JButton("Back");
		back.addActionListener((ActionEvent e) -> {
			changeView("login");
		});
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
		this.add(buttons, BorderLayout.EAST);
	}

}
