package model.entidade;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="pedido")
public class Pedido {
	@Id
	@GeneratedValue
	@Column(name="id")
	protected Long id;
	
	@ManyToOne
	protected Cliente cliente = null;
	
	@ManyToOne
	protected Entregador entregador = null;
	
	@OneToOne(mappedBy="pedido", cascade=CascadeType.ALL)
	protected Pagamento pagamento = null;
	
	@Column(name="data")
	protected String data = null;
	
	@Column(name="pendente_entrega")
	protected boolean pendenteEntrega = true;

	public Pedido() {
		LocalDate dt = LocalDate.now();
		data = dt.format(DateTimeFormatter.ofPattern("dd/MM/YYYY"));
	}
	public Pedido(Cliente cliente, Entregador entregador, Pagamento pagamento) {
		this();
		this.cliente = cliente;
		this.pagamento = pagamento;
		this.pagamento.setPedido(this);
		
		this.setEntregador(entregador);
	}
	
	public void addItem(ItemCardapio item, int qtd) {
		addItemPedido(new ItemPedido(item, qtd));
	}

	@OneToMany(mappedBy="pedido", cascade=CascadeType.ALL)
	protected List<ItemPedido> itensPedidos = new ArrayList<ItemPedido>();
	public List<ItemPedido> getItensPedidos() { 
		return itensPedidos; 
	}
	public void addItemPedido(ItemPedido itemPedido) { 
		this.itensPedidos.add(itemPedido);
		itemPedido.setPedido(this);
	}
	public void setEntregador(Entregador entregador) { 
		this.entregador = entregador; 
		this.entregador.addPedido(this);
	}
	
	public Long getId() { return id; }
	public Cliente getCliente() { return cliente; }
	public void setCliente(Cliente cliente) { this.cliente = cliente; }
	public Entregador getEntregador() { return entregador; }
	public Pagamento getPagamento() { return pagamento; }
	public void setPagamento(Pagamento _pagamento) { this.pagamento=_pagamento; }
	public String getData() { return data; } 
	public boolean isPendenteEntrega() { return pendenteEntrega; }
	public void setId(Long id) { this.id = id; }
	public void setPendenteEntrega(boolean _pendenteEntrega) { this.pendenteEntrega = _pendenteEntrega; }

	public double total() {
		double soma = 0;
		for (ItemPedido ip : itensPedidos)
			soma += ip.getValidValue();
		return soma;
	}
	public String ticket() {
		StringBuilder sb = new StringBuilder();
		sb.append(System.lineSeparator()+"  Itens:");
		
		for (ItemPedido ip : itensPedidos)
			sb.append(ip.toString()+System.lineSeparator()+"        ");
		return "Pedido:"+System.lineSeparator()+
				"  "+cliente+System.lineSeparator()+
				"  pendenteEntrega="+pendenteEntrega+", data="+data+", total="+total()+
				sb.toString();
	}
	public void entregaFeita(boolean ativo){ 
		this.pendenteEntrega = !ativo; 
	}
	@Override
	public String toString() {
		return "Pedido(id=" + id + ", data=" + data + ")";
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Pedido) {
			return (((Pedido)obj).id==this.id);
		}
		return false;
	}
}
