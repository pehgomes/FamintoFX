package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrincipalController {
	@FXML
	public void menuCardapioClick() {
		Stage modal = new Stage();
		modal.initOwner(new Stage());
		modal.initModality(Modality.APPLICATION_MODAL);
		try {
			BorderPane janela = (BorderPane) FXMLLoader.load(getClass().getResource("crud/CardapioView.fxml"));
			Scene cenario = new Scene(janela, 900, 400);
			cenario.getStylesheets().add(getClass().getResource("design.css").toExternalForm());
			modal.setScene(cenario);
			modal.setTitle("Cadastro de Cardapios");
			modal.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void menuBebidaClick() {
		Stage modal = new Stage();
		modal.initOwner(new Stage());
		modal.initModality(Modality.APPLICATION_MODAL);
		try {
			BorderPane janela = (BorderPane) FXMLLoader.load(getClass().getResource("crud/BebidaView.fxml"));
			Scene cenario = new Scene(janela, 900, 400);
			cenario.getStylesheets().add(getClass().getResource("design.css").toExternalForm());
			modal.setScene(cenario);
			modal.setTitle("Cadastro de Bebida");
			modal.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void menuClienteClick() { 
		Stage modal = new Stage();
		modal.initOwner(new Stage());
		modal.initModality(Modality.APPLICATION_MODAL);
		try {
			BorderPane janela = (BorderPane) FXMLLoader.load(getClass().getResource("crud/ClienteView.fxml"));
			Main.mudarVisao(janela);
//			Scene cenario = new Scene(janela, 900, 400);
//			cenario.getStylesheets().add(getClass().getResource("design.css").toExternalForm());
//			modal.setScene(cenario);
//			modal.setTitle("Cadastro de Cardapios");
//			modal.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void menuFecharClick() {
		System.exit(0);
	}


	@FXML
	public void menuPedidosClick() {
	}
}
