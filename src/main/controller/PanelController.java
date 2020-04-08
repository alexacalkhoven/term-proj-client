package main.controller;

import main.view.*;

public class PanelController {

	Frame theFrame;
	
	public PanelController(){
		theFrame = new Frame("Test");
		theFrame.setPanel(new LoginPanel());
	}
	
	//how to switch between the views without giving the Panels a PanelController object?
}
