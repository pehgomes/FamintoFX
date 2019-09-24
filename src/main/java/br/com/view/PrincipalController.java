package br.com.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class PrincipalController {
	@FXML
	public void menuEntregadorClick() {
		BorderPane janela = obterJanela("EntregadorView.fxml");
		Main.mudarVisao(janela);
	}

	@FXML
	public void menuBebidaClick() {
		BorderPane janela = obterJanela("BebidaView.fxml");
		Main.mudarVisao(janela);
	}

	@FXML
	public void menuClienteClick() {
		BorderPane janela = obterJanela("ClienteView.fxml");
		Main.mudarVisao(janela);
	}

	@FXML
	public void menuPedidosClick() {
		BorderPane janela = obterJanela("PedidoView.fxml");
		Main.mudarVisao(janela);
		
	}

	private BorderPane obterJanela(String nomefxml) {
		BorderPane janela = null;
		try {
			janela = (BorderPane) FXMLLoader.load(PrincipalController.class.getResource(nomefxml));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return janela;

	}

	@FXML
	public void menuFecharClick() {
		System.exit(0);
	}

}
