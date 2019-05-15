package WeatherApp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.google.gson.*;

public class Weather {
    private static final String API_KEY = "29b749c13d1e7d87c0c2fbd19b771a4f";

    // Takes a location string of form "Cambridge,UK", returns json string
    public static Forecast getCurrentWeather(String location) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + API_KEY;

        try {
            URLConnection connection = (new URL(url)).openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(br.readLine(), JsonObject.class);
            System.out.println(jsonObject);

            return new Forecast(jsonObject);
        } catch (Exception e) {
            System.out.println("Error getting weather data: " + e);
            System.out.println(url);
            return null;
        }
    }

    // Get forecast object for next few days
    public static JsonObject getForecastObject(String location) {
        String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + location + "&appid=" + API_KEY;

        try {
            URLConnection connection = (new URL(url)).openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(br.readLine(), JsonObject.class);

            return jsonObject;
        } catch (Exception e) {
            System.out.println("Error getting weather data: " + e);
            System.out.println(url);
            return null;
        }
    }

    // get 3 hr forecast list for next 24 hrs
    public static ArrayList<Forecast> getNextDayForecast(JsonObject jsonObject) {
        JsonArray forecastArray = jsonObject.getAsJsonArray("list");
        ArrayList<Forecast> forecasts = new ArrayList<>();

        for(int i = 0; i < 8; i++) {
            JsonElement jsonElement = forecastArray.get(i);
            Forecast forecast = new Forecast(jsonElement.getAsJsonObject());
            forecasts.add(forecast);
        }

        return forecasts;
    }

    // get daily forecast
    public static ArrayList<Forecast> getNextWeekForecast(JsonObject jsonObject) {
        JsonArray forecastArray = jsonObject.getAsJsonArray("list");
        ArrayList<Forecast> forecasts = new ArrayList<>();

        for(int i = 0; i < 40; i++) {
            JsonElement jsonElement = forecastArray.get(i);
            Forecast forecast = new Forecast(jsonElement.getAsJsonObject());
            if(forecast.getDate().getHours() <= 13 && forecast.getDate().getHours() > 10) {
                System.out.println(jsonElement.getAsJsonObject());
                forecasts.add(forecast);
            }
        }

        return forecasts;
    }

    // Example usage
    public static final void main(String[] args) {
        try {
            // Get current weather
            System.out.println("Current:");
            Forecast current = getCurrentWeather("Cambridge,UK");
            System.out.println(current);
            System.out.println("");

            // Get forecast object
            JsonObject forecastTest = getForecastObject("Cambridge,UK");

            // Get next 24hrs forecast (8 elements, every 3hrs)
            System.out.println("Day:");
            ArrayList<Forecast> nextDay = getNextDayForecast(forecastTest);
            for(Forecast f : nextDay) {
                System.out.println(f);
            }
            System.out.println("");

            // Get next week forecast
            System.out.println("Week:");
            ArrayList<Forecast> nextWeek = getNextWeekForecast(forecastTest);
            for(Forecast f : nextWeek) {
                System.out.println(f);
            }

        } catch (Exception e) {
            System.out.println("Error connecting to URL. " + e);
        }
    }
}