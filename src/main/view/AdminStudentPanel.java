package main.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
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
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import main.controller.AdminFunctController;
import main.controller.PanelController;
import main.model.Course;
import main.model.CourseOffering;
import main.model.Registration;
import main.model.Student;

public class AdminStudentPanel extends Panel {
	private static final long serialVersionUID = 1L;

	private AdminFunctController adCon;
	private JTextPane studentInfo;
	private JScrollPane infoScrollPane;
	private JToolBar toolBar;
	private JPanel display;
	private JTable table;
	private DefaultTableModel tableModel;

	public AdminStudentPanel(PanelController panMan, AdminFunctController adCon) {
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
		updateStudentTable();
	}

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

	private void setupButtons() {
		setupCreateStudent();
		setupRemoveStudent();
		setupBack();
	}

	private void setupBack() {
		makeButton("Back", (ActionEvent e) -> {
			changeView("login");
		});
	}

	private void setupCreateStudent() {
		makeButton("Create Student", (ActionEvent e) -> {
			try {
				String[] inputs = getInputs(new String[] { "Name:", "ID:" });
				if (inputs == null)
					return;

				int id = Integer.parseInt(inputs[1]);
				adCon.createStudent(inputs[0], id);
			} catch (NumberFormatException ex) {
				JOptionPane.showConfirmDialog(getRootPane(), "Error, must enter a number");
			}
			updateStudentTable();
		});
	}

	private void setupRemoveStudent() {
		makeButton("Remove Student", (ActionEvent e) -> {
			try {
				int row = table.getSelectedRow();

				if (row < 0) {
					JOptionPane.showMessageDialog(getRootPane(), "Please select a row", "Error", JOptionPane.OK_OPTION);
					return;
				}

				int id = adCon.getStudentIdFromRow(row);
				adCon.removeStudent(id);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(getRootPane(), "ID must be a number", "Error", JOptionPane.OK_OPTION);
			}
			updateStudentTable();
			studentInfo.setText("Student removed.");
		});
	}

	private void updateStudentTable() {
		ArrayList<Student> students = adCon.getStudentList();

		if (students == null)
			return;

		clearTable();

		for (Student s : students) {
			addTableData(s);
		}
	}

	private void clearTable() {
		tableModel.setRowCount(0);
	}

	private void addTableData(Student student) {
		Object[] data = new Object[] { student.getId(), student.getName() };
		tableModel.addRow(data);
	}

	private void setupTextArea() {
		studentInfo = new JTextPane();
		infoScrollPane = new JScrollPane(studentInfo);
		infoScrollPane.setPreferredSize(new Dimension(350, 175));
		studentInfo.setEditable(false);
		display.add(infoScrollPane);
	}

	private void setupTable() {
		String[] columns = { "Student ID", "Student Name" };

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
					int num = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
					updateTextArea(adCon.searchStudent(num));
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(350, 175));
		display.add(scrollPane);
	}

	private void updateTextArea(Student s) {
		String info = "Student Info: \n\n";

		info += "Name: " + s.getName() + "\n";
		info += "Course ID: " + s.getId() + "\n";

		info += "\n";

		info += "Enrolled courses: \n";
		ArrayList<Registration> regs = adCon.getRegs(s.getId());
		if (regs.size() == 0) {
			info += "This student is in no courses.\n";
		} else {
			for (int i = 0; i < regs.size(); i++) {
				info += regs.get(i) + "\n";
			}
		}
		
		studentInfo.setText(info);
	}
}
