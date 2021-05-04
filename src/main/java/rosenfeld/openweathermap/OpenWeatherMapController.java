package rosenfeld.openweathermap;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class OpenWeatherMapController {


    @FXML
    RadioButton celsius;
    @FXML
    RadioButton fahrenheit;
    @FXML
    List<RadioButton> units;
    final ToggleGroup unitGroup = new ToggleGroup();
    @FXML
    TextField enterLocation;
    @FXML
    Button changeLocation;
    @FXML
    Label currentLocation;
    @FXML
    Label currentTemp;
    @FXML
    Label currentDateTime;
    @FXML
    ImageView currentIcon;
    @FXML
    ArrayList<Label> days;
    @FXML
    ArrayList<Label> daysTemp;
    @FXML
    ArrayList<ImageView> daysIcon;


    public void initialize() {
        initializeRadioButtons();

    }

    public void getWeather() {
        OpenWeatherMapServiceFactory factory = new OpenWeatherMapServiceFactory();
        OpenWeatherMapService service = factory.newInstance();

        Disposable disposableFeed = service.getCurrentWeather(enterLocation.getText(), "imperial")
                // request the data in the background
                .subscribeOn(Schedulers.io())
                // work with the data in the foreground
                .observeOn(Schedulers.trampoline())
                // work with the feed whenever it gets downloaded
                .subscribe(this::onOpenWeatherMapFeed, this::onError);

        Disposable disposableForecast = service.getWeatherForecast(enterLocation.getText(), "imperial")
                // request the data in the background
                .subscribeOn(Schedulers.io())
                // work with the data in the foreground
                .observeOn(Schedulers.trampoline())
                // work with the feed whenever it gets downloaded
                .subscribe(this::onOpenWeatherMapForecast, this::onError);
    }

    public void onOpenWeatherMapFeed(OpenWeatherMapFeed feed) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                currentLocation.setText(enterLocation.getText());
                currentTemp.setText(String.valueOf(feed.main.temp));
                currentDateTime.setText(String.valueOf(feed.getTime()));
                currentIcon.setImage(new Image(feed.weather.get(0).getIconUrl()));
            }
        });
    }

    public void onOpenWeatherMapForecast(OpenWeatherMapForecast forecast) {

//        for (int ix = 0; ix < daysTemp.size(); ix ++){
//            daysTemp.get(ix).setText(String.valueOf(forecast.getForecastFor(ix).main.temp));
//        }
//
//        for (int ix = 0; ix < days.size(); ix ++) {
//            String day = forecast.getForecastFor(ix).getDate().toString();
//            days.get(ix).setText(day.substring(0, day.indexOf(" ")));
//        }
//
//
//        for (int ix = 0; ix < daysIcon.size(); ix ++){
//            daysIcon.get(ix).setImage(new Image(forecast.getForecastFor(ix).weather.get(ix).getIconUrl()));
//        }
    }

    public void onError(Throwable throwable) {
        // this is not the correct way to handle errors
        System.out.println("error occurred");
    }

    private void initializeRadioButtons() {
        for (RadioButton u : units) {
            u.setToggleGroup(unitGroup);
        }
        celsius.setSelected(true);
    }

    public void changeUnit(MouseEvent mouseEvent) {
        // Check which unit is selected
        if (celsius.isSelected()) {
            // change to metric
        } else if (fahrenheit.isSelected()) {
            // change to imperial
        }
    }

}
