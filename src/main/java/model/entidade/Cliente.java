package model.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cliente")
public class Cliente {
	@Id
	@GeneratedValue
	@Column(name="id")
	protected Long id;
	
	@Column(name="nome")
	protected String nome;
	
	@Column(name="endereco")
	protected String endereco;
	
	@Column(name="ativo")
	protected Boolean ativo = true;
	
	public Cliente() {}
	
	public Cliente(String nome, String endereco) {
		super();
		this.nome = nome;
		this.endereco = endereco;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; } 
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }
	public String getEndereco() { return endereco; } 
	public void setEndereco(String endereco) { this.endereco = endereco; }
	public Boolean isAtivo() { return ativo; }
	public void setAtivo(Boolean ativo) { this.ativo = ativo; }

	@Override
	public String toString() {
		return "Cliente[" + nome + "," + endereco + "," + ativo + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Cliente) {
			return (((Cliente)obj).id==this.id);
		}
		return false;
	}
}
