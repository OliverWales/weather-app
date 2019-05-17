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


    protected HourByHour create(ArrayList<Forecast> Weather) throws IOException {
        frame = createFrame();
        forecast = Weather;
        myPanel = createPanel();
        frame.add(myPanel);
        return this;
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("Holiday Weather App");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(Pwidth, Pheight));
        return frame;
    }

    private JPanel createPanel() throws IOException {
        JPanel screen = new JPanel(new BorderLayout());

        JPanel top = new JPanel(new GridLayout(1, 0));
        // put all the top stuff in
        JButton back = new JButton("⇦");
        back.addActionListener(this);
        JLabel location = new JLabel("Cambridge");
        top.setBackground(Color.orange);
        top.add(back);
        top.add(location);
        screen.add(top, BorderLayout.NORTH);

        JPanel forecasts = new JPanel(new GridLayout(0, 1));
        int maxInADay = 8;
        JPanel[] panels = new JPanel[maxInADay];
        int i = 0;
        for (Forecast f : forecast) {

            panels[i] = new JPanel(new GridLayout(1, 0));
            // add everything to forecast panel
            JLabel icon = new JLabel(new ImageIcon(getIconImage(f.getIcon())));
            JLabel time = new JLabel(f.getTime());
            String temp = String.valueOf(f.getTemp());
            JLabel tempLabel = new JLabel(temp + " °C");
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

    private BufferedImage getIconImage(String weather) throws IOException {
        BufferedImage icon = null;
        icon = ImageIO.read(new File("../data/icons/" + weather + "d.png"));
        return icon;
    }

    private void show() {
        frame.pack();
        frame.setVisible(true);
    }

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new HourByHour().create(Weather.getNextDayForecast(Weather.getForecastObject("Cambridge"))).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        myPanel.setVisible(false);
    }
}
