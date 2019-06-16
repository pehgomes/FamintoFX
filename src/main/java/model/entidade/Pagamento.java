package model.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="pagamento")
public class Pagamento {
	@Id
	@GeneratedValue
	protected Long id;
	
	@Column(name="value")
	protected double value;

	@OneToOne @MapsId
	protected Pedido pedido = null;
	
	public Pagamento() { }

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public double getValue() { return value; }
	public void setValue(double value) { this.value = value; }
	public Pedido getPedido() { return pedido; }
	public void setPedido(Pedido pedido) { this.pedido = pedido; }

	@Override
	public String toString() {
		if(pedido==null) return "Pagamento[Sem Pedido vinculado]";
		return "R$ " + pedido.total();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Pagamento) {
			return (((Pagamento)obj).id==this.id);
		}
		return false;
	}
}
