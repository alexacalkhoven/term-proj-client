package main.controller;

import java.io.Serializable;

public class Response implements Serializable{

	private static final long serialVersionUID = 1L;
	public String command;
    public Object data;
    
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