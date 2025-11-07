package oop.calihat.mainmenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainmenuApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/oop/calihat/mainmenu/Mainmenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("SchedMoTo - Main Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
