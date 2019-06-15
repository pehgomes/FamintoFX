package view.crud;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import view.Main;
import view.PrincipalController;

public abstract class AbstractController {

	
	public void voltar(ActionEvent actionEvent) {
		try {
			BorderPane janela = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("view/PrincipalView.fxml"));
			Main.mudarVisao(janela);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	protected abstract void initialize();

}
