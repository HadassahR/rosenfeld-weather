package rosenfeld.openweathermap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OpenWeatherMapApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/weather_application.fxml"));

        Parent parent = loader.load();
        Scene scene = new Scene(parent, 500, 300);

        stage.setTitle("Weather");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}