package model.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class DAO<T, I extends Serializable> {
	private Class<T> persistentClass;
	private EntityManager manager;

	public DAO(EntityManager manager, Class<T> persistentClass) {
		this.manager = manager;
		this.persistentClass = persistentClass;
	}

	public T load(I id) {
		System.out.println("lendo " + persistentClass + " com id " + id);
		return (T) manager.find(persistentClass, id);
	}

	public void save(T t) {
		manager.getTransaction().begin();
		System.out.println("Salvando " + t);
		manager.persist(t);
		manager.flush();
		manager.getTransaction().commit();
	}
	public T merge(T t) {
		manager.getTransaction().begin();
		System.out.println("Salvando ou atualizando " + t);
		T r = manager.merge(t);
		manager.flush();
		manager.getTransaction().commit();
		return r;
	}
	public void delete(T t) {
		manager.getTransaction().begin();
		System.out.println("Deletando " + t);
		manager.remove(t);
		manager.flush();
		manager.getTransaction().commit();
	}
	public List<T> list() {
		manager.getTransaction().begin();
		System.out.println("Listando todos");
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(persistentClass);
		 
		TypedQuery<T> typedQuery = manager.createQuery(
		    query.select(
		       query.from(persistentClass)
		    )
		);
		manager.getTransaction().commit();
		return typedQuery.getResultList();
	}
}