package edu.uw.tcss450.chatphile.ui.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.uw.tcss450.chatphile.R;
import edu.uw.tcss450.chatphile.databinding.FragmentWeatherBinding;

/**
 * @author Uladzimir Hanevich
 * This fragment class is used to display the weather.
 */
public class WeatherFragment extends Fragment {
    private WeatherViewModel mModel;
    private WeatherViewModel mHourModel;
    //Empty constructor
    public WeatherFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(WeatherViewModel.class);
        mHourModel = new ViewModelProvider(getActivity()).get(WeatherViewModel.class);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // view binding variable
        FragmentWeatherBinding binding = FragmentWeatherBinding.bind(view);

        // Add hourly weather recycler view into weather fragment
//        mHourModel.addWeatherHourObserver(getViewLifecycleOwner(), weatherHoursList -> {
////            ((LinearLayoutManager)((RecyclerView) view).getLayoutManager())
//            ((LinearLayoutManager)binding.weatherHourly.getLayoutManager())
//                    .setOrientation(LinearLayoutManager.HORIZONTAL);
//
//                binding.weatherHourly.setAdapter(
//                        new WeatherHourRecyclerViewAdapter(WeatherWeekGenerator.getWeatherHour())
//                );
//
//        });

        //last update
        mHourModel.addWeatherHourObserver(getViewLifecycleOwner(), weatherHoursList -> {
            // Set a LinearLayoutManager with horizontal orientation to the weatherHourly RecyclerView
            binding.weatherHourly.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
            // Set the adapter for the weatherHourly RecyclerView
            binding.weatherHourly.setAdapter(new WeatherHourRecyclerViewAdapter(WeatherWeekGenerator.getWeatherHour()));
        }); //end last update

        //Add daily weather recycler view into weather fragment
//        mModel.addWeatherObserver(getViewLifecycleOwner(), weatherList -> binding.weatherDaily.setAdapter(
//                new WeatherRecyclerViewAdapter(WeatherWeekGenerator.getWeatherList())
//        ));

        //last update
        mModel.addWeatherObserver(getViewLifecycleOwner(), weatherList -> {
            // Set a LinearLayoutManager with vertical orientation to the weatherDaily RecyclerView
            binding.weatherDaily.setLayoutManager(new LinearLayoutManager(requireContext()));
            // Set the adapter for the weatherDaily RecyclerView
            binding.weatherDaily.setAdapter(new WeatherRecyclerViewAdapter(WeatherWeekGenerator.getWeatherList()));
        }); //end last update

//        binding.layoutWait.setVisibility(View.GONE);


//        binding.addLocationButton.setOnClickListener(button -> {
//            Navigation.findNavController(requireView()).navigate(
//                edu.uw.tcss450.chatphile.ui.weather.MapsFragment.actionNavigationWeatherFragmentToMapsFragment()
//                    );
//        });
    }

}