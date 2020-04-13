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
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import main.controller.CommunicationController;
import main.controller.PanelController;
import main.controller.StudentFunctController;
import main.model.Course;
import main.model.Registration;

public class StudentPanel extends Panel {
	private static final long serialVersionUID = 1L;
	
	private StudentFunctController stuCon;
	private JButton viewAllCourses;
	private JButton searchCourseCatalogue;
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
		//setupViewReg();
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

	private void setupSearch() {
		searchCourseCatalogue = new JButton("Search Course Catalogue");
		searchCourseCatalogue.addActionListener((ActionEvent e) -> {
			try {
				String [] userIn = getInputs(new String [] { "Course name: ", "Course number: " });
				if (userIn == null) return;

				int num = Integer.parseInt(userIn[1]);
				
				Course result = stuCon.search(userIn[0], num);
				
				String resultText;
				
				if (result == null) {
					resultText = "Course not found.";
				} else {
					resultText = result.toString();
				}

				JOptionPane.showMessageDialog(getRootPane(), resultText, resultText, JOptionPane.PLAIN_MESSAGE);
				
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(getRootPane(), "Course number must be a number", "Error", JOptionPane.OK_OPTION);
			}
		});
		buttons.add(searchCourseCatalogue);
	}

	private void setupView() {
		viewAllCourses = new JButton("View All Courses");
		viewAllCourses.addActionListener((ActionEvent e) -> {
			ArrayList<Course> courses = stuCon.view();
			ArrayList<Registration> regs = stuCon.getRegistrationList();
			
			if (courses == null) return;
			
			clearTable();
			
			for (Course c : courses) {
				addTableData(c, checkEnrollment(c, regs));
			}
		});
		buttons.add(viewAllCourses);
	}

	private char checkEnrollment(Course c, ArrayList<Registration> regs) {
		if (c == null || regs == null) return 'N';
		
		for (Registration reg : regs) {
			Course regCourse = reg.getOffering().getCourse();
			if (regCourse.getName().equalsIgnoreCase(c.getName()) && regCourse.getNumber() == regCourse.getNumber()) {
				return 'Y';
			}
		}
		
		return 'N';
	}

	private void setupBack() {
		back = new JButton("Back");
		back.addActionListener((ActionEvent e) -> {
			changeView("login");
		});
		buttons.add(back);
	}

	private void setupDisplay() {
		String[] columns = { "Course Name", "Course Number", "Enrolled?" };
		
		tableModel = new DefaultTableModel(null, columns) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(tableModel);
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
	
	private void addTableData(Course course, char enrolled) {
        Object[] data = new Object[] { course.getName(), course.getNumber(), enrolled };
		tableModel.addRow(data);
	}
}
