package main.view;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.controller.PanelController;

@SuppressWarnings("serial")
public class Panel extends JPanel {

	protected PanelController panMan;
	
	public Panel(PanelController panMan) {
		this.panMan = panMan;
	}
	
	public void changeView(String s) {
		panMan.switchTo(s);
	}
	
	public String[] getInputs(String[] names) {
		String[] stringInputs = new String[names.length];
		Object[] message = new Object[2*names.length];
		JTextField[] textfields = new JTextField[names.length];
		
		for(int i = 0; i<names.length; i++) {
			textfields[i] = new JTextField();
		}
		
		for(int i = 0; i<names.length; i+=2) {
			message[i] = names[i];
		}
		
		for(int i = 0; i<textfields.length; i+=2) {
			message[i+1] = textfields[i];
		}
		
		JOptionPane.showConfirmDialog(getRootPane(), message, "Input", JOptionPane.OK_CANCEL_OPTION);
		
		for(int i = 0; i<textfields.length; i++) {
			stringInputs[i] = textfields[i].getText();
		}

		return stringInputs;
		
	}
}
