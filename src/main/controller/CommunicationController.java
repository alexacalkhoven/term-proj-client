package main.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

/**
 * Controlls the communication between the client and server
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class CommunicationController {

	private Socket aSocket;
	private ObjectInputStream socketIn;
	private ObjectOutputStream socketOut;
/**
 * The constructor for CommunicationController
 * @param host the host 
 * @param port the port
 */
	public CommunicationController(String host, int port) {
		try {
			aSocket = new Socket(host, port);
			socketIn = new ObjectInputStream(aSocket.getInputStream());
			socketOut = new ObjectOutputStream(aSocket.getOutputStream());
			
			System.out.println("Connected to server at " + host + ":" + port);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Could not connect to the server", "Error", JOptionPane.OK_OPTION);
			System.exit(0);
		}
	}
	/**
	 * Makes a request to the server
	 * @param name the name of the request
	 * @param ob the arguments passed 
	 * @return the output of the server based on the passed arguments and name of request
	 */
	public Object makeRequest(String name, Object ob) {
		try {
			socketOut.writeObject(new Request(name, ob));
			socketOut.reset();
			
			Response res = (Response) socketIn.readObject();
			
			if (res.getError() != null) {
				JOptionPane.showMessageDialog(null, res.getError(), "Error", JOptionPane.OK_OPTION);
				System.err.println("Request error for '" + res.getCommand() + "': " + res.getError());
			}
			
			return res.getData();		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}
		return null;
	}
	//Makes a request to the server with no arguments
	public Object makeRequest(String name) {
		return makeRequest(name, null);
	}
}
