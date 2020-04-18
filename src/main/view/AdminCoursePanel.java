package main.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import main.controller.AdminFunctController;
import main.controller.PanelController;
import main.model.Course;
import main.model.CourseOffering;

public class AdminCoursePanel extends Panel {
	private static final long serialVersionUID = 1L;
	
	private AdminFunctController adCon;
	private JTextPane courseInfo;
	private JScrollPane infoScrollPane;
	private JToolBar toolBar;
	private JPanel display;
	private JTable table;
	private DefaultTableModel tableModel;
	
	public AdminCoursePanel(PanelController panMan, AdminFunctController adCon) {
		super(panMan);
		this.adCon = adCon;
		
		setLayout(new BorderLayout());
		display = new JPanel();
		
		setupToolBar();
		setupButtons();
		setupTable();
		setupTextArea();
		
		add(display, BorderLayout.CENTER);
	}
	
	@Override
	public void onViewChanged(JFrame frame) {
		updateCourses();
	}
	/**
	 * sets up the tool bar
	 */
	private void setupToolBar() {
		toolBar = new JToolBar("Buttons");
		toolBar.setFloatable(false);
		toolBar.setOrientation(SwingConstants.VERTICAL);
		add(toolBar, BorderLayout.EAST);
	}
	/**
	 * sets up the buttons
	 */
	private void setupButtons() {
		setupBack();
		setupCreateCourse();
		setupRemoveCourse();
		setupCreateOffering();
	}
	/**
	 * makes a button
	 * @param name the name of the button
	 * @param listener the listener for the button
	 * @return returns a JButton
	 */
	private JButton makeButton(String name, ActionListener listener) {
		JButton btn = new JButton(name);
		Dimension d = new Dimension(175, 25);
		btn.setPreferredSize(d);
		btn.setMinimumSize(d);
		btn.setMaximumSize(d);
		btn.addActionListener(listener);
		toolBar.add(btn);
		
		return btn;
	}
	/**
	 * sets up the back button
	 */
	private void setupBack() {
		makeButton("Back", (ActionEvent e) -> {
			changeView("login");
		});
	}
	/**
	 * sets up the Create Offering button
	 */
	private void setupCreateOffering() {
		makeButton("Create Course Offering", (ActionEvent e) -> {
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
	}
	/**
	 * sets up the Remove Course button
	 */
	private void setupRemoveCourse() {
		makeButton("Remove Course", (ActionEvent e) -> {
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
			courseInfo.setText("Course deleted.");
		});
	}
	/**
	 * sets up the CreateCourse button
	 */
	private void setupCreateCourse() {
		makeButton("Create Course", (ActionEvent e) -> {
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
	}
	/**
	 * updates the displayed courses
	 */
	private void updateCourses() {
		ArrayList<Course> results = adCon.viewCourses();

		if (results == null)
			return;

		clearTable();

		for (Course c : results) {
			addTableData(c);
		}
	}
	/**
	 * clears the table
	 */
	private void clearTable() {
		tableModel.setRowCount(0);
	}
	/**
	 * adds data to the table
	 * @param course the course to be added
	 */
	private void addTableData(Course course) {
		Object[] data = new Object[] { course.getName(), course.getNumber() };
		tableModel.addRow(data);
	}
	/**
	 * sets up the text area
	 */
	private void setupTextArea() {
		courseInfo = new JTextPane();
		infoScrollPane = new JScrollPane(courseInfo);
		infoScrollPane.setPreferredSize(new Dimension(350, 175));
		courseInfo.setEditable(false);
		display.add(infoScrollPane);
	}
	/**
	 * sets up the table
	 */
	private void setupTable() {
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

		table.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
			if (table.getSelectedRow() >= 0) {
				String name = table.getValueAt(table.getSelectedRow(), 0).toString();
				int num = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 1).toString());
				updateTextArea((Course) adCon.search(name, num));
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(350, 175));
		display.add(scrollPane);
	}
	/**
	 * updates the text area
	 * @param c the course
	 */
	private void updateTextArea(Course c) {
		String info = "Course Info: \n\n";
		info += "Name: " + c.getFullName() + "\n";
		info += "Course ID: " + c.getCourseId() + "\n";

		info += "\n";

		info += "Course Offerings: \n";
		ArrayList<CourseOffering> offerings = adCon.getOfferings(c.getCourseId());
		if (offerings.size() == 0) {
			info += "None.\n";
		} else {
			for (int i = 0; i < offerings.size(); i++) {
				info += offerings.get(i).toString() + "\n";
			}
		}

		info += "\n";

		info += "Pre-Requisites: \n";
		ArrayList<Course> preReqs = adCon.getPrereqs(c.getCourseId());
		if (preReqs.size() == 0) {
			info += "None.\n";
		} else {
			for (int i = 0; i < preReqs.size(); i++) {
				info += preReqs.get(i).toString();
			}
		}

		courseInfo.setText(info);
	}
}
