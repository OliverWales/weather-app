package WeatherApp;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    JPanelWBI homeP, destP;

    // size of phone screen
    private int pHeight = 1920/3;
    private int pWidth = 1080/3;

    static final String cities = "data/citylistFixed.txt"; // file containing previous searches
    private List<String> locations; // previous searches loaded into a List
    JComboBox homeBox, destBox;

    MainScreen(String h, String d) throws Exception{
        home = h;
        dest = d;

        homeForecast = Weather.getCurrentWeather(h);
        destForecast = Weather.getCurrentWeather(d);
        // initialises combo box
        locations = Files.readAllLines(Paths.get(cities)); // read in history file

        homeBox = new JComboBox(locations.toArray());
        homeBox.setEditable(true);
        homeBox.setSelectedIndex(-1);
        homeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    changeHomeLocation((String) homeBox.getSelectedItem());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //AutoCompletion.enable(homeBox);

        destBox = new JComboBox(locations.toArray());
        destBox.setEditable(true);
        destBox.setSelectedIndex(-1);
        destBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    changeDestLocation((String) destBox.getSelectedItem());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //AutoCompletion.enable(destBox);

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
        MainScreen ui = new MainScreen("Cambridge,UK", "Oxford,UK");
    }

    /**  DONE COMMENTING **/
    public void initWeather(){
        // initialises weather types array
        weather = new ArrayList<>();
        weather.add("01d");
        weather.add("02d");
        weather.add("03d");
        weather.add("04d");
        weather.add("09d");
        weather.add("10d");
        weather.add("11d");
        weather.add("13d");
        weather.add("50d");
        weather.add("ND");
    }

    /**  DONE COMMENTING **/
    public void initBackgroundPanels() throws Exception{
        // array of images corresponding to types of weather
        BufferedImage[] images = new BufferedImage[10];
        // array of sources of images
        ArrayList<Path> files = new ArrayList<>();

        // adds all sources to file array
        Files.newDirectoryStream(Paths.get("data/backs"))
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
        fHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add keyboard listener for screen change
        fHome.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Key pressed code=" + e.getKeyCode() + ", char=" + e.getKeyChar());
                switch (e.getKeyCode()) {
                    case 38:
                        // up
                        try {
                            switchToHourFromDest();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case 40:
                        // down
                        break;
                    case 37:
                        // left
                        try {
                            switchToHourFromHome();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case 39:
                        // right
                        break;
                    default:
                        // other
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        // gets weather codes and gets forecasts
        String hWeatherCode = getHomeWeatherCode();
        String dWeatherCode = getDestWeatherCode();

        // gets index of home weather, creates clone of homePanel
        homeWeather = weather.indexOf(hWeatherCode);
        homeP = new JPanelWBI(panels[homeWeather]);

        // gets index of destination weather, creates clone of destPanel
        destWeather = weather.indexOf(dWeatherCode);
        destP = new JPanelWBI(panels[destWeather]);

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

    public void switchToHourFromHome() throws Exception{
        fHome.setVisible(false);
        HourByHour hourF = new HourByHour();
        hourF.create(home);
    }
    public void switchToHourFromDest() throws Exception{
        fHome.setVisible(false);
        HourByHour hourF = new HourByHour();
        hourF.create(dest);
    }

    public void changeHomePanel(String newHomeWeather) throws IOException{
        fHome.remove(homeP);
        fHome.remove(destP);
        homeWeather = weather.indexOf(newHomeWeather);
        homeP = new JPanelWBI(panels[homeWeather]);
        setUpHomePanel(homeP);
        fHome.add(homeP);
        fHome.add(destP);
        fHome.setVisible(true);
    }

    public void changeDestPanel(String newDestWeather) throws IOException{
        fHome.remove(homeP);
        fHome.remove(destP);
        destWeather = weather.indexOf(newDestWeather);
        destP = new JPanelWBI(panels[destWeather]);
        setUpDestPanel(destP);
        fHome.add(homeP);
        fHome.add(destP);
        fHome.setVisible(true);
    }

    public void changeHomeLocation(String newHomeLoc) throws IOException{
        home = newHomeLoc;
        homeForecast = Weather.getCurrentWeather(home);
        String hWeatherCode = getHomeWeatherCode();
        changeHomePanel(hWeatherCode);
    }

    public void changeDestLocation(String newDestLoc) throws IOException{
        dest = newDestLoc;
        destForecast = Weather.getCurrentWeather(dest);
        String dWeatherCode = getDestWeatherCode();
        changeDestPanel(dWeatherCode);
    }

    private void setUpHomePanel(JPanelWBI panel) throws IOException {
        panel.setLayout(new BorderLayout());

        JPanel searchBar = new JPanel();
        searchBar.add(homeBox, BorderLayout.CENTER);
        searchBar.setOpaque(false);
        panel.add(searchBar, BorderLayout.NORTH);

        JPanel centre = new JPanel();
        BufferedImage icon = getIcon(getHomeWeatherCode());
        JLabel image = new JLabel(new ImageIcon(icon));
        SJLabel temp = new SJLabel(homeForecast.getTemp() + "°C");
        temp.setForeground(Color.white);
        centre.add(image, BorderLayout.CENTER);
        centre.add(temp, BorderLayout.SOUTH);
        centre.setOpaque(false);
        panel.add(centre, BorderLayout.CENTER);

        JPanel cityName = new JPanel();
        SJLabel location = new SJLabel(home);
        location.setForeground(Color.white);
        cityName.add(location);
        cityName.setOpaque(false);
        panel.add(cityName, BorderLayout.SOUTH);
    }

    private void setUpDestPanel(JPanelWBI panel) throws IOException{
        panel.setLayout(new BorderLayout());

        JPanel searchBar = new JPanel();
        searchBar.add(destBox);
        searchBar.setOpaque(false);
        panel.add(searchBar, BorderLayout.NORTH);

        JPanel centre = new JPanel();
        BufferedImage icon = getIcon(getDestWeatherCode());
        JLabel image = new JLabel(new ImageIcon(icon));
        SJLabel temp = new SJLabel(destForecast.getTemp() + "°C");
        temp.setForeground(Color.white);
        centre.add(image, BorderLayout.CENTER);
        centre.add(temp, BorderLayout.SOUTH);
        centre.setOpaque(false);
        panel.add(centre, BorderLayout.CENTER);

        JPanel cityName = new JPanel();
        SJLabel location = new SJLabel(dest);
        location.setForeground(Color.white);
        cityName.add(location);
        cityName.setOpaque(false);
        panel.add(cityName, BorderLayout.SOUTH);
    }

    private BufferedImage getIcon(String weather) throws IOException {
        BufferedImage icon;
        icon = ImageIO.read(new File("data/icons/" + weather + ".png"));
        return icon;
    }

    private String getHomeWeatherCode(){
        return homeForecast.getIcon() + "d";
    }

    private String getDestWeatherCode(){
        return destForecast.getIcon() + "d";
    }
}