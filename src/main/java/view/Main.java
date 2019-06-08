package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import util.ConnectionFactory;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane janela = (BorderPane) FXMLLoader.load(getClass().getResource("PrincipalView.fxml"));
			Scene cenario = new Scene(janela, 400, 400);
			cenario.getStylesheets().add(getClass().getResource("design.css").toExternalForm());
			primaryStage.setScene(cenario);
			primaryStage.setMaximized(true);
			primaryStage.setTitle("Sistema 1.0");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Main.launch(args);
		ConnectionFactory.close();
	}
}