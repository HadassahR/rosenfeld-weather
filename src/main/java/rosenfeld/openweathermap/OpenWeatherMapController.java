package rosenfeld.openweathermap;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenWeatherMapController {

    @FXML
    TableColumn tempColumn;

    @FXML
    TableColumn locationColumn;

    @FXML
    TableView<OpenWeatherMapFeed.Main> tableView;

    @FXML
    public void initialize() {

        tempColumn.setCellValueFactory(new PropertyValueFactory<OpenWeatherMapFeed.Main, String>("temp"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<OpenWeatherMapFeed.Main, String>("location"));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        OpenWeatherMapService service = retrofit.create(OpenWeatherMapService.class);

        Disposable disposable = service.getCurrentWeather("New York")
                // request the data in the background
                .subscribeOn(Schedulers.io())
                // work with the data in the foreground
                .observeOn(Schedulers.trampoline())
                // work with the feed whenever it gets downloaded
                .subscribe(this::onOpenWeatherMapFeed, this::onError);
    }

    public void onOpenWeatherMapFeed(OpenWeatherMapFeed feed) {
        tableView.getItems().setAll(feed.main);
        tableView.refresh();
    }

    public void onError(Throwable throwable) {
        // this is not the correct way to handle errors
        System.out.println("error occurred");
    }
}
