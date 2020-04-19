package main.controller;

import java.io.Serializable;

/**
 * The response from the server.
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class Response implements Serializable {

	private static final long serialVersionUID = 1L;
	private String command;
	private Object data;
	private String error;

	public String getError() {
		return error;
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