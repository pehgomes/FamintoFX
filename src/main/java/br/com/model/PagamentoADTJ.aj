package br.com.model;

public aspect PagamentoADTJ { 

	pointcut setExecution(): 
		execution(void Pagamento.setPedido(Pedido));
		
	before() : setExecution() {
		Object[] args = thisJoinPoint.getArgs();
		Pedido b1 = (Pedido) args[0];
		System.out.println("CRIANDO UM PEDIDO "+ b1);
	}
	
	after() : setExecution() {
		System.out.println("INSERIU ...");
	}
				
	
}