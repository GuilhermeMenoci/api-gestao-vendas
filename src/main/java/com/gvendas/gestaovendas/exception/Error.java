package com.gvendas.gestaovendas.exception;

public class Error {

	private String msgUsuario;
	private String msgDev;
	
	public Error(String msgUsuario, String msgDev) {
		this.msgUsuario = msgUsuario;
		this.msgDev = msgDev;
	}

	public String getMsgUsuario() {
		return msgUsuario;
	}

	public String getMsgDev() {
		return msgDev;
	}
	
}
