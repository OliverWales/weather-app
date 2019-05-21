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

public class HourByHour implements ActionListener {
    private JFrame frame;
    private JPanel myPanel;
    private static int Pwidth = 1080 / 3;
    private static int Pheight = 1920 / 3;
    private ArrayList<Forecast> forecast;

    //    creates the instance of the frame and everything that needs to go in the frame,
//    takes a location as input and generates the weather forecast
    public HourByHour create(String location) throws IOException {
        JsonObject locationForecast = Weather.getForecastObject(location);
        frame = createFrame();
        forecast = Weather.getNextDayForecast(locationForecast);
        myPanel = createPanel(location);
        frame.add(myPanel);
        show();
        return this;
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("The Next 24 Hours");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(Pwidth, Pheight));
        return frame;
    }

    private JPanel createPanel(String loc) throws IOException {
//        panel for the whole screen
        JPanel screen = new JPanel(new BorderLayout());

//        panel for the top information
        JPanel top = new JPanel(new GridLayout(1, 0));
        // back button to make panel disappear so to return to home page
        JButton back = new JButton("⇦");
        back.addActionListener(this);
        back.setPreferredSize(new Dimension(10,30));
        // diaplays current location at top of screen
        SJLabel location = new SJLabel(loc);
        top.setBackground(Color.orange);
        top.add(back);
        top.add(location);
        screen.add(top, BorderLayout.NORTH);

//        new panel for all the forecasts to be displayed in
        JPanel forecasts = new JPanel(new GridLayout(0, 1));
        int maxInADay = 8;
        JPanel[] panels = new JPanel[maxInADay];
        int i = 0;
//        cycles through forcast objects from input and creates a new panel for each hour summary
        for (Forecast f : forecast) {

            panels[i] = new JPanel(new GridLayout(1, 0));
            // add everything to forecast panel
            JLabel icon = new JLabel(new ImageIcon(getIconImage(f.getIcon())));
            SJLabel time = new SJLabel(f.getTime());
            String temp = String.valueOf(f.getTemp());
            SJLabel tempLabel = new SJLabel(temp + " °C");
            panels[i].setOpaque(true);
            panels[i].setBackground(requestColour(f.getIcon() + "d"));
            panels[i].add(icon);
            panels[i].add(time);
            panels[i].add(tempLabel);

            forecasts.add(panels[i]);
            i++;
        }

        screen.add(forecasts, BorderLayout.CENTER);

        return screen;
    }

    //  method for getting the necessary icon image
    private BufferedImage getIconImage(String weather) throws IOException {
        BufferedImage icon = null;
        icon = ImageIO.read(new File("src/data/icons/" + weather + "d.png"));
        return icon;
    }

    private void show() {
        frame.pack();
        frame.setVisible(true);
    }

    //    finds colour to be used as background
    private Color requestColour(String weather) {
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

    //    example main method to make it run on its own
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new HourByHour().create("Cambridge");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
