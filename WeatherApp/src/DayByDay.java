package WeatherApp;

import com.google.gson.JsonObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class DayByDay implements ActionListener {

    //list of forecasts for next 5 days
    private ArrayList<Forecast> forecast;

    //panel info
    private static int Pwidth = 1080/3;
    private static int Pheight = 1920/3;
    private JFrame frame;

    public DayByDay create(String location) throws IOException {

        frame = createFrame();

        //creates a border layour screen panel
        JPanel screen = new JPanel();
        screen.setLayout(new BorderLayout());

        //creates a top panel containing the location and back button
        JPanel top = new JPanel(new GridLayout(1,0));
        top.add(createLocationPanel(location));

        //gets forecast for given location
        JsonObject locationForecast = Weather.getForecastObject(location);
        forecast = Weather.getNextWeekForecast(locationForecast);

        //creates a panels of weather info for each of the coming days
        JPanel forecasts = new JPanel(new GridLayout(0,1));
        for (int i = 0; i<=4; i++){
            forecasts.add(createDayPanel(i));
        }

        //adds the top and weather info panels to the screen and the screen to the frame
        screen.add(top, BorderLayout.NORTH);
        screen.add(forecasts, BorderLayout.CENTER);
        frame.add(screen);

        fshow();

        return this;
    }

    //sets up basic frame information
    private JFrame createFrame() {
        JFrame frame = new JFrame("5 Day Weather Breakdown");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(Pwidth,Pheight);
        frame.setPreferredSize(new Dimension(Pwidth,Pheight));
        return frame;
    }

    //shows the frame
    private void fshow(){
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    private Component createLocationPanel(String loc){

        JPanel panel = new JPanel();

        //adds a back button
        JButton back = new JButton("⇦");
        panel.add(back);
        back.addActionListener(this);
        back.setPreferredSize(new Dimension(10,30));

        //sets the colour and layout
        panel.setBackground(Color.orange);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        //ads a location label
        SJLabel location = new SJLabel(loc);
        panel.add(location);

        panel.setBounds(0,0, Pwidth, Pheight/6);

        return panel;

    }

    private JPanel createDayPanel(int day) throws IOException{

        //gets the forecast from the 5 day list
        Forecast todayForecast = forecast.get(day);

        //gets the unique weather code for the current weather
        String weatherCode = todayForecast.getIcon() + "d";

        //gets the unique colour and image associated with the weather code
        final Color colour = getColour(weatherCode);
        final BufferedImage icon = getIcon(weatherCode);

        //sets up panel with grid layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,0));

        //sets up labels for date, icon and temperature
        SJLabel date = new SJLabel(todayForecast.getDay()); // = getDay();
        JLabel image = new JLabel(new ImageIcon(icon));
        SJLabel temp = new SJLabel(todayForecast.getTemp()+ "°C"); // = getTemp();

        //sets the background colour and adds the labels to panel
        panel.setBackground(colour);
        panel.add(image);
        panel.add(date);
        panel.add(temp);

        panel.setBounds(0,(day + 1) * (Pheight / 6) - 15, Pwidth, (Pheight/6) - 15);

        return panel;

    }

    //purely for testing
    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try{
                    new DayByDay().create("Cambridge,UK").fshow();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

    //returns the unique icon based on weather code
    private BufferedImage getIcon(String weather) throws IOException {
        BufferedImage icon = null;
        icon = ImageIO.read(new File("src/data/icons/" + weather + ".png"));
        return icon;
    }

    //returns the unique colour baased on weather code
    private Color getColour(String weather) {
        Color col = null;

        if (weather.equals("11d")) { // thunderstorm
            col = new Color(42, 68, 71);
        }
        if (weather.equals("10d")) { // drizzle
            col = new Color(17, 104, 114);
        }
        if (weather.equals("09d")) { //rain
            col = new Color(92, 110, 112);
        }
        if (weather.equals("13d")) { //snow
            col = new Color(195, 227, 230);
        }
        if (weather.equals("01d")) { //clear
            col = new Color(50, 225, 238);
        }
        if (weather.equals("02d")) { //few clouds
            col = new Color(16, 180, 192);
        }
        if (weather.equals("03d")) { //scattered clouds
            col = new Color(53, 143, 149);
        }
        if (weather.equals("04d")) { //dark clouds
            col = new Color(86, 135, 142);
        }
        if (weather.equals("50d")) { //mist
            col = new Color(120, 139, 141);
        }

        return col;
    }

    // functionality for the button
    @Override
    public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
        UI.reInit();
    }


}
