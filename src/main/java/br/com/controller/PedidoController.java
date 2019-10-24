package br.com.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;
import javax.swing.JOptionPane;

import br.com.dao.DAO;
import br.com.model.Bebida;
import br.com.model.Cliente;
import br.com.model.Entregador;
import br.com.model.ItemCardapio;
import br.com.model.Pagamento;
import br.com.model.Pedido;
import br.com.util.ConnectionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class PedidoController extends AbstractController {

	protected Boolean isAberto = Boolean.TRUE;

	protected DAO<Pedido, Long> pedidoDao;

	protected DAO<Cliente, Long> clienteDao;

	private DAO<Entregador, Long> entregadorDao;

	private DAO<ItemCardapio, Long> cardapioDao;

	@FXML
	public ComboBox<Cliente> combobox;

	@FXML
	public ComboBox<Entregador> comboboxEntregador;

	@FXML
	public ComboBox<ItemCardapio> comboboxItemPedido;

	@FXML
	public ListView<String> listView;

	@FXML
	public Button btnAdd;
	
	@FXML
	protected Button btnRemove;

	@FXML
	public TextFlow nota;

	@FXML
	protected GridPane grid_pane;
	
	@FXML
	private TextField textFieldID;

	protected TableViewController<Pedido> tableView;

	final ObservableList<String> listItems = FXCollections.observableArrayList();

	private List<ItemCardapio> itensCardapio = new ArrayList<ItemCardapio>();

	@FXML
	protected void initialize() {
		Text ttlNfe = new Text("NFE - NOTA FISCAL " + new Date() + "\n");
		ttlNfe.setFill(Color.BLACK);
		ttlNfe.setFont(Font.font("Helvetica", FontPosture.ITALIC, 10));
		ttlNfe.setTextAlignment(TextAlignment.CENTER);
		nota.getChildren().add(ttlNfe);

		pedidoDao = new DAO<Pedido, Long>(ConnectionFactory.currentManager(), Pedido.class);
		clienteDao = new DAO<Cliente, Long>(ConnectionFactory.currentManager(), Cliente.class);
		entregadorDao = new DAO<Entregador, Long>(ConnectionFactory.currentManager(), Entregador.class);
		cardapioDao = new DAO<ItemCardapio, Long>(ConnectionFactory.currentManager(), ItemCardapio.class);

		listView.setItems(listItems);
		combobox.setItems(getClientes());
		comboboxEntregador.setItems(getEntregadores());
		comboboxItemPedido.setItems(getItens());
		tabelaConfig();
		updateTable();
	}

	@FXML
	public void finalizar(ActionEvent event) {

		if (isAberto) {
			Map<Long, Long> result = itensCardapio.stream()
					.collect(Collectors.groupingBy(ItemCardapio::getId, Collectors.counting()));
			
			Cliente clientePedido = combobox.getSelectionModel().getSelectedItem();
			Entregador entregadorPedido = comboboxEntregador.getSelectionModel().getSelectedItem();
			Pagamento pagamentoPedido = new Pagamento();
			pagamentoPedido.setValue(itensCardapio.stream().mapToDouble(ItemCardapio::getPreco).sum());
			Pedido pedido = new Pedido(clientePedido, entregadorPedido, pagamentoPedido);

			result.forEach((k, v) -> {
				pedido.addItem(cardapioDao.load(k), v.intValue());
			});

			pedidoDao.save(pedido);

			comboboxItemPedido.setDisable(true);
			concluirNfe();
			isAberto = Boolean.FALSE;
			updateTable();
		}
	}
	
	@Override
	public void voltar(ActionEvent actionEvent) {
		Stage stage = (Stage) btnAdd.getScene().getWindow();
		stage.close();
		super.voltar(actionEvent);
	}
	
	@FXML
	public void btnRemoveClick(ActionEvent event) {
		try {
			if (!textFieldID.getText().equals("")) {
				Pedido bebida = getPedido();
				pedidoDao.delete(bebida);
				updateTableView();
				textFieldID.setText("");
			} else {
				JOptionPane.showMessageDialog(null, "Codigo nullo!");
			}
		} catch (PersistenceException e) {
			chamarAlerta(e.getMessage());
		}
	}
	
	public void updateTableView() {
		tableView.getItems().remove(getPedido());
	}
	
	public Pedido getPedido() {
		Long id = -1l;

		try {
			id = Long.parseLong(textFieldID.getText());
		} catch (Exception e) {
		}

		Pedido pedido = null;
		if (id >= 0)
			pedido = pedidoDao.load(id);
		if (pedido == null)
			pedido = new Pedido();

		if (id >= 0)
			pedido.setId(id);
		return pedido;
	}


	
	@FXML
	public void addItemListView(ActionEvent event) {
		if (!isAberto) {
			return;
		}
		ItemCardapio item = comboboxItemPedido.getSelectionModel().getSelectedItem();
		if (item instanceof Bebida && ((Bebida) item).getQtd() == 0) {
			chamarAlerta("Sem Bebidas no Estoque !");
			return;
		}

		listItems.add(item.toString());
		itensCardapio.add(item);
		listView.setItems(listItems);

		if (item instanceof Bebida) {
			Bebida bebida = (Bebida) cardapioDao.load(item.getId());
			bebida.setQtd(bebida.getQtd() - 1);
			cardapioDao.merge(bebida);
		}

		montarNotaFiscal(item);
		comboboxItemPedido.setItems(getItens());
	}

	private ObservableList<ItemCardapio> getItens() {
		ObservableList<ItemCardapio> lista = FXCollections.observableArrayList(cardapioDao.list());
		return lista;
	}

	private ObservableList<Cliente> getClientes() {
		ObservableList<Cliente> lista = FXCollections.observableArrayList(clienteDao.list());
		return lista;
	}

	private ObservableList<Entregador> getEntregadores() {
		ObservableList<Entregador> lista = FXCollections.observableArrayList(entregadorDao.list());
		return lista;
	}

	private ObservableList<Pedido> getPedidos() {
		ObservableList<Pedido> lista = FXCollections.observableArrayList(pedidoDao.list());
		return lista;
	}
	
	public void tabelaConfig() {
		tableView = new TableViewController<Pedido>(Pedido.class, "cliente", "entregador", "pagamento", "data", "pendenteEntrega");
		grid_pane.getChildren().add(tableView);
		tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 1)
					tableEventUpdate();
			}
		});
	}
	
	private void tableEventUpdate() {
		Pedido pedido = tableView.getSelectionModel().getSelectedItem();
		if (pedido != null) {
			textFieldID.setText("" + pedido.getId());
		}
	}

	
	private void updateTable() {
		tableView.getItems().clear();
		tableView.getItems().addAll(getPedidos());
	}

	private void montarNotaFiscal(ItemCardapio item) {
		Text text1 = new Text(item.getNome().toUpperCase() + " ------------------ " + item.getPreco() + " R$ \n");
		text1.setFill(Color.BLACK);
		text1.setFont(Font.font("Helvetica", FontPosture.ITALIC, 10));
		nota.setTextAlignment(TextAlignment.LEFT);
		nota.setPrefSize(200, 270);
		nota.setLineSpacing(1.0);
		ObservableList<Node> list = nota.getChildren();
		list.add(text1);
	}

	private void concluirNfe() {
		double a = itensCardapio.stream().mapToDouble(ItemCardapio::getPreco).sum();
		Text ttlNfe = new Text("\n\n\n\n TOTAL ------------------ " + a + " R$");
		ttlNfe.setFill(Color.BLACK);
		ttlNfe.setFont(Font.font("Helvetica", FontPosture.ITALIC, 10));
		ttlNfe.setTextAlignment(TextAlignment.CENTER);
		nota.getChildren().add(ttlNfe);
	}

}
