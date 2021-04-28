package rosenfeld.openweathermap;

import java.util.Date;
import java.util.List;

public class OpenWeatherMapFeed {
    Main main;
    String name;
    long dt;

    public static class Main {
        double temp;
    }

    public Date getTime() {
        return new Date(dt * 1000);
    }

}