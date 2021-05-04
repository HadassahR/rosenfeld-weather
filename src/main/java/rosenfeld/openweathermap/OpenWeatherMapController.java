package rosenfeld.openweathermap;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class OpenWeatherMapController {


    @FXML
    RadioButton celsius, fahrenheit;
    @FXML
    List<RadioButton> units;
    final ToggleGroup unitGroup = new ToggleGroup();
    @FXML
    TextField enterLocation;
    @FXML
    Button changeLocation;
    @FXML
    Label currentTemp, currentDay;
    @FXML
    ImageView currentIcon;
    @FXML
    ArrayList<Label> days;
    @FXML
    ArrayList<Label> daysTemp;
    @FXML
    ArrayList<ImageView> daysIcon;


    public void initialize() {
        for (RadioButton u : units) {
            u.setToggleGroup(unitGroup);
        }
        celsius.setSelected(true);
    }

    public void getWeather() {
        String units = celsius.isSelected() ? "metric" : "imperial";
        OpenWeatherMapServiceFactory factory = new OpenWeatherMapServiceFactory();
        OpenWeatherMapService service = factory.newInstance();

        Disposable disposableFeed = service.getCurrentWeather(enterLocation.getText(), units)
                // request the data in the background
                .subscribeOn(Schedulers.io())
                // work with the data in the foreground
                .observeOn(Schedulers.trampoline())
                // work with the feed whenever it gets downloaded
                .subscribe(this::onOpenWeatherMapFeed, this::onError);

        Disposable disposableForecast = service.getWeatherForecast(enterLocation.getText(), units)
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
                currentTemp.setText(String.valueOf(feed.main.temp));
                currentDay.setText("Current Weather");
                currentIcon.setImage(new Image(feed.weather.get(0).getIconUrl()));
            }
        });
    }

    public void onOpenWeatherMapForecast(OpenWeatherMapForecast forecast) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                int day = 1;
                for(Label textLabel : days) {
                    String date = forecast.getForecastFor(day).getDate().toString();
                    textLabel.setText(date.substring(0, date.indexOf("11")));
                    day++;
                }

                day = 1;
                for(Label weatherLabel : daysTemp) {
                    weatherLabel.setText(String.valueOf(forecast.getForecastFor(day).main.temp));
                    day++;
                }

                day = 1;
                for (ImageView icon : daysIcon) {
                    icon.setImage(new Image(forecast.getForecastFor(day).weather.get(0).getIconUrl()));
                    day++;
                }

            }
        });

    }

    public void onError(Throwable throwable) {
        // this is not the correct way to handle errors
        System.out.println("error in retrieving weather data");
    }


}
