package main.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

/**
 * Controls the Admin panel view where courses can be managed.
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class AdminCoursePanel extends Panel {
	private static final long serialVersionUID = 1L;

	private AdminFunctController adCon;
	private JTextPane courseInfo;
	private JScrollPane infoScrollPane;
	private JToolBar toolBar;
	private JPanel display;
	private JTable table;
	private DefaultTableModel tableModel;

	/**
	 * Initializes a new AdminCoursePanel
	 * 
	 * @param panMan Panel manager
	 * @param adCon Admin panel function controller
	 */
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

	/**
	 * Method called when the view is changed to this panel
	 * 
	 * @param frame The host frame
	 */
	@Override
	public void onViewChanged(JFrame frame) {
		updateCourses();
	}

	/**
	 * sets up the tool bar
	 */
	private void setupToolBar() {
		JPanel buttons = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		toolBar = new JToolBar("Buttons");
		toolBar.setFloatable(false);
		toolBar.setOrientation(SwingConstants.VERTICAL);
		buttons.add(toolBar, c);
		add(buttons, BorderLayout.EAST);
	}

	/**
	 * sets up the buttons
	 */
	private void setupButtons() {
		setupCreateCourse();
		setupRemoveCourse();
		setupCreateOffering();
		setupRemoveOffering();
		setupAddPreReq();
		setupRemovePreReq();
		setupBack();
	}

	/**
	 * makes a button
	 * 
	 * @param name     the name of the button
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
	 * Removes a pre-req from a course.
	 */
	private void setupRemovePreReq() {
		makeButton("Remove Pre-Requisite", (ActionEvent e) -> {
			try {
				int row = table.getSelectedRow();

				if (row < 0) {
					JOptionPane.showMessageDialog(getRootPane(), "Please select a course", "Error",
							JOptionPane.OK_OPTION);
					return;
				}

				String[] inputs = getInputs(new String[] { "Course ID of pre-req to remove: " });

				if (inputs == null)
					return;

				int parentCourseId = adCon.getCourseIdFromRow(row);
				int childCourseId = Integer.parseInt(inputs[0]);

				adCon.removePreReq(parentCourseId, childCourseId);
				updateTextArea(adCon.searchCourseById(parentCourseId));

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(getRootPane(), "Course number must be a number", "Error",
						JOptionPane.OK_OPTION);
			}
		});
	}

	/**
	 * Adds a pre-req to a course.
	 */
	private void setupAddPreReq() {
		makeButton("Add Pre-Requisite", (ActionEvent e) -> {
			try {
				int row = table.getSelectedRow();

				if (row < 0) {
					JOptionPane.showMessageDialog(getRootPane(), "Please select a course", "Error",
							JOptionPane.OK_OPTION);
					return;
				}

				String[] inputs = getInputs(new String[] { "Course ID of Pre-Req: " });

				if (inputs == null)
					return;

				int parentCourseId = adCon.getCourseIdFromRow(row);
				int childCourseId = Integer.parseInt(inputs[0]);

				adCon.addPreReq(parentCourseId, childCourseId);
				updateTextArea(adCon.searchCourseById(parentCourseId));

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(getRootPane(), "Course number must be a number", "Error",
						JOptionPane.OK_OPTION);
			}
		});
	}

	/**
	 * Removes an offering from a course.
	 */
	private void setupRemoveOffering() {
		makeButton("Remove Offering", (ActionEvent e) -> {
			try {
				int row = table.getSelectedRow();

				if (row < 0) {
					JOptionPane.showMessageDialog(getRootPane(), "Please select a section", "Error",
							JOptionPane.OK_OPTION);
					return;
				}

				String[] inputs = getInputs(new String[] { "Section Number:" });

				if (inputs == null)
					return;

				int courseId = adCon.getCourseIdFromRow(row);
				int secNum = Integer.parseInt(inputs[0]);

				int offeringId = adCon.getOfferingId(courseId, secNum);
				adCon.removeOffering(offeringId);
				updateCourses();
				updateTextArea(adCon.searchCourseById(courseId));

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(getRootPane(), "Section number must be a number", "Error",
						JOptionPane.OK_OPTION);
			}

			courseInfo.setText("Offering deleted.");
		});
	}

	/**
	 * Sets up the back button
	 */
	private void setupBack() {
		makeButton("Back", (ActionEvent e) -> {
			changeView("login");
		});
	}

	/**
	 * Sets up the Create Offering button
	 */
	private void setupCreateOffering() {
		makeButton("Create Course Offering", (ActionEvent e) -> {
			try {
				int row = table.getSelectedRow();

				if (row < 0) {
					JOptionPane.showMessageDialog(getRootPane(), "Please select a course", "Error",
							JOptionPane.OK_OPTION);
					return;
				}

				String[] inputs = getInputs(new String[] { "Section Number:", "Section Capacity:" });

				if (inputs == null)
					return;

				int courseId = adCon.getCourseIdFromRow(row);
				int num = Integer.parseInt(inputs[0]);
				int cap = Integer.parseInt(inputs[1]);
				adCon.addOffering(courseId, num, cap);
				updateTextArea(adCon.searchCourseById(courseId));

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(getRootPane(), "Course number must be a number", "Error",
						JOptionPane.OK_OPTION);
			}
		});
	}

	/**
	 * Sets up the Remove Course button
	 */
	private void setupRemoveCourse() {
		makeButton("Remove Course", (ActionEvent e) -> {
			try {
				int row = table.getSelectedRow();

				if (row < 0) {
					JOptionPane.showMessageDialog(getRootPane(), "Please select a course", "Error",
							JOptionPane.OK_OPTION);
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
	 * Sets up the CreateCourse button
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
	 * Updates the displayed courses
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
	 * Clears the table
	 */
	private void clearTable() {
		tableModel.setRowCount(0);
	}

	/**
	 * Adds data to the table
	 * 
	 * @param course the course to be added
	 */
	private void addTableData(Course course) {
		Object[] data = new Object[] { course.getName(), course.getNumber() };
		tableModel.addRow(data);
	}

	/**
	 * Sets up the text area
	 */
	private void setupTextArea() {
		courseInfo = new JTextPane();
		infoScrollPane = new JScrollPane(courseInfo);
		infoScrollPane.setPreferredSize(new Dimension(350, 175));
		courseInfo.setEditable(false);
		display.add(infoScrollPane);
	}

	/**
	 * Sets up the table
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
	 * Updates the text area
	 * 
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
				info += offerings.get(i) + "\n";
			}
		}

		info += "\n";

		info += "Pre-Requisites: \n";
		ArrayList<Course> preReqs = adCon.getPrereqs(c.getCourseId());
		if (preReqs.size() == 0) {
			info += "None.\n";
		} else {
			for (int i = 0; i < preReqs.size(); i++) {
				info += String.format("ID: %d, Name: %s\n", preReqs.get(i).getCourseId(), preReqs.get(i).getFullName());
			}
		}

		courseInfo.setText(info);
	}
}
