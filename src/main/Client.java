package main;

import javax.swing.JPanel;
import main.view.AdminPanel;
import main.view.Frame;
import main.view.LoginPanel;
import main.view.StudentPanel;

public class Client {
	public static void main(String[] args) {
		JPanel p = new LoginPanel();
		JPanel a = new AdminPanel();
		JPanel s = new StudentPanel();
		Frame myFrame = new Frame("Hey");
		myFrame.setPanel(a);
	}
}
