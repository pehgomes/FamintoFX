package br.com.model;

import javax.persistence.Entity;

@Entity
public class Lanche extends ItemCardapio {
	public Lanche() {}

	public Lanche(String _nome, double _preco) {
		this.nome = _nome;
		this.preco = _preco;
	}
}
