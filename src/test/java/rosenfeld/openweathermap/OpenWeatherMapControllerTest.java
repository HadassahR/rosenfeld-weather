package rosenfeld.openweathermap;

import io.reactivex.rxjava3.core.Single;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;

public class OpenWeatherMapControllerTest {

    private OpenWeatherMapController controller;
    OpenWeatherMapService service;

    @BeforeClass
    public static void beforeClass() {
        com.sun.javafx.application.PlatformImpl.startup(() -> {
        });
    }

    private void givenOpenWeatherMapController() {
        OpenWeatherMapServiceFactory factory = new OpenWeatherMapServiceFactory();
        OpenWeatherMapService svc = factory.newInstance();

        service = mock(OpenWeatherMapService.class);
        controller = new OpenWeatherMapController(service);
        controller.celsius = mock(RadioButton.class);
        controller.fahrenheit = mock(RadioButton.class);
        controller.enterLocation = mock(TextField.class);
        controller.changeLocation = mock(Button.class);
        controller.currentTemp = mock(Label.class);
        controller.currentDay = mock(Label.class);
        controller.currentIcon = mock(ImageView.class);
        controller.days = mock(ArrayList.class);
        controller.daysTemp = mock(ArrayList.class);
        controller.daysIcon = mock(ArrayList.class);

        controller.units = Arrays.asList(
                mock(RadioButton.class),
                mock(RadioButton.class));
    }

    @Test
    public void initialize() {
        //given
        givenOpenWeatherMapController();

        // when
        controller.initialize();

        // then
        verify(controller.units.get(0)).setToggleGroup(controller.unitGroup);
        verify(controller.units.get(1)).setToggleGroup(controller.unitGroup);
        verify(controller.celsius).setSelected(true);
    }

    @Test
    public void getCurrentWeather() {
        // given
        givenOpenWeatherMapController();
        doReturn(Single.never()).when(service).getCurrentWeather("New York", "imperial");
        doReturn(Single.never()).when(service).getWeatherForecast("New York", "imperial");
        doReturn("New York").when(controller.enterLocation).getText();
        doReturn(true).when(controller.units.get(1)).isSelected();

        // when
        controller.getWeather();

        // then
        verify(service).getCurrentWeather("New York", "imperial");
    }

    @Test
    public void metricUnits_getWeather() {

        // given
        givenOpenWeatherMapController();
        doReturn(Single.never()).when(service).getCurrentWeather("New York", "imperial");
        doReturn(Single.never()).when(service).getWeatherForecast("New York", "imperial");
        doReturn("New York").when(controller.enterLocation).getText();
        doReturn(true).when(controller.units.get(0)).isSelected();


        controller.celsius.setSelected(true);
        controller.fahrenheit.setSelected(false);

        //when
        controller.getWeather();

        // then
        verify(controller.celsius).setSelected(true);
        verify(controller.fahrenheit).setSelected(false);
    }

    @Test
    public void imperialUnits_getWeather() {
        // given
        givenOpenWeatherMapController();
        doReturn(Single.never()).when(service).getCurrentWeather("New York", "imperial");
        doReturn(Single.never()).when(service).getWeatherForecast("New York", "imperial");
        doReturn("New York").when(controller.enterLocation).getText();
        doReturn(true).when(controller.units.get(1)).isSelected();


        controller.celsius.setSelected(false);
        controller.fahrenheit.setSelected(true);

        //when
        controller.getWeather();

        // then
        verify(controller.celsius).setSelected(false);
        verify(controller.fahrenheit).setSelected(true);

    }

    @Test
    public void onOpenWeatherMapFeed() {
        // given
        givenOpenWeatherMapController();
        OpenWeatherMapFeed feed = mock(OpenWeatherMapFeed.class);
        feed.main = mock(OpenWeatherMapFeed.Main.class);
        feed.main.temp = 67.5;
        feed.weather = Arrays.asList(
                mock(OpenWeatherMapFeed.Weather.class),
                mock(OpenWeatherMapFeed.Weather.class)
        );

        doReturn("67.5").when(controller.currentTemp).getText();
        doReturn("Current Day").when(controller.currentDay).getText();
        doReturn("http://openweathermap.org/img/wn/04n@2x.png").when(feed.weather.get(0)).getIconUrl();
        controller.currentTemp.setText("67.5");
        controller.currentDay.setText("Current Day");
        controller.currentIcon.setImage(new Image("http://openweathermap.org/img/wn/04n@2x.png"));

        // when
        controller.onOpenWeatherMapFeed(feed);

        //then
        verify(controller.currentTemp).setText(String.valueOf(feed.main.temp));
        verify(controller.currentDay).setText("Current Day");
        verify(controller.currentIcon).setImage(any(Image.class));
    }

}


