package WeatherApp;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

class UI {
    private Forecast homeForecast;
    private Forecast destForecast;
    BufferedImage[] images;
    JPanel screen;
    ArrayList<Path> files;
    JPanelWBI[] panels;
    ArrayList<String> weather;
    String home;
    String dest;
    int homeWeather;
    JFrame f_comp;
    int destWeather;
    private int pHeight = 1920/3;
    private int pWidth = 1080/3;
    UI(String h, String d) throws Exception{
        weather = new ArrayList<>();
        weather.add("04d");
        weather.add("03d");
        weather.add("50d");
        weather.add("ND");
        weather.add("08d");
        weather.add("10d");
        weather.add("13d");
        weather.add("01d");
        weather.add("02d");
        weather.add("11d");

        f_comp.setSize(1080/3,1920/3);
        // hide JPanel by calling setVisible(false)
        screen = new JPanel();
        screen.setSize(1080/3, 1920/3);
        f_comp.add(screen);
        GridLayout experimentLayout = new GridLayout(2,1);
        screen.setLayout(experimentLayout);

        home = "Cambridge,UK";
        dest = "Oxford,UK";

        images = new BufferedImage[10];
        files = new ArrayList<>();
        Files.newDirectoryStream(Paths.get("/home/archie/Documents/weather-app/data/backs"))
                .forEach(x -> files.add(x));
        Collections.sort(files,
                new Comparator<Path>() {public int compare(Path f1, Path f2){return f1.toString().compareTo(f2.toString()); }});
        for (int i = 0; i<10; i++){
            images[i] = ImageIO.read(files.get(i).toFile());
        }
        panels = new JPanelWBI[10];
        for (int i = 0; i<10; i++){
            panels[i] = new JPanelWBI(images[i],1920/6, 1080/3);
        }
        /*
        init("rain");
        TimeUnit.SECONDS.sleep(1);
        changeDest("thunderstorm");
        TimeUnit.SECONDS.sleep(1);
        changeHome("mist");*/
        //test();
        /*for (JPanelWBI x : panels){
            f_comp.add(x);
            f_comp.setVisible(true);
            TimeUnit.SECONDS.sleep(2);
            f_comp.remove(x);
        }
        */
    }

    public static void main(String[] args) throws Exception{
        UI ui = new UI("ND", "ND");
        ui.init();
    }

    /*public void test() throws Exception{
        init();
        TimeUnit.SECONDS.sleep(2);
        changeHome("broken_cloud");
        changeDest("cloud");
        TimeUnit.SECONDS.sleep(2);
        changeHome("mist");
        changeDest("ND");
        TimeUnit.SECONDS.sleep(2);
        changeHome("rain");
        changeDest("shower");
        TimeUnit.SECONDS.sleep(2);
        changeHome("snow");
        changeDest("sun");
        TimeUnit.SECONDS.sleep(2);
        changeHome("sun_and_cloud");
        changeDest("thunderstorm");
    }*/

    public void init() throws IOException{
        String hWeatherCode = getHomeWeatherCode();
        String dWeatherCode = getDestWeatherCode();

        homeWeather = weather.indexOf(hWeatherCode);
        JPanelWBI homeP = new JPanelWBI(panels[homeWeather]);
        destWeather = weather.indexOf(dWeatherCode);
        JPanelWBI destP = new JPanelWBI(panels[destWeather]);

        setUpHomePanel(homeP);
        setUpDestPanel(destP);

        screen.add(homeP);
        screen.add(destP);
        f_comp.add(screen);
        f_comp.setVisible(true);
    }

    public void changeHome(String newHomeWeather) throws IOException{
        screen.removeAll();
        homeWeather = weather.indexOf(newHomeWeather);
        JPanelWBI homeP = new JPanelWBI(panels[homeWeather]);
        JPanelWBI destP = new JPanelWBI(panels[destWeather]);
        setUpHomePanel(homeP);
        screen.add(homeP);
        screen.add(destP);
        f_comp.setVisible(true);
    }

    public void changeDest(String newDestWeather) throws IOException{
        screen.removeAll();
        destWeather = weather.indexOf(newDestWeather);
        JPanelWBI homeP = new JPanelWBI(panels[homeWeather]);
        JPanelWBI destP = new JPanelWBI(panels[destWeather]);
        setUpDestPanel(destP);
        screen.add(homeP);
        screen.add(destP);
        f_comp.setVisible(true);
    }

    private void setUpHomePanel(JPanelWBI panel) throws IOException{
        panel.setLayout(new BorderLayout());
        JLabel location = new JLabel(home);
        BufferedImage icon = getIcon(getHomeWeatherCode());
        JLabel image = new JLabel(new ImageIcon(icon));
        JLabel temp = new JLabel(homeForecast.getTemp() + "°C");
        panel.add(location, BorderLayout.NORTH);
        panel.add(image, BorderLayout.CENTER);
        panel.add(temp, BorderLayout.SOUTH);

    }

    private void setUpDestPanel(JPanelWBI panel) throws IOException{
        panel.setLayout(new BorderLayout());
        JLabel location = new JLabel(dest);
        BufferedImage icon = getIcon(getDestWeatherCode());
        JLabel image = new JLabel(new ImageIcon(icon));
        JLabel temp = new JLabel(destForecast.getTemp() + "°C");
        panel.add(location, BorderLayout.NORTH);
        panel.add(image, BorderLayout.CENTER);
        panel.add(temp, BorderLayout.SOUTH);

    }

    private BufferedImage getIcon(String weather) throws IOException {
        BufferedImage icon = null;
        icon = ImageIO.read(new File("/home/archie/Documents/weather-app/data/icons/" + weather + ".png"));
        return icon;
    }

    private String getHomeWeatherCode(){
        String hWeatherCode;
        if (home.equals("ND")){
            homeForecast = null;
            hWeatherCode = "ND";
        }
        else{
            homeForecast = Weather.getCurrentWeather(
        f_comp = new JFrame();home);
            hWeatherCode = homeForecast.getIcon() + "d";
        }
        return hWeatherCode;
    }

    private String getDestWeatherCode(){
        String dWeatherCode;
        if(dest.equals("ND")){
            destForecast = null;
            dWeatherCode = "ND";
        }
        else{
            destForecast = Weather.getCurrentWeather(dest);
            dWeatherCode = destForecast.getIcon() + "d";
        }
        return dWeatherCode;
    }
}