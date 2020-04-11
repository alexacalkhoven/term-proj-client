package main;

import main.controller.*;

public class Client {
	private PanelController panMan;
	private CommunicationController comController;
	
	public Client() {
		comController = new CommunicationController();
		panMan = new PanelController(comController);	
	}
	
	public static void main(String[] args) {
		Client client = new Client();
	}
}
