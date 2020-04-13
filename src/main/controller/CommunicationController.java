package main.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

public class CommunicationController {

	private Socket aSocket;
	private ObjectInputStream socketIn;
	private ObjectOutputStream socketOut;

	public CommunicationController(String host, int port) {
		try {
			aSocket = new Socket(host, port);
			socketIn = new ObjectInputStream(aSocket.getInputStream());
			socketOut = new ObjectOutputStream(aSocket.getOutputStream());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Could not connect to the server", "Error", JOptionPane.OK_OPTION);
			System.exit(0);
		}
	}

	public Object makeRequest(String name, Object ob) {
		try {
			socketOut.writeObject(new Request(name, ob));
			socketOut.reset();
			
			Response res = (Response) socketIn.readObject();
			
			if (res.getError() != null) {
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
	
	public Object makeRequest(String name) {
		return makeRequest(name, null);
	}
}
