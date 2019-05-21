package WeatherApp;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JPanelWBI extends JPanel {

    private BufferedImage backgroundImage;
    private int height;
    private int width;

    // Some code to initialize the background image.
    // Here, we use the constructor to load the image. This
    // can vary depending on the use case of the panel.
    public JPanelWBI(BufferedImage image, double h, double w) throws IOException {
        backgroundImage = image;
        height = (int) h;
        width = (int) w;
    }

    public JPanelWBI(JPanelWBI original){
        backgroundImage = original.backgroundImage;
        width = original.width;
        height = original.height;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image.
        g.drawImage(backgroundImage, 0,0, width, height, 0, 0, backgroundImage.getWidth(), backgroundImage.getHeight(), null);

    }


}