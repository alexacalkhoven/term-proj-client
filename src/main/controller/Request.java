package main.controller;

import java.io.Serializable;

/**
 * A request to send to the server.
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class Request implements Serializable {
	private static final long serialVersionUID = 1L;

	private String command;
	private Object data;

	/**
	 * Creates a new network request object with given command and data
	 * 
	 * @param command Network command
	 * @param data Network data
	 */
	public Request(String command, Object data) {
		this.command = command;
		this.data = data;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
