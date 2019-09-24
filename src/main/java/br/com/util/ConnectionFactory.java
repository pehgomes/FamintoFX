package br.com.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionFactory {
	private static ThreadLocal<EntityManager> managers = new ThreadLocal<EntityManager>();
	
	private static final EntityManagerFactory factory;

	static {
		factory = Persistence.createEntityManagerFactory("jpa_unifor");
		System.out.println("Entrou no static");
	}

	public static EntityManager openSession() {
		if (containManager()) {
			System.out.println("GRAVE, alguem nao fechou um MANAGER ja aberto");
		}
		managers.set(getNewManager());
		return managers.get();
	}
	public static boolean containManager() {
		return (managers.get() != null);
	}

	public static EntityManager getNewManager() {
		System.out.println("Abrindo uma nova Sessao manager");
		return factory.createEntityManager();
	}

	public static void closeCurrentManager() {
		managers.get().close();
		managers.set(null);
	}
	public static void close() {
		if (containManager())
			currentManager().close();
		factory.close();
	}
	public static EntityManager currentManager() {
		if(containManager())
			return managers.get();
		else return openSession();
	}
}
