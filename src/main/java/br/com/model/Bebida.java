package br.com.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Bebida extends ItemCardapio {
	@Column(name = "qtd")
	protected int qtd;

	public Bebida() {
	}

	public Bebida(String _nome, double _preco, int qtd) {
		this.nome = _nome;
		this.preco = _preco;
		this.qtd = qtd;
	}

	public Integer getQtd() {
		return qtd;
	}

	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}

	@Override
	public final boolean visit(ItemPedido visitor) {
		boolean liberado = visitor.getQuantidade() <= this.qtd;
		if (liberado)
			this.qtd = this.qtd - visitor.getQuantidade();
		return liberado;
	}

	@Override
	public String toString() {
		return nome + "(" + this.preco + ", " + this.qtd + ")";
	}
}
