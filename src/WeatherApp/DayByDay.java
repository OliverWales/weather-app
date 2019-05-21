package WeatherApp;

import com.google.gson.JsonObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class DayByDay extends JPanel {
    private ArrayList<Forecast> forecast;
    private static int Pwidth = 1080/3;
    private static int Pheight = 1920/3;
    private JFrame frame;
    private DayByDay create() throws IOException {
        frame = createFrame();
        JPanel screen = new JPanel();
        screen.setLayout(new BorderLayout());
        JPanel top = new JPanel(new GridLayout(1,0));
        top.add(createLocationPanel());

        JsonObject locationForecast = Weather.getForecastObject("Cambridge,UK");

        forecast = Weather.getNextWeekForecast(locationForecast);
        JPanel forecasts = new JPanel(new GridLayout(0,1));
        for (int i = 0; i<=4; i++){
            forecasts.add(createDayPanel(i));
        }
        screen.add(top, BorderLayout.NORTH);
        screen.add(forecasts, BorderLayout.CENTER);
        frame.add(screen);
        return this;
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("Holiday Weather App");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(Pwidth,Pheight);
        frame.setPreferredSize(new Dimension(Pwidth,Pheight));
        return frame;
    }

    private void fshow(){
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }



    private Component createLocationPanel(){
        JPanel panel = new JPanel();
        JButton back = new JButton("⇦");
        panel.add(back);
        panel.setBackground(Color.orange);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        JLabel location = new JLabel("Cambridge");
        panel.add(Box.createRigidArea(new Dimension(20, 0)));
        panel.add(location);
        panel.setBounds(0,0, Pwidth, Pheight/6);
        return panel;

    }

    private JPanel createDayPanel(int day) throws IOException{
        Forecast todayForecast = forecast.get(day);
        String weatherCode = todayForecast.getIcon() + "d";
        final Color colour = getColour(weatherCode);
        final BufferedImage icon = getIcon(weatherCode);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,0));
        JLabel date = new JLabel(todayForecast.getDay()); // = getDay();
        JLabel image = new JLabel(new ImageIcon(icon));
        JLabel temp = new JLabel(todayForecast.getTemp()+ "°C"); // = getTemp();
        panel.setBackground(colour);
        panel.add(Box.createRigidArea(new Dimension(20, 0)));
        panel.add(image);
        panel.add(Box.createRigidArea(new Dimension(20, 0)));
        panel.add(date);
        panel.add(Box.createRigidArea(new Dimension(20, 0)));
        panel.add(temp);
        panel.setBounds(0,(day + 1) * (Pheight / 6) - 15, Pwidth, (Pheight/6) - 15);
        return panel;

    }

    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try{
                    new DayByDay().create().fshow();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private BufferedImage getIcon(String weather) throws IOException {
        BufferedImage icon = null;
        icon = ImageIO.read(new File("/home/archie/Documents/weather-app/data/icons/" + weather + ".png"));
        return icon;
    }

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


}
