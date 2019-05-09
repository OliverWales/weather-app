import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import com.google.gson.*;

public class Weather {
    public static final void main(String[] args) {
        final String API_KEY = "29b749c13d1e7d87c0c2fbd19b771a4f";
        final String LOCATION1 = "Cambridge,UK";
        final String LOCATION2 = "Girton,UK";
        String url1 = "http://api.openweathermap.org/data/2.5/weather?q=" + LOCATION1 + "&appid=" + API_KEY;
        String url2 = "http://api.openweathermap.org/data/2.5/weather?q=" + LOCATION2 + "&appid=" + API_KEY;
        final double ZERO_Celcius = 273.15; // For conversion from Deg. Kelvin.

        System.out.println(url1);
        System.out.println(url2);

        try {
            URLConnection connection1 = (new URL(url1)).openConnection();
            URLConnection connection2 = (new URL(url2)).openConnection();
            BufferedReader br1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));
            BufferedReader br2 = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
            String line1 = br1.readLine();
            String line2 = br2.readLine();
            System.out.println(line1);
            System.out.println(line2);

            Gson gson = new Gson();
            JsonObject gsonObject1 = gson.fromJson(line1, JsonObject.class);
            JsonObject gsonObject2 = gson.fromJson(line2, JsonObject.class);
            JsonElement next1 = gsonObject1.get("main");
            JsonElement next2 = gsonObject2.get("main");

            double currentTemp1 = next1.getAsJsonObject().get("temp").getAsDouble() - ZERO_Celcius;
            double currentTemp2 = next2.getAsJsonObject().get("temp").getAsDouble() - ZERO_Celcius;
            long epochTime = gsonObject1.get("dt").getAsLong() * 1000;

            java.util.Date time = new java.util.Date(epochTime);
            System.out.println((Math.round(currentTemp1 * 10))/10.0 + "°C in Cambridge");
            System.out.println((Math.round(currentTemp2 * 10))/10.0 + "°C in Girton");
            System.out.println(time);

        } catch (Exception e) {
            System.out.println("Error connecting to URL. " + e);
        }


    }
}