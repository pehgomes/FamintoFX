package br.com.dao;

import br.com.model.Bebida;

public aspect LoggerDao {
		
	pointcut saveExceution(): 
		execution(void DAO.save(Object));		
		
	pointcut mergeExecution(): 
		execution(Object DAO.merge(Object));		
		
	pointcut deleteExecution(): 
	execution(void DAO.delete(Object));					
		
	before() : saveExceution() {
		Object[] args = thisJoinPoint.getArgs();
		Bebida b1 = (Bebida) args[0];
		System.out.println("IRA INSERIR "+ b1.getNome());
	}
	
	after() : saveExceution() {
		System.out.println("INSERIU ...");
	}
	
	before() : mergeExecution() {
		Object[] args = thisJoinPoint.getArgs();
		System.out.println("IRA ATUALIZAR " + args[0]);
	}	
	
	after() : mergeExecution() {
		System.out.println("ATUALIZOU ...");
	}
	
	before() : deleteExecution() {
		Object[] args = thisJoinPoint.getArgs();
		System.out.println("IRA DELETAR ..."+ args[0]);
	}	
	
	after() : deleteExecution() {
		System.out.println("DELETOU ...");
	}	
	
}