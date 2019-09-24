package br.com.dao;

import javax.persistence.EntityManager;

import br.com.model.Pedido;

public class PedidoDAO extends DAO<Pedido, Long>{

	public PedidoDAO(EntityManager manager, Class<Pedido> persistentClass) {
		super(manager, persistentClass);
	}
	

}
