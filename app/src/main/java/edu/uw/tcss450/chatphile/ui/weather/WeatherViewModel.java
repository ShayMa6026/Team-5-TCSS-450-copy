package edu.uw.tcss450.chatphile.ui.weather;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Uladzimir Hanevich
 * Class to handle Weather observer
 */
public class WeatherViewModel extends AndroidViewModel {
    /*
    Instance fields
     */
    private MutableLiveData<List<WeatherDay>> mWeather;
    private MutableLiveData<List<WeatherHour>> mHourWeather;

    /*
    Constructor
     */
    public WeatherViewModel(@NonNull Application application) {
        super(application);
        mWeather = new MutableLiveData<>();
        mWeather.setValue(new ArrayList<>());

        mHourWeather = new MutableLiveData<>();
        mHourWeather.setValue(new ArrayList<>());
    }

    public void addWeatherObserver(@NonNull LifecycleOwner owner,
                                   @NonNull Observer<? super List<WeatherDay>> observer) {
        mWeather.observe(owner, observer);
    }

    public void addWeatherHourObserver(@NonNull LifecycleOwner owner,
                                   @NonNull Observer<? super List<WeatherHour>> observer) {
        mHourWeather.observe(owner, observer);
    }
}