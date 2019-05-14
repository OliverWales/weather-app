import WeatherApp.Weather;

import java.io.*;

public class RemoveInvalidLocations {
    public static void main(String[] args) throws IOException {
        File fout = new File("citylistFixed.txt");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    "/home/archie/Documents/weather-app/citylist.txt"));
            String line = reader.readLine();
            while (line != null) {
                if (Weather.getCurrentWeather(line) != null) bw.write(line + "\n");
                line = reader.readLine();
            }
            reader.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
