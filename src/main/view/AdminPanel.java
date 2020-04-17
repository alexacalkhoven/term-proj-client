package main.view;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import main.controller.*;
import main.model.Course;
import main.model.CourseOffering;

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
	private JButton back;
	private JButton createCourse;
	private JButton removeCourse;
	private JButton createCourseOffering;
	private JButton createStudent;
	private JButton removeStudent;

	private GridBagConstraints c;
	private JToolBar toolBar;
	private JPanel title, display, buttons;
	private JTextPane courseInfo;
	private JTable table;
	private DefaultTableModel tableModel;

	public AdminPanel(PanelController panMan, CommunicationController comCon) {
		super(panMan);
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		adCon = new AdminFunctController(comCon);
		setLayout(new BorderLayout());
		setupToolbar();
		setupButtons();
		setupPanels();
		setupDisplay();

	}

	@Override
	public void onViewChanged(JFrame frame) {
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
		setupTable();
		setupTextArea();
	}

	private void setupTextArea() {
		courseInfo = new JTextPane();
		courseInfo.setEditable(false);
		JScrollPane jsp = new JScrollPane(courseInfo);
		display.add(jsp);
	}

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

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (table.getSelectedRow() >= 0) {
					String name = table.getValueAt(table.getSelectedRow(), 0).toString();
					int num = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 1).toString());
					updateTextArea((Course) adCon.search(name, num));
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(350, 175));
		display.add(scrollPane);
	}

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

	private void setupPanels() {
		title = new JPanel();
		display = new JPanel(new GridLayout());
		buttons = new JPanel(new GridBagLayout());
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		buttons.add(toolBar, c);
		this.add(title, BorderLayout.NORTH);
		this.add(display, BorderLayout.CENTER);
		this.add(buttons, BorderLayout.EAST);
	}
}
