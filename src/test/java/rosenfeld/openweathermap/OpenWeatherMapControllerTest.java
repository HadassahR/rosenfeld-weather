package rosenfeld.openweathermap;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class OpenWeatherMapControllerTest {

    private OpenWeatherMapController controller;

    @BeforeClass
    public static void beforeClass() {
        com.sun.javafx.application.PlatformImpl.startup(() -> {
        });
    }

    private void givenOpenWeatherMapController() {
        OpenWeatherMapServiceFactory factory = new OpenWeatherMapServiceFactory();
        OpenWeatherMapService service = factory.newInstance();

        controller = new OpenWeatherMapController();
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
    public void getCurrentWeather_getWeather() {
        // given
        givenOpenWeatherMapController();

        controller.celsius.setSelected(false);
        controller.fahrenheit.setSelected(true);
        controller.enterLocation.setText("New York");

        String units = controller.celsius.isSelected() ? "metric" : "imperial";

        OpenWeatherMapServiceFactory factory = new OpenWeatherMapServiceFactory();
        OpenWeatherMapService service = factory.newInstance();
        OpenWeatherMapFeed feed = service.getCurrentWeather(controller.enterLocation.toString(), units)
                .blockingGet();

        //when
        controller.getWeather();

        //verify
//        verify(       ).onOpenWeatherMapFeed(feed);

    }

    @Test
    public void getWeatherForecast_getWeather() {
        // given
        givenOpenWeatherMapController();
        OpenWeatherMapServiceFactory factory = new OpenWeatherMapServiceFactory();
        OpenWeatherMapService service = factory.newInstance();
        controller.enterLocation.setText("New York");
        controller.fahrenheit.setSelected(true);

        //when
        service.getCurrentWeather(controller.enterLocation.toString(), controller.fahrenheit.toString());

        //verify

    } // to implement

    @Test
    public void onOpenWeatherMapFeed() {
        // given
        givenOpenWeatherMapController();
        OpenWeatherMapServiceFactory factory = new OpenWeatherMapServiceFactory();
        OpenWeatherMapService service = factory.newInstance();


        //when
        OpenWeatherMapFeed feed = service.getCurrentWeather(controller.enterLocation.toString(),
                controller.fahrenheit.toString())
                .blockingGet();


        // then
    } // to implement
    @Test
    public void onOpenWeatherMapForecast() {

    } // to implement
}

