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
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import main.controller.CommunicationController;
import main.controller.PanelController;
import main.controller.StudentFunctController;
import main.model.Course;
import main.model.CourseOffering;
import main.model.Registration;

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
	private JButton viewAllCourses;
	private JButton searchCourseCatalogue;
	private JButton registerForCourse;
	private JButton dropCourse;
	private JButton back;

	private JToolBar toolBar;
	private JPanel title;
	private JPanel display;
	private JPanel buttons;
	private JTable table;
	private DefaultTableModel tableModel;

	private JTable offeringTable;
	private DefaultTableModel offeringTableModel;

	public StudentPanel(PanelController panMan, CommunicationController comCon) {
		super(panMan);
		stuCon = new StudentFunctController(comCon);
		setLayout(new BorderLayout());
		setupToolbar();
		setupButtons();
		setupPanels();
		setupDisplay();
	}

	private void setupToolbar() {
		toolBar = new JToolBar("Buttons");
		toolBar.setFloatable(false);
		toolBar.setOrientation(SwingConstants.VERTICAL);
	}

	private void setupButtons() {
		setupBack();
		setupView();
		setupSearch();
		// setupViewReg();
		setupRegForCourse();
		setupDrop();
	}
	private void updateCourses() {
		ArrayList<Course> results = stuCon.viewCourses();
		
		if (results == null)
			return;

		clearTable();

		for (Course c : results) {
			addTableData(c);
		}
	}
	private void addTableData(Course course) {
		Object[] data = new Object[] { course.getName(), course.getNumber() };
		tableModel.addRow(data);
	}

	private void setupDrop() {
		dropCourse = new JButton("Drop Course");
		dropCourse.addActionListener((ActionEvent e) -> {
			try {
				int row = table.getSelectedRow();
				
				if (row < 0) {
					JOptionPane.showMessageDialog(getRootPane(), "Please select a row", "Error", JOptionPane.OK_OPTION);
					return;
				}
				
				int courseId = stuCon.getCourseIdFromRow(row);
				stuCon.dropCourse(courseId);
				updateCourses();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(getRootPane(), "Course number must be a number", "Error",
						JOptionPane.OK_OPTION);
			}
		});
		toolBar.add(dropCourse);
	}

	private void setupRegForCourse() {
		registerForCourse = new JButton("Register For Course");
		registerForCourse.addActionListener((ActionEvent e) -> {
			try {
				String[] userIn = getInputs(new String[] { "Course name: ", "Course number: ", "Section number: " });
				if (userIn == null)
					return;

				int number = Integer.parseInt(userIn[1]);
				int section = Integer.parseInt(userIn[2]);
				stuCon.regForCourse(userIn[0], number, section);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(getRootPane(), "Course/section number must be a number", "Error",
						JOptionPane.OK_OPTION);
			}
		});
		toolBar.add(registerForCourse);
	}

	private void setupSearch() {
		searchCourseCatalogue = new JButton("Search Course Catalogue");
		searchCourseCatalogue.addActionListener((ActionEvent e) -> {
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
		toolBar.add(searchCourseCatalogue);
	}

	private void setupView() {
		viewAllCourses = new JButton("View All Courses");
		viewAllCourses.addActionListener((ActionEvent e) -> {
			ArrayList<Course> courses = stuCon.view();
			ArrayList<Registration> regs = stuCon.getRegistrationList();

			if (courses == null)
				return;

			clearTable();

			for (Course c : courses) {
				addTableData(c, checkEnrollment(c, regs));
			}
		});
		toolBar.add(viewAllCourses);
	}

	private char checkEnrollment(Course c, ArrayList<Registration> regs) {
		if (c == null || regs == null)
			return 'N';

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
		toolBar.add(back);
	}

	private void setupDisplay() {
		setupCourseTable();
		setupOfferingTable();
		
	}

	private void setupOfferingTable() {
		String[] columns = { "Section Number", "Section Capacity", "Current Enrollments"};

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
		
		//how to make this not occur on select and release?
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	            // do some actions here, for example
	            // print first column value from selected row
	        	String name = table.getValueAt(table.getSelectedRow(), 0).toString();
	        	int num = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 1).toString());
	        	displaySections(stuCon.search(name, num));
	        }
	    });

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(350, 175));
		display.add(scrollPane, BorderLayout.WEST);
	}
	
	private void displaySections(Course c) {
		ArrayList<CourseOffering> offeringList = stuCon.getOfferings(c.getCourseId());
		
		for(int i = 0; i < offeringList.size(); i++) {
			addOfferingTableData(offeringList.get(i));
		}
	}

	private void setupPanels() {
		title = new JPanel();
		display = new JPanel();
		display.setLayout(new BorderLayout());
		// display.setPreferredSize(new Dimension(400, 200));
		buttons = new JPanel();
		buttons.add(toolBar);
		this.add(title, BorderLayout.NORTH);
		this.add(display, BorderLayout.CENTER);
		this.add(buttons, BorderLayout.EAST);
	}

	private void clearTable() {
		tableModel.setRowCount(0);
	}

	private void addTableData(Course course, char enrolled) {
		Object[] data = new Object[] { course.getName(), course.getNumber(), enrolled };
		tableModel.addRow(data);
	}
	
	private void addOfferingTableData(CourseOffering o) {
		Object[] data = new Object[] {o.getSecNum(), o.getSecCap(), o.getStudentAmount()};
		tableModel.addRow(data);
	}
}
