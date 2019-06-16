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
	protected Button btnRemover;
	
	@FXML
	protected Button btnAtualizar;
	
	@FXML
	protected Button btnLimpar;

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
			chamarAlerta("CAMPOS OBRIGATÓRIOS !");
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
	
	@FXML
	public void remover(ActionEvent event) {
		if (textFieldCodigo.getText() != null && !textFieldCodigo.getText().isEmpty()) {
			Cliente cliente = clienteDao.load(Long.parseLong(textFieldCodigo.getText())) ;
			clienteDao.delete(cliente);
			chamarAlerta("CLIENTE DELETADO DA BASE.");
		}
		updateTable();
	}
	
	@FXML
	public void atualizar(ActionEvent event) {
		if (camposObrigatoriosPreenchidos()) {
			Cliente cliente = clienteDao.load(Long.parseLong(textFieldCodigo.getText()));
			cliente.setEndereco(textFieldEndereco.getText());
			cliente.setNome(textFieldNome.getText());
			cliente.setTelefone(textFieldTelefone.getText());
			cliente.setIdade(Integer.parseInt(textFieldIdade.getText()));

			clienteDao.merge(cliente);
			updateTable();
		} else {
			chamarAlerta("CAMPOS OBRIGATÓRIOS NÃO PREENCHIDOS");
		}
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
	public void limpar(ActionEvent event) {
		textFieldCodigo.setText("");
		textFieldEndereco.setText("");
		textFieldIdade.setText("");
		textFieldNome.setText("");
		textFieldTelefone.setText("");
		
	}

	public void tabelaConfig() {
		tableView = new TableViewController<Cliente>(Cliente.class, "nome", "endereco", "telefone", "idade");
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
			textFieldIdade.setText(cliente.getIdade() != null ? cliente.getIdade().toString() : "");
			textFieldTelefone.setText(cliente.getTelefone());
		}
	}

	private boolean camposObrigatoriosPreenchidos() {
		boolean isValido = true;
		if (textFieldTelefone.getText() == null || textFieldTelefone.getText().isEmpty()) {
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
