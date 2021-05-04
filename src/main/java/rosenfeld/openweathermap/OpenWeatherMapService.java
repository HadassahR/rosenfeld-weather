package rosenfeld.openweathermap;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherMapService {

    @GET("http://api.openweathermap.org/data/2.5/weather?APPID=248bda26f84412a243d9772c10198b1c&units=imperial")
    Single<OpenWeatherMapFeed> getCurrentWeather(
            @Query("q") String location,
            @Query("units") String units);

    @GET("http://api.openweathermap.org/data/2.5/forecast?appid=248bda26f84412a243d9772c10198b1c")
    Single<OpenWeatherMapForecast> getWeatherForecast(
            @Query("q") String location,
            @Query("units") String units
    );
}