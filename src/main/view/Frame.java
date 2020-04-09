package main.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

//shows the various views
@SuppressWarnings("serial")
public class Frame extends JFrame{
	
	public Frame(String s) {
		super(s);
		setTitle("Course Registration System");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dimensions.width - getSize().width) / 2, (dimensions.height - getSize().height) / 2);
	}
	
	public void setPanel(JPanel p) {
		this.setContentPane(p);
		pack();
		Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dimensions.width - getSize().width) / 2, (dimensions.height - getSize().height) / 2);
	}
}
