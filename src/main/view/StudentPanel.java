package main.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
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

	private GridBagConstraints c;
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
	@Override
	public void onViewChanged(JFrame frame) {
		updateCourses();
	}
	private void setupToolbar() {
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setOrientation(SwingConstants.VERTICAL);
	}

	private void setupButtons() {
		setupBack();
		setupSearch();
		setupRegForCourse();
		setupDrop();
	}

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

	private void setButtonSize(JButton b) {
		Dimension d = new Dimension(175, 25);
		b.setPreferredSize(d);
		b.setMinimumSize(d);
		b.setMaximumSize(d);
	}

	private void setupDrop() {
		dropCourse = new JButton("Drop Course");
		setButtonSize(dropCourse);

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
			updateCourses();
		});
		toolBar.add(dropCourse);
	}

	private void setupRegForCourse() {
		registerForCourse = new JButton("Register For Course");
		setButtonSize(registerForCourse);

		registerForCourse.addActionListener((ActionEvent e) -> {
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
			updateCourses();
		});
		toolBar.add(registerForCourse);
	}

	private void setupSearch() {
		searchCourseCatalogue = new JButton("Search Course Catalogue");
		setButtonSize(searchCourseCatalogue);

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

	private char checkEnrollment(Course c, ArrayList<Registration> regs) {
		if (c == null || regs == null)
			return 'N';

		for (Registration reg : regs) {
			Course regCourse = reg.getOffering().getCourse();
			if (c.getCourseId() == regCourse.getCourseId()) {
				return 'Y';
			}
		}

		return 'N';
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
		setupCourseTable();
		setupOfferingTable();

	}

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
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				// do some actions here, for example
				// print first column value from selected row
				if (table.getSelectedRow() >= 0) {
					String name = table.getValueAt(table.getSelectedRow(), 0).toString();
					int num = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 1).toString());
					displaySections(stuCon.search(name, num));
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(350, 175));
		display.add(scrollPane, BorderLayout.WEST);
	}

	private void displaySections(Course c) {
		System.out.println(c);
		System.out.println(c.getCourseId());
		ArrayList<CourseOffering> offeringList = stuCon.getOfferings(c.getCourseId());

		// keep an updated array of the offerings in the student funct controller
		stuCon.setOfferingList(offeringList);

		if (offeringList.size() == 0) {
			clearOfferingTable();
			Object[] data = new Object[] { "No Sections" };
			offeringTableModel.addRow(data);
			return;
		}

		clearOfferingTable();
		for (int i = 0; i < offeringList.size(); i++) {
			addOfferingTableData(offeringList.get(i));
		}
	}

	private void setupPanels() {
		title = new JPanel();
		display = new JPanel();
		display.setLayout(new BorderLayout());
		// display.setPreferredSize(new Dimension(400, 200));
		buttons = new JPanel(new GridBagLayout());
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		buttons.add(toolBar, c);
		this.add(title, BorderLayout.NORTH);
		this.add(display, BorderLayout.CENTER);
		this.add(buttons, BorderLayout.EAST);
	}

	private void clearTable() {
		tableModel.setRowCount(0);
	}

	private void clearOfferingTable() {
		offeringTableModel.setRowCount(0);
	}

	private void addTableData(Course course, char enrolled) {
		Object[] data = new Object[] { course.getName(), course.getNumber(), enrolled };
		tableModel.addRow(data);
	}

	private void addOfferingTableData(CourseOffering o) {
		Object[] data = new Object[] { o.getSecNum(), o.getSecCap(), o.getStudentAmount() };
		offeringTableModel.addRow(data);
	}
}
