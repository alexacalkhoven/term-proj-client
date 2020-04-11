package main.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CommunicationController {

	private Socket aSocket;
	private ObjectInputStream socketIn;
	private ObjectOutputStream socketOut;

	public CommunicationController() {
		try {
			aSocket = new Socket("localhost", 4200);
			socketIn = new ObjectInputStream(aSocket.getInputStream());
			socketOut = new ObjectOutputStream(aSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Object makeRequest(String name, Object ob) {
		try {
			socketOut.writeObject(new Request(name, ob));
			socketOut.reset();
			Response res = (Response) socketIn.readObject();
			return res.getData();		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}
		return null;
	}

}
