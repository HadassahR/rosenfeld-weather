package rosenfeld.openweathermap;

import io.reactivex.rxjava3.core.Single;

public interface OpenWeatherMapService {

        @GET("api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=248bda26f84412a243d9772c10198b1c")
        Single<OpenWeatherMapFeed> getCurrentWeather();
    }

