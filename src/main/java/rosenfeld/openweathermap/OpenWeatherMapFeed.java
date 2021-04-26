package rosenfeld.openweathermap;

import java.util.List;

public class OpenWeatherMapFeed {
    List<Main> mains;

    public static class Main {
        MainProperties properties;

        public double getTemp() {
            return properties.temp;
        }
    }

    public static class MainProperties {
        double temp;
    }


}