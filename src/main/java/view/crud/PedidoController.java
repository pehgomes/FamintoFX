package view.crud;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import model.dao.DAO;
import model.entidade.Cliente;
import model.entidade.Pedido;
import util.ConnectionFactory;

public class PedidoController extends AbstractController {

	private DAO<Pedido, Long> pedidoDao;
	
	protected ComboBox<Cliente> clientes;
	
	@FXML
	protected void initialize() {
		pedidoDao = new DAO<Pedido, Long>(ConnectionFactory.currentManager(), Pedido.class);
		clientes.getItems().removeAll(clientes.getItems());
		clientes.getItems().add(new Cliente());
	}

}
