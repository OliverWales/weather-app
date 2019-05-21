package WeatherApp;

import javax.swing.*;
import java.awt.*;

import static java.awt.Font.BOLD;

public class SJLabel extends JLabel {
    Font font;
    public SJLabel(String text){
        super(text);
        font = new Font("Hiragino Sans GB", BOLD,32);
        setFont(font);
    }
    public void setTextSize(int size){
        font = new Font(font.getFontName(), BOLD, size);

    }
}