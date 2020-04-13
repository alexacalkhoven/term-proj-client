package main.controller;

import java.util.HashMap;
import javax.swing.JPanel;
import main.view.*;

public class PanelController {

	Frame theFrame;
	HashMap<String, Panel> thePanels;
	
	public PanelController(CommunicationController comCon){
		theFrame = new Frame("Test");
		thePanels = new HashMap<String, Panel>();
		thePanels.put("login", new LoginPanel(this, comCon));
		thePanels.put("student", new StudentPanel(this, comCon));
		thePanels.put("admin", new AdminPanel(this, comCon));
		switchTo("login");
	}
	
	public void switchTo(String key) {
		Panel panel = thePanels.get(key);
		theFrame.setPanel(panel);
		theFrame.pack();
		panel.onViewChanged(theFrame);
		theFrame.center();
	}
}
