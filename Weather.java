import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import com.google.gson.*;

public class Weather {
    private static final String API_KEY = "29b749c13d1e7d87c0c2fbd19b771a4f";
    private static final double ZERO_Celcius = 273.15;

    // Takes a location string of form "Cambridge,UK", returns json string
    public static String getCurrentWeatherString(String location) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + API_KEY;

        try {
            URLConnection connection = (new URL(url)).openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            return br.readLine();

        } catch (Exception e) {
            System.out.println("Error getting weather data: " + e);
            System.out.println(url);
            return null;
        }
    }

    // Takes json string, returns temperature
    public static double getTemp(String json) {
        Gson gson = new Gson();
        JsonObject gsonObject = gson.fromJson(json, JsonObject.class);
        JsonElement main = gsonObject.get("main");
        double temp = main.getAsJsonObject().get("temp").getAsDouble() - ZERO_Celcius;
        return Math.round(temp * 10)/10.0;
    }

    // Takes json string, returns weather type
    public static String getType(String json) {
        Gson gson = new Gson();
        JsonObject gsonObject = gson.fromJson(json, JsonObject.class);
        JsonArray weather = gsonObject.getAsJsonArray("weather");
        return weather.get(0).getAsJsonObject().get("main").getAsString();
    }

    // Example usage
    public static final void main(String[] args) {
        Weather weather = new Weather();
        final String LOCATION1 = "Cambridge,UK";
        final String LOCATION2 = "London,UK";

        try {
            String json1 = getCurrentWeatherString(LOCATION1);
            String json2 = getCurrentWeatherString(LOCATION2);

            System.out.println(getTemp(json1)+ "°C, " + getType(json1) + " in " + LOCATION1); //LOCATION1.substring(0, LOCATION1.length() - 3));
            System.out.println(getTemp(json2)+ "°C, " + getType(json2) + " in " + LOCATION2); //LOCATION2.substring(0, LOCATION2.length() - 3));
        } catch (Exception e) {
            System.out.println("Error connecting to URL. " + e);
        }


    }
}