package main.view;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import main.controller.*;


@SuppressWarnings("serial")
public class AdminPanel extends Panel {
	private AdminFunctController adCon;
	private JButton back;
	private JButton viewAllCourses;
	private JButton createCourse;
	private JButton	removeCourse;
	private JButton createCourseOffering;
	private JButton createStudent;
	private JButton removeStudent;

	private JPanel title, display, buttons;
	private JTable table;

	public AdminPanel(PanelController panMan, CommunicationController comCon) {
		super(panMan);
		adCon = new AdminFunctController(comCon);
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
			try {
				String[] inputs = getInputs(new String[] { "ID:" });
				if (inputs == null) return;
				
				int id = Integer.parseInt(inputs[0]);
				adCon.removeStudent(id);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(getRootPane(), "ID must be a number", "Error", JOptionPane.OK_OPTION);
			}
		});
		buttons.add(removeStudent);
	}

	private void setupCreateStudent() {
		createStudent = new JButton("Create Student");
		createStudent.addActionListener((ActionEvent e) -> {
			try {
				String[] inputs = getInputs(new String[] { "Name:", "ID:" });
				if (inputs == null) return;
				
				int id = Integer.parseInt(inputs[1]);
				adCon.createStudent(inputs[0], id);
			} catch (NumberFormatException ex) {
				JOptionPane.showConfirmDialog(getRootPane(), "Error, must enter a number");
			}
		});
		buttons.add(createStudent);
	}

	private void setupCreateOffering() {
		createCourseOffering = new JButton("Create Course Offering");
		createCourseOffering.addActionListener((ActionEvent e) -> {
			try {
				String [] inputs1 = getInputs(new String[] { "Course Name: ", "Course Number" });
				if (inputs1 == null) return;
				String [] inputs2 = getInputs(new String[] { "Section Number:", "Section Capacity:" });
				if (inputs2 == null) return;
				
				int num = Integer.parseInt(inputs2[0]);
				int cap = Integer.parseInt(inputs2[1]);
				int courseNum = Integer.parseInt(inputs1[1]);
				adCon.addOffering(num, cap, inputs1[0], courseNum);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(getRootPane(), "Course number must be a number", "Error", JOptionPane.OK_OPTION);
			}
		});
		buttons.add(createCourseOffering);
	}

	private void setupRemoveCourse() {
		removeCourse = new JButton("Remove Course");
		removeCourse.addActionListener((ActionEvent e) -> {
			try {
				String inputs[] = getInputs(new String[] { "Name:", "Number:" });
				if (inputs == null) return;
				
				int num = Integer.parseInt(inputs[1]);
				adCon.removeCourse(inputs[0], num);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(getRootPane(), "Course number must be a number", "Error", JOptionPane.OK_OPTION);
			}
		});
		buttons.add(removeCourse);
	}

	private void setupCreateCourse() {
		createCourse = new JButton("Create Course");
		createCourse.addActionListener((ActionEvent e) -> {
			try {
				String[] inputs = getInputs(new String[] { "Name:", "Number:" });
				if (inputs == null) return;
				
				int num = Integer.parseInt(inputs[1]);
				adCon.createCourse(inputs[0], num);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(getRootPane(), "Course number must be a number", "Error", JOptionPane.OK_OPTION);
			}
		});
		buttons.add(createCourse);
	}

	private void setupView() {
		viewAllCourses = new JButton("View All Courses");
		viewAllCourses.addActionListener((ActionEvent e) -> {
			adCon.viewCourses();
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
		this.add(buttons, BorderLayout.SOUTH);
	}
}
