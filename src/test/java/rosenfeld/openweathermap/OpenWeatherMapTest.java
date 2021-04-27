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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        OpenWeatherMapService service = retrofit.create(OpenWeatherMapService.class);

        Single<OpenWeatherMapFeed> single = service.getCurrentWeather("New York");
        //DO NOT USE BLOCKING GET
        OpenWeatherMapFeed feed = single.blockingGet();

        // then
        assertNotNull(feed);
        assertNotNull(feed.main);
        assertFalse(feed.main.toString().isEmpty());
    }


}
