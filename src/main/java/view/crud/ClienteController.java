package view.crud;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.dao.DAO;
import model.entidade.Cliente;
import util.ConnectionFactory;

public class ClienteController extends AbstractController {

	@FXML
	protected TextField textFieldEndereco;

	@FXML
	protected TextField textFieldNome;

	@FXML
	protected TextField textFieldIdade;

	@FXML
	protected TextField textFieldTelefone;

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
		updateTable();
	}

	@FXML
	public void salvarCliente(ActionEvent event) {
		if (!camposObrigatoriosPreenchidos()) {
			Alert a = new Alert(AlertType.NONE);
			a.setAlertType(AlertType.WARNING);
			a.setContentText("CAMPOS OBRIGATÓRIOS !");
			a.show();
			return;
		}
		Cliente cliente = new Cliente(textFieldNome.getText(), textFieldEndereco.getText(), textFieldTelefone.getText(),
				Integer.parseInt(textFieldIdade.getText()));
		clienteDao.save(cliente);
		Integer id = clienteDao.list().size() + 1;
		textFieldCodigo.setText(id.toString());
		updateTable();
	}

	@FXML
	public void voltar(ActionEvent event) {
		super.voltar(event);
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
		if (cliente != null) {
			textFieldCodigo.setText("" + cliente.getId());
			textFieldNome.setText(cliente.getNome());
			textFieldEndereco.setText(cliente.getEndereco());
		}
	}

	private boolean camposObrigatoriosPreenchidos() {
		boolean isValido = true;
		if (textFieldTelefone.getText().isEmpty()) {
			isValido = false;
		} else if (textFieldIdade.getText().isEmpty()) {
			isValido = false;
		} else if (textFieldNome.getText().isEmpty()) {
			isValido = false;
		} else if (textFieldEndereco.getText().isEmpty()) {
			isValido = false;
		}
		return isValido;
	}

}
