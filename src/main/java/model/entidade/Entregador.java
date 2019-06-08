package model.entidade;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="entregador")
public class Entregador {
	@Id
	@GeneratedValue
	@Column(name="id")
	protected Long id;
	
	@Column(name="nome")
	protected String nome;
	
	@Column(name="placa")
	protected String placa;
	
	public Entregador() { }

	public Entregador(String nome, String placa) {
		super();
		this.nome = nome;
		this.placa = placa;
	}

	@OneToMany(mappedBy="entregador", cascade=CascadeType.ALL)
	protected Collection<Pedido> pedidos = new HashSet<Pedido>();
	public Collection<Pedido> getPedidos() { 
		return pedidos;
	}
	public void addPedido(Pedido pedido) { 
		if(!this.pedidos.contains(pedido)) {
			this.pedidos.add(pedido);
			pedido.setEntregador(this);
		}
	}
	
	public void recebePedido(Pedido pedido) {
		pedidos.add(pedido);
	}
	public void recebePedido(Pedido ... _pedidos) {
		for (Pedido p : _pedidos)
			pedidos.add(p);
	}
	public double totalOfPedidoEntregues() {
		double soma = 0;
		for (Pedido p : pedidos) {
			if(!p.isPendenteEntrega())
				soma += p.total();
		}
		return soma;
	}
	public double getComissao() {
		return totalOfPedidoEntregues()*0.05;
	}
	
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }
	public String getPlaca() { return placa; }
	public void setPlaca(String placa) { this.placa = placa; }

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (Pedido pedido : pedidos) {
			sb.append(pedido.getId()+" ");
		}
		sb.append("]");
		return "Entregador[" + nome + "," + placa + sb + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Entregador) {
			return (((Entregador)obj).id==this.id);
		}
		return false;
	}

	public String ticket() {
		return "Total: " + this.totalOfPedidoEntregues() + " Comissao: " + this.getComissao();
	}
}
