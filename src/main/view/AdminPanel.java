package main.view;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import main.controller.*;
import main.model.Course;

/**
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class AdminPanel extends Panel {
	private static final long serialVersionUID = 1L;
	private static final int BUTTON_FIELD_SIZE = 32;
	
	private AdminFunctController adCon;
	private JButton back;
	private JButton createCourse;
	private JButton removeCourse;
	private JButton createCourseOffering;
	private JButton createStudent;
	private JButton removeStudent;
	
	private GridBagConstraints c;
	private JToolBar toolBar;
	private JPanel title, display, buttons;
	private JTable table;
	private DefaultTableModel tableModel;

	public AdminPanel(PanelController panMan, CommunicationController comCon) {
		super(panMan);
		adCon = new AdminFunctController(comCon);
		setLayout(new BorderLayout());
		setupToolbar();
		setupButtons();
		setupPanels();
		setupDisplay();
		updateCourses();
	}

	private void setupToolbar() {
		toolBar = new JToolBar("Buttons");
		toolBar.setFloatable(false);
		toolBar.setOrientation(SwingConstants.VERTICAL);
	}
	
	private void setupButtons() {
		setupBack();
		setupCreateCourse();
		setupRemoveCourse();
		setupCreateOffering();
		setupCreateStudent();
		setupRemoveStudent();
	}
	
	private void setButtonSize(JButton b) {
		Dimension d = new Dimension(175, 25);
		b.setPreferredSize(d);
		b.setMinimumSize(d);
		b.setMaximumSize(d);
	}
	
	private void setupRemoveStudent() {
		removeStudent = new JButton("Remove Student");
		setButtonSize(removeStudent);
		
		removeStudent.addActionListener((ActionEvent e) -> {
			try {
				String[] inputs = getInputs(new String[] { "ID:" });
				if (inputs == null)
					return;

				int id = Integer.parseInt(inputs[0]);
				adCon.removeStudent(id);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(getRootPane(), "ID must be a number", "Error", JOptionPane.OK_OPTION);
			}
		});
		toolBar.add(removeStudent);
	}

	private void setupCreateStudent() {
		createStudent = new JButton("Create Student");
		setButtonSize(createStudent);
		
		createStudent.addActionListener((ActionEvent e) -> {
			try {
				String[] inputs = getInputs(new String[] { "Name:", "ID:" });
				if (inputs == null)
					return;

				int id = Integer.parseInt(inputs[1]);
				adCon.createStudent(inputs[0], id);
			} catch (NumberFormatException ex) {
				JOptionPane.showConfirmDialog(getRootPane(), "Error, must enter a number");
			}
		});
		toolBar.add(createStudent);
	}

	private void setupCreateOffering() {
		createCourseOffering = new JButton("Create Course Offering");
		setButtonSize(createCourseOffering);
		
		createCourseOffering.addActionListener((ActionEvent e) -> {
			try {
				int row = table.getSelectedRow();
				
				if (row < 0) {
					JOptionPane.showMessageDialog(getRootPane(), "Please select a row", "Error", JOptionPane.OK_OPTION);
					return;
				}
				
				String[] inputs = getInputs(new String[] { "Section Number:", "Section Capacity:" });
				
				if (inputs == null)
					return;

				int courseId = adCon.getCourseIdFromRow(row);
				int num = Integer.parseInt(inputs[0]);
				int cap = Integer.parseInt(inputs[1]);
				adCon.addOffering(courseId, num, cap);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(getRootPane(), "Course number must be a number", "Error",
						JOptionPane.OK_OPTION);
			}
		});
		toolBar.add(createCourseOffering);
	}

	private void setupRemoveCourse() {
		removeCourse = new JButton("Remove Course");
		setButtonSize(removeCourse);
		
		removeCourse.addActionListener((ActionEvent e) -> {
			try {
				int row = table.getSelectedRow();
				
				if (row < 0) {
					JOptionPane.showMessageDialog(getRootPane(), "Please select a row", "Error", JOptionPane.OK_OPTION);
					return;
				}
				
				int courseId = adCon.getCourseIdFromRow(row);
				adCon.removeCourse(courseId);
				updateCourses();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(getRootPane(), "Course number must be a number", "Error",
						JOptionPane.OK_OPTION);
			}
		});
		toolBar.add(removeCourse);
	}

	private void setupCreateCourse() {
		createCourse = new JButton("Create Course");
		setButtonSize(createCourse);
		
		createCourse.addActionListener((ActionEvent e) -> {
			try {
				String[] inputs = getInputs(new String[] { "Name:", "Number:" });
				if (inputs == null)
					return;

				int num = Integer.parseInt(inputs[1]);
				adCon.createCourse(inputs[0], num);
				updateCourses();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(getRootPane(), "Course number must be a number", "Error",
						JOptionPane.OK_OPTION);
			}
		});
		toolBar.add(createCourse);
	}
	
	private void updateCourses() {
		ArrayList<Course> results = adCon.viewCourses();
		
		if (results == null)
			return;

		clearTable();

		for (Course c : results) {
			addTableData(c);
		}
	}

	private void clearTable() {
		tableModel.setRowCount(0);
	}

	private void addTableData(Course course) {
		Object[] data = new Object[] { course.getName(), course.getNumber() };
		tableModel.addRow(data);
	}

	private void setupBack() {
		back = new JButton("Back");
		setButtonSize(back);
		
		back.addActionListener((ActionEvent e) -> {
			changeView("login");
		});
		toolBar.add(back);
	}

	private void setupDisplay() {
		String[] columns = { "Course Name", "Course Number" };

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
		buttons = new JPanel(new GridBagLayout());
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		buttons.add(toolBar, c);
		this.add(title, BorderLayout.NORTH);
		this.add(display, BorderLayout.CENTER);
		this.add(buttons, BorderLayout.EAST);
	}
}
