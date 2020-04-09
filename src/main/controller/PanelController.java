package main.controller;

import java.util.HashMap;
import javax.swing.JPanel;
import main.view.*;

public class PanelController {

	Frame theFrame;
	HashMap<String, JPanel> thePanels;
	
	public PanelController(){
		theFrame = new Frame("Test");
		thePanels = new HashMap<String, JPanel>();
		thePanels.put("login", new LoginPanel(this));
		thePanels.put("student", new StudentPanel(this));
		thePanels.put("admin", new AdminPanel(this));
		switchTo("login");
	}
	
	public void switchTo(String key) {
		theFrame.setPanel(thePanels.get(key));
		theFrame.pack();
	}
}
