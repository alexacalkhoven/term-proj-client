package main.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.controller.CommunicationController;
import main.controller.PanelController;
import main.controller.StudentFunctController;
import main.model.Course;

@SuppressWarnings("serial")
public class StudentPanel extends Panel {
	private StudentFunctController stuCon;
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
	private DefaultTableModel tableModel;

	public StudentPanel(PanelController panMan, CommunicationController comCon) {
		super(panMan);
		stuCon = new StudentFunctController(comCon);
		setLayout(new BorderLayout());
		setupPanels();
		setupDisplay();
		setupButtons();
	}

	private void setupButtons() {
		setupBack();
		setupView();
		setupSearch();
		setupViewReg();
		setupRegForCourse();
		setupDrop();
	}


	private void setupDrop() {
		dropCourse = new JButton("Drop Course");
		dropCourse.addActionListener((ActionEvent e) -> {
			try {
				String [] userIn = getInputs(new String [] {"Course name: ", "Course number: "});
				if (userIn == null) return;
				
				int num = Integer.parseInt(userIn[1]);
				stuCon.dropCourse(userIn[0], num);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(getRootPane(), "Course number must be a number", "Error", JOptionPane.OK_OPTION);
			}
		});
		buttons.add(dropCourse);
	}

	private void setupRegForCourse() {
		registerForCourse = new JButton("Register For Course");
		registerForCourse.addActionListener((ActionEvent e) -> {
			try {
				String [] userIn = getInputs(new String [] { "Course name: ", "Course number: ", "Section number: " });
				if (userIn == null) return;
				
				int number = Integer.parseInt(userIn[1]);
				int section = Integer.parseInt(userIn[2]);
				stuCon.regForCourse(userIn[0], number, section);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(getRootPane(), "Course/section number must be a number", "Error", JOptionPane.OK_OPTION);
			}
		});
		buttons.add(registerForCourse);
	}

	private void setupViewReg() {
		viewRegisteredCourses = new JButton("View Registered Courses");
		viewRegisteredCourses.addActionListener((ActionEvent e) -> {
			ArrayList<Course> result = stuCon.viewReg();
			//Display result
		});
		buttons.add(viewRegisteredCourses);
	}

	private void setupSearch() {
		searchCourseCatalogue = new JButton("Search Course Catalogue");
		searchCourseCatalogue.addActionListener((ActionEvent e) -> {
			String [] userIn = getInputs(new String [] {"Course name: ", "Course number: "});
			if (userIn == null) return;
			
			Course result = stuCon.search(userIn);
			if (result == null) {
				System.out.println("No such course exists :(");
			} else {
				System.out.println(result);
			}
		});
		buttons.add(searchCourseCatalogue);
	}

	private void setupView() {
		viewAllCourses = new JButton("View All Courses");
		viewAllCourses.addActionListener((ActionEvent e) -> {
			ArrayList<Course> results = stuCon.view();
			if (results == null) return;
			
			clearTable();
			
			for (Course c : results) {
				addTableData(c);
			}
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
		String[] columns = { "Course Name", "Course Number" };
		
		tableModel = new DefaultTableModel(null, columns) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(tableModel);
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);
		table.removeEditor();

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(350, 175));
		display.add(scrollPane);
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

	private void clearTable() {
		tableModel.setRowCount(0);
	}
	
	private void addTableData(Course course) {
		Object[] data = new Object[] { course.getName(), course.getNumber() };
		tableModel.addRow(data);
	}
}
