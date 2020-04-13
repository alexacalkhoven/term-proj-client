package main;

import main.controller.*;

@SuppressWarnings("unused")
public class Client {
	private PanelController panMan;
	private CommunicationController comController;
	
	public Client(String host, int port) {
		comController = new CommunicationController(host, port);
		panMan = new PanelController(comController);	
	}
	
	public static void main(String[] args) {
		Client client = new Client("localhost", 4200);
	}
}
