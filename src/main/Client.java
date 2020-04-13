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
		String host = "localhost";
		int port = 4200;
		
		try {
			if (args.length == 2) {
				host = args[0];
				port = Integer.parseInt(args[1]);
			}
			
			Client client = new Client(host, port);
		} catch (NumberFormatException e) {
			System.err.println("If supplying arguments for host and port, port must be a number!");
		}
	}
}
