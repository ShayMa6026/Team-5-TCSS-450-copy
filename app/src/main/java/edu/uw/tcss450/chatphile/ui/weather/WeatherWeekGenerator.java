package edu.uw.tcss450.chatphile.ui.weather;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Uladzimir Hanevich
 * Weater for one week (7 days) dummy data class. (includes 7 cards for each day)
 */

public final class WeatherWeekGenerator {
    /*
    * Instance Fields
    */
    private static final ArrayList<WeatherDay> DAYS;
    public static final int WEEK = 7;
    private static final String[] mockTemp = {"75", "20", "12", "55", "100", "Hot", "Cold"};
    private static final String[] mockWind = {"NW", "W", "S", "SW", "E", "SE", "NE"};
    private static final String[] mockSky = {"cloudy", "buggy..ish", "clear", "NoData", "Dark", "Scary", "NotSure"};

    private static final ArrayList<WeatherHour> HOURS;
    public static final int DAY = 24;
    private static final String[] mockHour = {"1 am", "2 am", "3 am", "4 am", "5 am", "6 am", "7 am", "8 am", "9 am", "10 am", "11 am", "12 am",
                                                "1 pm", "2 pm", "3 pm", "4 pm", "5 pm", "6 pm", "7 pm", "8 pm", "9 pm", "10 pm", "11 pm", "12 pm"};
    private static final String[] mockHourTemp = {"78 F", "12 F", "133 F", "45 F", "56 F", "68 F", "76 F", "89 F", "93 F", "103 F", "11 F", "12 F",
                                                      "78 F", "12 F", "133 F", "45 F", "56 F", "68 F", "76 F", "89 F", "93 F", "103 F", "11 F", "12 F"};



        // weather days  data construct
        static {
            DAYS = new ArrayList<>();

            for (int i = 0; i < WEEK; i++) {
                DAYS.add(new WeatherDay
                        .Builder(mockTemp[i], mockSky[i], mockWind[i])
                        .build());
            }
        }

        // weather hours data construct
        static {
            HOURS = new ArrayList<>();

            for (int i = 0; i < DAY; i++) {
                HOURS.add(new WeatherHour(mockHourTemp[i], mockHour[i]));
            }
        }

    /**
     * Empty private constructor.
     */
    private WeatherWeekGenerator() {}

    /**
     * Getter method to obtain the ArrayList of WeatherDay (Day Cards) objects.
     * @return ArrayList of WeatherDay objects (Cards)
     */
    public static List<WeatherDay> getWeatherList() { return DAYS; }

    /**
     * Getter method to obtain the ArrayList of WeatherHour (Hour Cards) objects.
     * @return ArrayList of WeatherHour objects (Cards)
     */
    public static List<WeatherHour> getWeatherHour() { return HOURS; }

} // WeatherWeekGenerator end