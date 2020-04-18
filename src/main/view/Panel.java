package main.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.controller.PanelController;

/**
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public abstract class Panel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected PanelController panMan;
	
	public Panel(PanelController panMan) {
		this.panMan = panMan;
	}
	/**
	 * changes the view of the panel
	 * @param s the desired panel
	 */
	public void changeView(String s) {
		panMan.switchTo(s);
	}
	
	public void onViewChanged(JFrame frame) {
		
	}
	/**
	 * gets inputs 
	 * @param names the names of the inputs
	 * @return the user inputs
	 */
	public String[] getInputs(String[] names) {
		String[] stringInputs = new String[names.length];
		Object[] message = new Object[2*names.length];
		JTextField[] textfields = new JTextField[names.length];
		
		//four for loops :)
		for(int i = 0; i<names.length; i++) {
			textfields[i] = new JTextField();
		}
		
		for(int i = 0, j = 0; i<message.length; i+=2, j++) {
			message[i] = names[j];
		}
		
		for(int i = 0, j = 0; i<message.length; i+=2, j++) {
			message[i+1] = textfields[j];
		}
		
		int ret = JOptionPane.showConfirmDialog(getRootPane(), message, "Input", JOptionPane.OK_CANCEL_OPTION);
		
		if (ret == JOptionPane.CANCEL_OPTION || ret == JOptionPane.CLOSED_OPTION) {
			return null;
		}
		
		for(int i = 0; i<textfields.length; i++) {
			stringInputs[i] = textfields[i].getText();
		}

		return stringInputs;
		
	}
}
