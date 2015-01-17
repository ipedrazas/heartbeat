package me.pedrazas.heartbeat.om;

import java.util.List;

public class Envelope {

	@Override
	public String toString() {
		return "Envelope [id=" + id + ", message=" + message + ", commands="
				+ commands + ", error=" + error + ", errorCode=" + errorCode
				+ "]";
	}
	
	private int id;
	private String message;
	private List<String> commands;
	private String error;
	private int errorCode;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<String> getCommands() {
		return commands;
	}
	public void setCommands(List<String> commands) {
		this.commands = commands;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
