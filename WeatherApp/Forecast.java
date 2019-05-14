package WeatherApp;

import com.google.gson.JsonObject;
import java.util.Date;

class Forecast {
    private static final double ZERO_Celcius = 273.15;

    private Date date;
    private String type;
    private String desc;
    private String icon;
    private double temp;
    private double wind;

    Forecast (JsonObject weatherObject) {
        // Date
        this.date = new Date((long)weatherObject.get("dt").getAsLong() * 1000);

        // Type
        this.type = weatherObject.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main").getAsString();

        // Description
        this.desc = weatherObject.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString();

        // Icon code
        this.icon = weatherObject.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();

        // Avg. temp in deg. C.
        double temp = weatherObject.get("main").getAsJsonObject().get("temp").getAsDouble() - ZERO_Celcius;
        this.temp = Math.round(temp * 10)/10.0;

        // Wind speed
        this.wind = weatherObject.get("wind").getAsJsonObject().get("speed").getAsDouble();
    }

    @Override
    public String toString() {
        String s = date.toString() + " " + type + ", " + temp + "°C";
        return s;
    }

    public Date getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public String getIcon() {
        return icon;
    }

    public double getTemp() {
        return temp;
    }

    public double getWind() {
        return wind;
    }
}