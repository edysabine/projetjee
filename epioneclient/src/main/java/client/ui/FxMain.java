package client.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene; //  tchallasabine@gmail.com
import javafx.stage.Stage;

public class FxMain extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root=FXMLLoader.load(getClass().getResource("./Admin.fxml"));
		//Parent root=FXMLLoader.load(getClass().getResource("./Login.fxml"));
		Scene scene = new Scene(root);
        
        primaryStage.setTitle("Appointment Platform"); //titre de notre page
        primaryStage.setScene(scene);
        primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
