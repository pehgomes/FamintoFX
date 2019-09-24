package br.com.view;

import br.com.util.ConnectionFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static Stage stageApplication = new Stage();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			stageApplication = primaryStage;
			BorderPane janela = (BorderPane) FXMLLoader.load(getClass().getResource("PrincipalView.fxml"));
			Scene cenario = new Scene(janela);
			cenario.getStylesheets().add(getClass().getResource("design.css").toExternalForm());
			stageApplication.setScene(cenario);
			stageApplication.setTitle("FamintoFX 1.0");
			stageApplication.setMaxWidth(1150);
			stageApplication.setMaxHeight(740);
			stageApplication.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void mudarVisao(Pane pane) {
		if (pane == null) {
			Main main2 = new Main();
			main2.start(stageApplication);
			return ;
		}
		
		Scene cenario = new Scene(pane);
		cenario.getStylesheets().add(Main.class.getResource("design.css").toExternalForm());
		stageApplication.setScene(cenario);
		stageApplication.setMaxWidth(1150);
		stageApplication.setMaxHeight(740);
		stageApplication.setWidth(1150);
		stageApplication.setHeight(740);
		stageApplication.show();
	}
	
	public static void main(String[] args) {
		Main.launch(args);
		ConnectionFactory.close();
	}
}