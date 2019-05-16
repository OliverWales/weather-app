package WeatherApp;

import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;

class Forecast {
    private static final double ZERO_Celcius = 273.15;

    private Date date;
    private String type;
    private String desc;
    private String icon;
    private double temp;
    private double pressure;
    private double humidity;
    private double maxTemp;
    private double minTemp;
    private double windspeed;

    Forecast (JsonObject weatherObject) {
        JsonObject weather = weatherObject.get("weather").getAsJsonArray().get(0).getAsJsonObject();
        JsonObject main = weatherObject.get("main").getAsJsonObject();

        // Date
        this.date = new Date(weatherObject.get("dt").getAsLong() * 1000);

        // Type
        this.type = weather.get("main").getAsString();

        // Description
        this.desc = weather.get("description").getAsString();

        // Icon code
        this.icon = weather.get("icon").getAsString().substring(0, 2);

        // Avg. temp in deg. C.
        double temp = main.get("temp").getAsDouble() - ZERO_Celcius;
        this.temp = Math.round(temp * 10)/10.0;

        // Air pressure
        this.pressure = main.get("pressure").getAsDouble();

        // Humidity
        this.humidity = main.get("pressure").getAsDouble();

        // Min Temp
        this.minTemp = main.get("temp_min").getAsDouble();

        // Max Temp
        this.maxTemp = main.get("temp_max").getAsDouble();

        // Wind speed
        this.windspeed = weatherObject.get("wind").getAsJsonObject().get("speed").getAsDouble();
    }

    @Override
    public String toString() {
        String s = date.toString() + " " + type + " (" + desc + "), " + temp + "°C";
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

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getWindspeed() {
        return windspeed;
    }

    public String getDay(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        return sdf.format(date);
    }

    public String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("ha");
        return sdf.format(date);
    }
}