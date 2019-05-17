package WeatherApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CitySearchBar extends JComboBox implements ActionListener {
    static final String cities = "../data/citylist.txt";
    private List<String> locations;
    private String selectedLocation;

    public CitySearchBar() throws IOException {
        super((Files.readAllLines(Paths.get(cities))).toArray());
        locations = Files.readAllLines(Paths.get(cities));
        selectedLocation = null;
        AutoCompletion.enable(this);
        this.setEditable(true);
        this.setSelectedIndex(-1);
        this.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        selectedLocation = (String) this.getSelectedItem();
    }

    public String getSelected() {
        return selectedLocation;
    }

}