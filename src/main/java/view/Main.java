package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import util.ConnectionFactory;

public class Main extends Application {
	
	private static Stage stageApplication = new Stage();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			stageApplication = primaryStage;
			BorderPane janela = (BorderPane) FXMLLoader.load(getClass().getResource("PrincipalView.fxml"));
			Scene cenario = new Scene(janela, 900, 400);
			cenario.getStylesheets().add(getClass().getResource("design.css").toExternalForm());
			stageApplication.setScene(cenario);
			stageApplication.setTitle("FamintoFX 1.0");
			stageApplication.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void mudarVisao(Pane pane) {
		Scene cenario = new Scene(pane, 900, 400);
		cenario.getStylesheets().add(Main.class.getResource("design.css").toExternalForm());
		stageApplication.setScene(cenario);
		stageApplication.show();
	}
	
	public static void main(String[] args) {
		Main.launch(args);
		ConnectionFactory.close();
	}
}