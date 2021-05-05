package rosenfeld.openweathermap;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;
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


        // when
        controller.onOpenWeatherMapFeed(feed);

        //then
        verify(controller.currentTemp).setText(String.valueOf(feed.main.temp));
    }


    //    @Test
//    public void getCurrentWeather_getWeather() {
//        // given
//        givenOpenWeatherMapController();
//
//        controller.celsius.setSelected(false);
//        controller.fahrenheit.setSelected(true);
//        controller.enterLocation.setText("New York");
//
//        String units = controller.celsius.isSelected() ? "metric" : "imperial";
//
//        OpenWeatherMapServiceFactory factory = mock(OpenWeatherMapServiceFactory.class);
//        OpenWeatherMapService service = mock(OpenWeatherMapService.class);
//        OpenWeatherMapFeed feed = mock(OpenWeatherMapFeed.class);
//
//        //when
//        controller.getWeather();
//
//        // then - something to verify that onOpenWeatherFeed is called
//        verify(service).getCurrentWeather(controller.enterLocation.toString(), units);
//    }
}

