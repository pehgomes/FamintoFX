package br.com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="cheque")
public class Cheque extends Pagamento {
	@Column(name="numero")
	private String numero;
	
	@Column(name="agencia")
	private String agencia;
	
	@Column(name="conta")
	private String conta;
	
	@Column(name="banco")
	private String banco;
	
	public Cheque() {
		super();
	}

	public Cheque(double valor, String numero, String agencia, String conta, String banco) {
		super();
		this.value = valor;
		this.numero = numero;
		this.agencia = agencia;
		this.conta = conta;
		this.banco = banco;
	}

	public String getNumero() { return numero; }
	public void setNumero(String numero) { this.numero = numero; }
	public String getAgencia() { return agencia; } 
	public void setAgencia(String agencia) { this.agencia = agencia; }
	public String getConta() { return conta; }
	public void setConta(String conta) { this.conta = conta; }
	public String getBanco() { return banco; }
	public void setBanco(String banco) { this.banco = banco; }
	
	@Override
	public String toString() {
		return "Cheque(nro=" + numero + 
				", ag=" + agencia + 
				", cc=" + conta + 
				", bco=" + banco +
				", val=" + value + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Cheque) {
			return (((Cheque)obj).id==this.id);
		}
		return false;
	}
}
