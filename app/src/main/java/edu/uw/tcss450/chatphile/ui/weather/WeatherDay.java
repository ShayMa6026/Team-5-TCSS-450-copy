package edu.uw.tcss450.chatphile.ui.weather;

import java.io.Serializable;


/**
 * @author Uladzimir Hanevich
 * Class to create a dummy weather for one day. (represents one card)
 */
public class WeatherDay implements Serializable {
    /*
       Instance Variables
       */
    private final String mTemperature;
//    private final String mTempSymbol;
    private final String mSkyCondition;
    private final String mWindDirection;

//    /**
//     * WeatherDay constructor to construct new wear herDay (card) object
//     */
//
//        public WeatherDay (final int theTemp, final String theTempSymbol, final String theSkyCondition, final String theWindDirection) {
//            this.mTemperature = theTemp;
//            this.mTempSymbol = theTempSymbol;
//            this.mSkyCondition = theSkyCondition;
//            this.mWindDirection = theWindDirection;
//
//        }

    /**
     * Helper class for building Weather card
     *
     * @author Uladzimir Hanevich
     */
    public static class Builder {
        private final String mTemperature;
//      private final String mTempSymbol;
        private final String mSkyCondition;
        private final String mWindDirection;


        /**
         * Constructs a new Builder.
         *
         * @param temp the temperature
         * @param skyCondition the sky condition (cloudy or sunny)
         * @param  windDirection the wind direction
         */
        public Builder(String temp, String skyCondition, String windDirection) {
            this.mTemperature = temp;
//          this.mTempSymbol = symbol;
            this.mSkyCondition = skyCondition;
            this.mWindDirection = windDirection;
        }

        public WeatherDay build() {
            return new WeatherDay(this);
            }
    }

    /**
     * Constructor for WeatherDay class
     * @param builder
     */
    private WeatherDay(final Builder builder) {
        this.mTemperature = builder.mTemperature;
//      this.mTempSymbol = builder.mTempSymbol;
        this.mSkyCondition = builder.mSkyCondition;
        this.mWindDirection = builder.mWindDirection;
    }


    /*
     * weather instance field getters
     */
    public String getTemperature() { return mTemperature; }
//   public String getmTempSymbol() { return "F"; }

    public String getSkyCondition () {
        return mSkyCondition;
    }

    public String getWindDirection () {
        return mWindDirection;
    }
}
