package main;

import main.controller.*;

/**
 * Controls the interactions for the client.
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
@SuppressWarnings("unused")
public class Client {
	private PanelController panMan;
	private CommunicationController comController;

	/**
	 * Constructs a new client that will connect to given host and port
	 * 
	 * @param host Server host
	 * @param port Server port
	 */
	public Client(String host, int port) {
		comController = new CommunicationController(host, port);
		panMan = new PanelController(comController);
	}

	/**
	 * Program entrypoint
	 * @param args Command line arguments
	 */
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
