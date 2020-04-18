package main.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import main.controller.CommunicationController;
import main.controller.PanelController;
import main.controller.StudentFunctController;
import main.model.Course;
import main.model.CourseOffering;
import main.model.Registration;
import main.model.Student;

/**
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class StudentPanel extends Panel {
	private static final long serialVersionUID = 1L;

	private StudentFunctController stuCon;
	private GridBagConstraints c;
	private JToolBar toolBar;
	private JPanel title;
	private JPanel display;
	private JPanel buttons;
	private JTable table;
	private DefaultTableModel tableModel;
	private JTextField field;

	private JTable offeringTable;
	private DefaultTableModel offeringTableModel;

	public StudentPanel(PanelController panMan, CommunicationController comCon) {
		super(panMan);
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		stuCon = new StudentFunctController(comCon);
		setLayout(new BorderLayout());
		setupToolbar();
		setupButtons();
		setupPanels();
		setupCourseTable();
		setupOfferingTable();
		field = new JTextField(2);
	}
	/**
	 * gets student name
	 */
	public String getName() {
		Student s = stuCon.getStudent();
		String string = s.getName();
		return string;
	}
/**
 * When the view is changed, call updateCourses()
 */
	@Override
	public void onViewChanged(JFrame frame) {
		updateCourses();
		Font font1 = new Font("SansSerif", Font.BOLD, 20);
		field.setText("Welcome, " + getName()+ "!");
		add(field, BorderLayout.NORTH);
		field.setHorizontalAlignment(JTextField.CENTER);
		field.setFont(font1);
	}
/**
 * Sets up the toolbar
 */
	private void setupToolbar() {
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setOrientation(SwingConstants.VERTICAL);
	}
/**
 * sets up the buttons
 */
	private void setupButtons() {
		setupBack();
		setupSearch();
		setupRegForCourse();
		setupDrop();
	}
/**
 * updates the tables
 */
	private void updateTables() {
		updateOfferings();
		updateCourses();

		
		
	}
/**
 * updates the offerings
 */

	private void updateOfferings() {
		int row = table.getSelectedRow();

		if (row < 0) {
			clearOfferingTable();
			return;
		}

		int courseId = stuCon.getCourseIdFromRow(row);
		ArrayList<CourseOffering> offeringList = stuCon.getOfferings(courseId);

		if (offeringList == null)
			return;

		clearOfferingTable();

		for (CourseOffering o : offeringList) {
			addOfferingTableData(o);
		}
	}
/**
 * updates the courses
 */
	private void updateCourses() {
		ArrayList<Course> results = stuCon.viewCourses();
		ArrayList<Registration> reg = stuCon.getRegistrationList();

		if (results == null)
			return;

		clearTable();

		for (Course c : results) {
			addTableData(c, checkEnrollment(c, reg));
		}

	}
/**
 * makes a button
 * @param name the name of the button
 * @param listener the listener of the button
 * @return returns a JButton	
 *  */
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
 * sets up the drop button
 */
	private void setupDrop() {
		makeButton("Drop Course", (ActionEvent e) -> {
			try {
				int row = offeringTable.getSelectedRow();

				if (row < 0) {
					JOptionPane.showMessageDialog(getRootPane(), "Please select a row", "Error", JOptionPane.OK_OPTION);
					return;
				}

				int offeringId = stuCon.getOfferingIdFromRow(row);
				stuCon.dropCourse(offeringId);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(getRootPane(), "Course number must be a number", "Error",
						JOptionPane.OK_OPTION);
			}
			updateTables();
		});
	}
/**
 * sets up the register for course button
 */
	private void setupRegForCourse() {
		makeButton("Register For Course", (ActionEvent e) -> {
			int row = offeringTable.getSelectedRow();

			if (row < 0) {
				JOptionPane.showMessageDialog(getRootPane(), "Please select a row", "Error", JOptionPane.OK_OPTION);
				return;
			}

			try {
				int offeringId = stuCon.getOfferingIdFromRow(row);
				stuCon.regForCourse(offeringId);
			} catch (IndexOutOfBoundsException err) {
				JOptionPane.showMessageDialog(getRootPane(), "No offerings available.", "Error", JOptionPane.OK_OPTION);
			}
			
			updateTables();
		});
	}
/**
 * sets up the search button
 */
	private void setupSearch() {
		makeButton("Search Course Catalogue", (ActionEvent e) -> {
			try {
				String[] userIn = getInputs(new String[] { "Course name: ", "Course number: " });
				if (userIn == null)
					return;

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
				JOptionPane.showMessageDialog(getRootPane(), "Course number must be a number", "Error",
						JOptionPane.OK_OPTION);
			}
		});
	}
/**
 * checks for enrollment
 * @param c the course
 * @param regs an arraylist of registrations
 * @return returns -1 if it fails, otherwise it returns the section number
 */
	private int checkEnrollment(Course c, ArrayList<Registration> regs) {
		if (c == null || regs == null)
			return -1;

		for (Registration reg : regs) {
			Course regCourse = reg.getOffering().getCourse();
			if (c.getCourseId() == regCourse.getCourseId()) {
				return reg.getOffering().getSecNum();
			}
		}

		return -1;
	}
/**
 * sets up the offering table
 */
	private void setupOfferingTable() {
		String[] columns = { "Section Number", "Section Capacity", "Current Enrollments" };

		offeringTableModel = new DefaultTableModel(null, columns) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		offeringTable = new JTable(offeringTableModel);
		offeringTable.setColumnSelectionAllowed(false);
		offeringTable.setRowSelectionAllowed(true);
		offeringTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane = new JScrollPane(offeringTable);
		scrollPane.setPreferredSize(new Dimension(350, 175));
		display.add(scrollPane, BorderLayout.EAST);
	}
/**
 * sets up the course table
 */
	private void setupCourseTable() {
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

		// how to make this not occur on select and release?
		table.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
			// do some actions here, for example
			// print first column value from selected row
			int row = table.getSelectedRow();

			if (row >= 0) {
				displaySections(stuCon.getCourseIdFromRow(row));
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(350, 175));
		display.add(scrollPane, BorderLayout.WEST);
	}
/**
 * displays the sections
 * @param courseId the courseId
 */
	private void displaySections(int courseId) {
		ArrayList<CourseOffering> offeringList = stuCon.getOfferings(courseId);

		clearOfferingTable();

		if (offeringList.size() == 0) {
			Object[] data = new Object[] { "No Sections" };
			offeringTableModel.addRow(data);
			return;
		}

		for (int i = 0; i < offeringList.size(); i++) {
			addOfferingTableData(offeringList.get(i));
		}
	}
/**
 * sets up the panels
 */
	private void setupPanels() {
		title = new JPanel();
		display = new JPanel();
		display.setLayout(new BorderLayout());
		// display.setPreferredSize(new Dimension(400, 200));
		buttons = new JPanel(new GridBagLayout());
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		buttons.add(toolBar, c);
		add(title, BorderLayout.NORTH);
		add(display, BorderLayout.CENTER);
		add(buttons, BorderLayout.EAST);
	}
/**
 * clears the table
 */
	private void clearTable() {
		tableModel.setRowCount(0);
	}
/**
 * clears the offering table
 */
	private void clearOfferingTable() {
		offeringTableModel.setRowCount(0);
	}
/**
 * adds data to the table
 * @param course the course
 * @param secNum the section number
 */
	private void addTableData(Course course, int secNum) {
		String enrollCol = "N";
		
		if (secNum >=  0) {
			enrollCol = "Y - section " + secNum;
		}
		
		Object[] data = new Object[] { course.getName(), course.getNumber(), enrollCol };
		tableModel.addRow(data);
	}
/**
 * adds data to the offering table
 * @param o the course offering
 */
	private void addOfferingTableData(CourseOffering o) {
		Object[] data = new Object[] { o.getSecNum(), o.getSecCap(), o.getStudentAmount() };
		offeringTableModel.addRow(data);
	}
}
