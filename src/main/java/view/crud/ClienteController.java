package view.crud;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.dao.DAO;
import model.entidade.Cliente;
import util.ConnectionFactory;

public class ClienteController {

	@FXML
	protected TextField textFieldEndereco;

	@FXML
	protected TextField textFieldNome;
	
	@FXML
	protected TextField textFieldCodigo;

	@FXML
	protected Button btnAdd;
	
	@FXML
	protected GridPane grid_pane;
	
	@FXML
	protected CheckBox checkbox;

	protected TableViewController<Cliente> tableView;
	
	protected DAO<Cliente, Long> clienteDao;
	
	@FXML 
	public void initialize() {
		clienteDao = new DAO<Cliente, Long>(ConnectionFactory.currentManager(), Cliente.class);
		textFieldCodigo.setText("1");
		textFieldCodigo.setDisable(true);
		tabelaConfig();
	}

	@FXML
	public void salvarCliente(ActionEvent event) {
		Cliente cliente = new Cliente(textFieldNome.getText(), textFieldEndereco.getText());
		clienteDao.save(cliente);
		updateTable();
	}
	
	private void updateTable() {
		tableView.getItems().clear();
		tableView.getItems().addAll(getClientes());
	}
	
	private ObservableList<Cliente> getClientes() {
		List<Cliente> clientes = clienteDao.list();
		ObservableList<Cliente> lista = FXCollections.observableArrayList();
		for (Cliente cliente : clientes)
			lista.add(cliente);
		return lista;
	}

	@FXML
	public void ativo(ActionEvent event) {
		System.out.println("");
	}

	@FXML
	public void sair(ActionEvent event) {
		System.exit(0);
	}

	
	public void tabelaConfig() {
		tableView = new TableViewController<Cliente>(Cliente.class, "id", "nome", "Endereco");
		grid_pane.getChildren().add(tableView);
		tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 1)
					tableEventUpdate();
			}
		});
	}

	private void tableEventUpdate() {
		Cliente cliente = tableView.getSelectionModel().getSelectedItem();
		if(cliente!=null) {
			textFieldCodigo.setText("" + cliente.getId());
			textFieldNome.setText(cliente.getNome());
			textFieldEndereco.setText(cliente.getEndereco());
		}
	}

}
