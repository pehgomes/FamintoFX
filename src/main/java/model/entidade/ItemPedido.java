package model.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="item_pedido")
public class ItemPedido {
	@Id
	@GeneratedValue
	@Column(name="id")
	protected Long id;
	
	@Column(name="qtd")
	protected int qtd;
	
	@Column(name="liberado")
	protected boolean liberado = false;
	
	public ItemPedido() { }
	public ItemPedido(ItemCardapio item, int qtd) {
		this.cardapio = item;
		this.qtd = qtd;
		this.liberado = item.visit(this);
	}
	
	public double getValidValue() {
		if(this.liberado)
			return this.cardapio.getPreco() * this.qtd;
		return 0;
	}
	
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public int getQuantidade() { return qtd; } 
	public void setQuantidade(int _qtd) { this.qtd = _qtd; } 
	public boolean isLiberado() { return liberado; } 
	public void setLiberado(boolean free) { this.liberado = free; }

	@ManyToOne
	protected ItemCardapio cardapio = null;
	public ItemCardapio getCardapio() { return cardapio; }
	public void setCardapio(ItemCardapio _cardapio) { this.cardapio = _cardapio; }

	@ManyToOne
	protected Pedido pedido = null;
	public Pedido getPedido() { return pedido; }
	public void setPedido(Pedido _pedido) { this.pedido = _pedido; }
	
	@Override
	public String toString() {
		//return "Item[" + cardapio + "," + qtd + ", liberado =" + liberado +"]";
		return "Item[" + cardapio + "," + qtd + ", liberado =" + liberado +", idPedido="+pedido.id+ "]";
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ItemPedido) {
			return (((ItemPedido)obj).id==this.id);
		}
		return false;
	}
}
