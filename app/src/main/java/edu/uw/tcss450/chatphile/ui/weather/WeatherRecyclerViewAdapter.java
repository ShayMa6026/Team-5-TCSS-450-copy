package edu.uw.tcss450.chatphile.ui.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.chatphile.R;
import edu.uw.tcss450.chatphile.databinding.FragmentWeatherCardBinding;


/**
 * @author Uladzimir Hanevich
 * Class to handle the weather recycler view functionality.
 */
public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<WeatherRecyclerViewAdapter.WeatherCardViewHolder> {
    /*
     * mWeatherList instance field to represent a list of cards for a week
     */
    private final List<WeatherDay> mWeatherList;


    /**
     * Constructor weather list
     */
    public WeatherRecyclerViewAdapter(List<WeatherDay> items) {
        this.mWeatherList = items;
    }

    @NonNull
    @Override
    public WeatherCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherCardViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_weather_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherCardViewHolder holder, int position) {
        holder.setWeatherDay(mWeatherList.get(position));
    }

    @Override
    public int getItemCount() { return mWeatherList.size(); }



    /**
     * Inner helper class
     * Objects from this class represent an Individual row View (card) from the List
     * of rows in the Weather Recycler View.
     */
    public class WeatherCardViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentWeatherCardBinding binding;
        private WeatherDay mWeather;

        public WeatherCardViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentWeatherCardBinding.bind(view);
        }

        //setting the text fields on the weather card
        void setWeatherDay (final WeatherDay weather) {
            mWeather = weather;
            //setting the text and other fields in binding, which is single card fragment.
            binding.textWind.setText(weather.getWindDirection());
            binding.textSkyCondition.setText(weather.getSkyCondition());
            binding.textTemperature.setText(weather.getTemperature());
            binding.textDay.setText("DAY");

        }
    }

}
