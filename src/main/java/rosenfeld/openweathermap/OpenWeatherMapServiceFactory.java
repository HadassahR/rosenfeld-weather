package rosenfeld.openweathermap;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * creates open weather source classes using Retrofit
 */
public class OpenWeatherMapServiceFactory {

    public OpenWeatherMapService newInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        OpenWeatherMapService service = retrofit.create(OpenWeatherMapService.class);

        return service;
    }
}
