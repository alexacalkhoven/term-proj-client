package main.controller;

public class LoginFunctController {
	private CommunicationController comCon;
	
	public LoginFunctController(CommunicationController comCon) {
		this.comCon = comCon;
	}
	
	public void loginStudent(int id) {
		comCon.makeRequest("student.login", id);
	}
}
