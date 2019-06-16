package view.crud;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import view.Main;

public abstract class AbstractController {

	@FXML
	public void voltar(ActionEvent actionEvent) {
		try {
			BorderPane janela = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("view/PrincipalView.fxml"));
			Main.mudarVisao(janela);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void chamarAlerta (String text) {
		Alert a = new Alert(AlertType.NONE);
		a.setAlertType(AlertType.WARNING);
		a.setContentText(text);
		a.show();
	}

	
	
	protected abstract void initialize();

}
