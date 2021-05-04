package rosenfeld.openweathermap;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
    public void onOpenWeatherMapFeed() {

    }

    public void onOpenWeatherMapForecast() {

    }
}

