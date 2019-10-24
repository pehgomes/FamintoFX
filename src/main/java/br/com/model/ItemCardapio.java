package br.com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="item_cardapio")
public class ItemCardapio {
	@Id
	@GeneratedValue
	@Column(name="id")
	protected Long id;

	@Column(name="nome")
	protected String nome;
	
	@Column(name="preco")
	protected double preco;
	
	public ItemCardapio() {}
	
	public ItemCardapio(String nome, double preco) {
		this.nome = nome;
		this.preco = preco;
	}
	
	public boolean visit(ItemPedido visitor) { return true; }
	
	public Long getId() { return id; }
	public String getNome() { return nome; }
	public double getPreco() { return preco; }
	public void setId(Long id) { this.id = id; }
	public void setNome(String nome) { this.nome = nome; }
	public void setPreco(double preco) { this.preco = preco; }
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ItemCardapio) {
			return (((ItemCardapio)obj).id==this.id);
		}
		return false;
	}
}