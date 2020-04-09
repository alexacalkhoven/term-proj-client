package main.view;

import javax.swing.JPanel;
import main.controller.PanelController;

@SuppressWarnings("serial")
public class Panel extends JPanel {

	private PanelController panMan;
	
	public Panel(PanelController panMan) {
		this.panMan = panMan;
	}
	
	public void changeView(String s) {
		panMan.switchTo(s);
	}
}
