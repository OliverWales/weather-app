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

class MainScreen {
    // Holds home and destination forecasts
    private Forecast homeForecast;
    private Forecast destForecast;

    // Holds panel & weather types
    JPanelWBI[] panels;
    ArrayList<String> weather;

    // Holds current home & destination
    String home;
    String dest;

    // indices of current weather codes
    int homeWeather;
    int destWeather;

    // frames for each screen
    static JFrame fHome;
    JFrame fDay;
    JFrame fWeek;

    // size of phone screen
    private int pHeight = 1920/3;
    private int pWidth = 1080/3;


    MainScreen(String h, String d) throws Exception{
        home = h;
        dest = d;
        // initialises weather array & panels
        initWeather();
        initBackgroundPanels();

        // initialises home frame
        initHome();

        // initialises day frame


        // initialises week frame

    }

    /**  DONE COMMENTING **/
    public static void main(String[] args) throws Exception{
        MainScreen ui = new MainScreen("ND", "ND");
        ui.initHome();
    }

    /**  DONE COMMENTING **/
    public void initWeather(){
        // initialises weather types array
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
    }

    /**  DONE COMMENTING **/
    public void initBackgroundPanels() throws Exception{
        // array of images corresponding to types of weather
        BufferedImage[] images = new BufferedImage[10];
        // array of sources of images
        ArrayList<Path> files = new ArrayList<>();

        // adds all sources to file array
        Files.newDirectoryStream(Paths.get("src/data/backs"))
                .forEach(x -> files.add(x));

        // sorts file array so that indices correspond to weather correctly
        Collections.sort(files,
                new Comparator<Path>() {public int compare(Path f1, Path f2){return f1.toString().compareTo(f2.toString()); }});

        // creates buffered image objects from files

        for (int i = 0; i<10; i++){
            images[i] = ImageIO.read(files.get(i).toFile());
        }

        // array of panels with background images corresponding to weather types
        panels = new JPanelWBI[10];
        for (int i = 0; i<10; i++){
            panels[i] = new JPanelWBI(images[i],pHeight/2, pWidth);
        }
    }
    static public void reInit(){
        fHome.setVisible(true);
    }
    /**  DONE COMMENTING **/
    public void initHome() throws IOException{
        // creates home frame & sets size to size of phone
        fHome = new JFrame();
        fHome.setSize(pWidth,pHeight);
        fHome.setLayout(new GridLayout(2,1));

        // gets weather codes and gets forecasts
        String hWeatherCode = getHomeWeatherCode();
        String dWeatherCode = getDestWeatherCode();

        // gets index of home weather, creates clone of homePanel
        homeWeather = weather.indexOf(hWeatherCode);
        JPanelWBI homeP = new JPanelWBI(panels[homeWeather]);

        // gets index of destination weather, creates clone of destPanel
        destWeather = weather.indexOf(dWeatherCode);
        JPanelWBI destP = new JPanelWBI(panels[destWeather]);

        // sets up the homePanel & destination panel
        if (!hWeatherCode.equals("ND")){
            setUpHomePanel(homeP);
        }
        if (!dWeatherCode.equals("ND")){
            setUpDestPanel(destP);
        }

        //
        fHome.add(homeP);
        fHome.add(destP);
        fHome.setVisible(true);
    }

    public void changeHome(String newHomeWeather) throws IOException{
        fHome.removeAll();
        homeWeather = weather.indexOf(newHomeWeather);
        JPanelWBI homeP = new JPanelWBI(panels[homeWeather]);
        JPanelWBI destP = new JPanelWBI(panels[destWeather]);
        setUpHomePanel(homeP);
        fHome.add(homeP);
        fHome.add(destP);
        fHome.setVisible(true);
    }

    public void changeDest(String newDestWeather) throws IOException{
        fHome.removeAll();
        destWeather = weather.indexOf(newDestWeather);
        JPanelWBI homeP = new JPanelWBI(panels[homeWeather]);
        JPanelWBI destP = new JPanelWBI(panels[destWeather]);
        setUpDestPanel(destP);
        fHome.add(homeP);
        fHome.add(destP);
        fHome.setVisible(true);
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
        icon = ImageIO.read(new File("src/data/icons/" + weather + ".png"));
        return icon;
    }

    private String getHomeWeatherCode(){
        String hWeatherCode;
        if (home.equals("ND")){
            homeForecast = null;
            hWeatherCode = "ND";
        }
        else{
            homeForecast = Weather.getCurrentWeather(home);
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
