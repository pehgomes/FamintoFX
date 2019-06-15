package view.crud;

import java.util.List;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.dao.DAO;
import model.entidade.Bebida;
import model.entidade.ItemCardapio;
import util.ConnectionFactory;

public class CardapioController extends AbstractController {
	@FXML
	protected TextField textFieldCodigo;
	@FXML
	protected TextField textFieldNome;
	@FXML
	protected TextField textFieldPreco;
	@FXML
	protected Button btnAdd;
	@FXML
	protected Button btnRemove;
	@FXML
	protected Button btnNew;
	@FXML
	protected GridPane grid_pane;

	protected TableViewController<ItemCardapio> tableView;
	protected DAO<ItemCardapio, Long> dao;

	@FXML
	public void initialize() {
		dao = new DAO<ItemCardapio, Long>(ConnectionFactory.currentManager(), ItemCardapio.class);
		tabelaConfig();
		textFieldCodigo.getStyleClass().add("desabilitado");
		textFieldCodigo.setDisable(true);
	}
	
	@FXML
	public void voltar(ActionEvent actionEvent) {
		// TODO Auto-generated method stub
		super.voltar(actionEvent);
	}

	protected void tabelaConfig() {
		tableView = new TableViewController<ItemCardapio>(ItemCardapio.class, "id", "nome", "preco");
		grid_pane.getChildren().add(tableView);
		tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 1)
					tableEventUpdate();
			}
		});
		updateTableView();
	}

	private void tableEventUpdate() {
		ItemCardapio cardapio = tableView.getSelectionModel().getSelectedItem();
		if(cardapio!=null) {
			textFieldCodigo.setText("" + cardapio.getId());
			textFieldNome.setText(cardapio.getNome());
			textFieldPreco.setText("" + cardapio.getPreco());
		}
	}

	public ItemCardapio getCardapio() {
		Long id = -1l;

		try {
			id = Long.parseLong(textFieldCodigo.getText());
		} catch (Exception e) {
		}

		ItemCardapio cardapio = null;
		if (id >= 0)
			cardapio = dao.load(id);
		if (cardapio == null)
			cardapio = new ItemCardapio();

		if (id >= 0)
			cardapio.setId(id);
		cardapio.setNome(textFieldNome.getText());
		cardapio.setPreco(Double.parseDouble(textFieldPreco.getText()));
		return cardapio;
	}

	@FXML
	public void btnAddClick(ActionEvent event) {
		ItemCardapio cardapio = getCardapio();
		dao.save(cardapio);
		updateTableView();
		textFieldCodigo.setText(cardapio.getId() + "");
	}

	@FXML
	public void btnNewClick(ActionEvent event) {
		textFieldCodigo.setText("");
		btnAddClick(event);
	}

	@FXML
	public void btnRemoveClick(ActionEvent event) {
		if (!textFieldCodigo.getText().equals("")) {
			ItemCardapio cardapio = getCardapio();
			dao.delete(cardapio);
			updateTableView();
			textFieldCodigo.setText("");
		} else
			JOptionPane.showMessageDialog(null, "Codigo nullo!");
	}

	public void updateTableView() {
		tableView.getItems().clear();
		tableView.getItems().addAll(getCardapios());
	}

	public ObservableList<ItemCardapio> getCardapios() {
		List<ItemCardapio> cardapios = dao.list();
		ObservableList<ItemCardapio> lista = FXCollections.observableArrayList();
		for (ItemCardapio cardapio : cardapios) {
			if(!(cardapio instanceof Bebida))
				lista.add(cardapio);
		}
		return lista;
	}
}
