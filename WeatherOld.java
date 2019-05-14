package WeatherApp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import com.google.gson.*;

public class Weather {
    private static final String API_KEY = "29b749c13d1e7d87c0c2fbd19b771a4f";
    private static final double ZERO_Celcius = 273.15;

    // Takes a location string of form "Cambridge,UK", returns json string
    public static JsonObject getCurrentWeatherObject(String location) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + API_KEY;

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

    // Takes json string, returns temperature
    public static double getTemp(JsonObject jsonObject) {
        if (jsonObject == null) return Double.NEGATIVE_INFINITY;
        JsonElement main = jsonObject.get("main");
        double temp = main.getAsJsonObject().get("temp").getAsDouble() - ZERO_Celcius;
        return Math.round(temp * 10)/10.0;
    }

    // Takes json string, returns weather type
    public static String getType(JsonObject jsonObject) {
        if (jsonObject == null) return "null";
        JsonArray weather = jsonObject.getAsJsonArray("weather");
        return weather.get(0).getAsJsonObject().get("main").getAsString();
    }

    // Takes json string, returns weather description
    public static String getDesc(JsonObject jsonObject) {
        if (jsonObject == null) return "null";
        JsonArray weather = jsonObject.getAsJsonArray("weather");
        return weather.get(0).getAsJsonObject().get("description").getAsString();
    }

    // Example usage
    public static final void main(String[] args) {
        final String LOCATION1 = "Cambridge,UK";
        final String LOCATION2 = "Girton,UK";
        final String LOCATION3 = "London,UK";

        try {
            JsonObject json1 = getCurrentWeatherObject(LOCATION1);
            JsonObject json2 = getCurrentWeatherObject(LOCATION2);
            JsonObject json3 = getCurrentWeatherObject(LOCATION3);

            System.out.println(json1);

            System.out.println(getTemp(json1)+ "°C, " + getType(json1) + " (" + getDesc(json1) + ") in " + LOCATION1.substring(0, LOCATION1.length() - 3));
            System.out.println(getTemp(json2)+ "°C, " + getType(json2) + " (" + getDesc(json2) + ") in "  + LOCATION2.substring(0, LOCATION2.length() - 3));
            System.out.println(getTemp(json3)+ "°C, " + getType(json3) + " (" + getDesc(json3) + ") in "  + LOCATION3.substring(0, LOCATION2.length() - 3));
        } catch (Exception e) {
            System.out.println("Error connecting to URL. " + e);
        }
    }
}
