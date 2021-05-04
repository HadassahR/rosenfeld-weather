package rosenfeld.openweathermap;


import io.reactivex.rxjava3.core.Single;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.*;

public class OpenWeatherMapTest {

    @Test
    public void getCurrentWeather(){

        // given
        OpenWeatherMapServiceFactory factory = new OpenWeatherMapServiceFactory();
        OpenWeatherMapService service = factory.newInstance();

        // when
        OpenWeatherMapFeed feed = service.getCurrentWeather("New York", "imperial")
                .blockingGet();

        // then
        assertNotNull(feed);
        assertNotNull(feed.main);
        assertEquals("New York", feed.name);
        assertTrue(feed.main.temp > 0);
        assertTrue(feed.main.temp < 150); // testing that is in imperial
        assertTrue(feed.dt > 0); // checking that date is coming in
    }

    @Test
    public void getWeatherForecast(){

        // given
        OpenWeatherMapServiceFactory factory = new OpenWeatherMapServiceFactory();
        OpenWeatherMapService service = factory.newInstance();

        //when
        OpenWeatherMapForecast forecast = service.getWeatherForecast("New York", "imperial")
                .blockingGet();

        // then
        assertNotNull(forecast);
        assertNotNull(forecast.list);
        assertFalse(forecast.list.isEmpty());
        assertTrue(forecast.list.get(0).dt > 0);
        assertNotNull(forecast.list.get(0).weather);
    }

    @Test
    public void getWeatherForecast_getForecastFor() {
        // given
        OpenWeatherMapServiceFactory factory = new OpenWeatherMapServiceFactory();
        OpenWeatherMapService service = factory.newInstance();
        OpenWeatherMapForecast forecast = service.getWeatherForecast("New York", "imperial")
                .blockingGet();

        // when
        OpenWeatherMapForecast.HourlyForecast hourlyForecast = forecast.getForecastFor(1);

        // then
        assertNotNull(hourlyForecast);
        assertEquals(11, hourlyForecast.getDate().getHours());
    }

}
