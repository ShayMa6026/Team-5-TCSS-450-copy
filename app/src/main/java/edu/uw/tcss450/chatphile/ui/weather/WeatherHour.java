package edu.uw.tcss450.chatphile.ui.weather;

import java.io.Serializable;


public class WeatherHour {
    /*
       Instance Variables
       */
    private final String mTemperature ;
    //    private final String mTempSymbol;
    private final String mHour ;

    /**
     * WeatherHour constructor to construct new weatherHour (card) object
     */

    public WeatherHour(String temperature, String hour) {
        this.mHour = hour;
        this.mTemperature = temperature;


    }
//
//    /**
//     * Helper class for building Weather card
//     *
//     * @author Uladzimir Hanevich
//     */
//    public static class Builder {
//        private final String mTemperature;
//        //      private final String mTempSymbol;
//        private final String mSkyCondition;
//        private final String mWindDirection;
//
//
//        /**
//         * Constructs a new Builder.
//         *
//         * @param temp the temperature
//         * @param skyCondition the sky condition (cloudy or sunny)
//         * @param  windDirection the wind direction
//         */
//        public Builder(String temp, String skyCondition, String windDirection) {
//            this.mTemperature = temp;
////          this.mTempSymbol = symbol;
//            this.mSkyCondition = skyCondition;
//            this.mWindDirection = windDirection;
//        }
//
//        public WeatherDay build() {
//            return new WeatherDay(this);
//        }
//    }

//    /**
//     * Constructor for WeatherDay class
//     * @param builder
//     */
//    private WeatherDay(final WeatherDay.Builder builder) {
//        this.mTemperature = builder.mTemperature;
////      this.mTempSymbol = builder.mTempSymbol;
//        this.mSkyCondition = builder.mSkyCondition;
//        this.mWindDirection = builder.mWindDirection;
//    }


    /*
     * weather instance field getters
     */
    public String getTemperature() { return mTemperature; }

    public String getHour() {
        return mHour;
    }
}