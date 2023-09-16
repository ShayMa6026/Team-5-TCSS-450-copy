package edu.uw.tcss450.chatphile.ui.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.chatphile.R;
import edu.uw.tcss450.chatphile.databinding.FragmentWeatherHourCardBinding;

public class WeatherHourRecyclerViewAdapter extends RecyclerView.Adapter<WeatherHourRecyclerViewAdapter.WeatherHourViewHolder> {

    /*
     * mWeatherHours instance field to represent a list of cards for hours
     */
    private final List<WeatherHour> mWeatherHours;

    /**
     * Constructor weather Hours
     */
    public WeatherHourRecyclerViewAdapter(List<WeatherHour> items) {
        this.mWeatherHours = items;
    }

    @NonNull
    @Override
    public WeatherHourRecyclerViewAdapter.WeatherHourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherHourRecyclerViewAdapter.WeatherHourViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_weather_hour_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherHourRecyclerViewAdapter.WeatherHourViewHolder holder, int position) {
        holder.setWeatherHour(mWeatherHours.get(position));
    }

    @Override
    public int getItemCount() { return mWeatherHours.size(); }


    /**
     * Inner helper class
     * Objects from this class represent an Individual row View (card) from the List
     * of rows in the Weather Recycler View.
     */
    public class WeatherHourViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentWeatherHourCardBinding binding;
        private WeatherHour mWeather;

        public WeatherHourViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentWeatherHourCardBinding.bind(view);
        }

        //setting the text fields on the weather card
        void setWeatherHour(final WeatherHour weather) {
            mWeather = weather;
            //setting the text and other fields in binding, which is single card fragment.
            binding.textTemperatureHour.setText(weather.getTemperature());
            binding.textTimeHour.setText(weather.getHour());

        }
    }

}
