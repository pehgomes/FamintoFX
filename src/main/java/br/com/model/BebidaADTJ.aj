package br.com.model;

public aspect BebidaADTJ { 

	public String Bebida.toString() {
		return "LOGANDO BEBIDA VIA ASPECT";
	}
}