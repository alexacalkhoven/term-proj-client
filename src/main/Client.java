package main;

import main.controller.*;

public class Client {
	private PanelController panMan;
	private CommunicationController comController;
	
	public Client() {
		panMan = new PanelController();
		comController = new CommunicationController();
	}
	
	public static void main(String[] args) {
		Client client = new Client();
	}
}
