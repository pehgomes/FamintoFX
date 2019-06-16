package model.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente {
	@Id
	@GeneratedValue
	@Column(name = "id")
	protected Long id;

	@Column
	protected String nome;

	@Column
	protected Integer idade;

	@Column
	protected String endereco;

	@Column
	protected String telefone;

	@Column
	protected Boolean ativo = true;

	public Cliente() {
	}

	public Cliente(String nome, String endereco, String telefone, Integer idade) {
		super();
		this.nome = nome;
		this.endereco = endereco;
		this.idade = idade;
		this.telefone = telefone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	@Override
	public String toString() {
		return   nome + "," + endereco + "," + ativo ;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Cliente) {
			return (((Cliente) obj).id == this.id);
		}
		return false;
	}
}
