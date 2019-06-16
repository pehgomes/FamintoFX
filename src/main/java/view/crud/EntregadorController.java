package view.crud;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.dao.DAO;
import model.entidade.Entregador;
import util.ConnectionFactory;

public class EntregadorController extends AbstractController {

	private DAO<Entregador, Long> entregadorDao;

	@FXML
	protected GridPane grid_pane;
	
	@FXML
	protected TextField textFieldNome;
	
	@FXML
	protected TextField textFieldPlaca;

	protected TableViewController<Entregador> tableView;

	@FXML
	public void initialize() {
		entregadorDao = new DAO<Entregador, Long>(ConnectionFactory.currentManager(), Entregador.class);
		tabelaConfig();
		updateTable();
	}
	
	@FXML
	public void salvar() {
		if (!camposObrigatoriosPreenchidos()) {
			chamarAlerta("Campos obrigatórios !");
			return;
		}
		
		Entregador entregador = new Entregador(textFieldNome.getText(), textFieldPlaca.getText());
		entregadorDao.save(entregador);
		updateTable();
	}

	public void tabelaConfig() {
		tableView = new TableViewController<Entregador>(Entregador.class, "nome", "placa");
		grid_pane.getChildren().add(tableView);
		tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 1)
					tableEventUpdate();
			}
		});
	}
	
	private void tableEventUpdate() {
		Entregador entregador = tableView.getSelectionModel().getSelectedItem();
		if (entregador != null) {
			textFieldNome.setText(entregador.getNome());
			textFieldPlaca.setText(entregador.getPlaca());
		}
	}
	
	private void updateTable() {
		tableView.getItems().clear();
		tableView.getItems().addAll(getEntregadores());
	}

	
	private ObservableList<Entregador> getEntregadores() {
		List<Entregador> entregadores = entregadorDao.list();
		ObservableList<Entregador> lista = FXCollections.observableArrayList();
		for (Entregador entregador : entregadores)
			lista.add(entregador);
		return lista;
	}
	
	private boolean camposObrigatoriosPreenchidos() {
		boolean isValido = true;
		if (textFieldNome.getText() == null || textFieldNome.getText().isEmpty()) {
			isValido = false;
		} else if (textFieldPlaca.getText().isEmpty()) {
			isValido = false;
		}
		return isValido;
	}


}
