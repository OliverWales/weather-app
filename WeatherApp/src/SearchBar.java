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

public class SearchBar extends JComboBox implements ActionListener {
    static final String history = "/home/archie/Documents/weather-app/WeatherApp/data/history.txt";
    private List<String> locations;
    private String selectedLocation;

    public SearchBar() throws IOException {
        super((Files.readAllLines(Paths.get(history))).toArray());
        locations = Files.readAllLines(Paths.get(history));
        selectedLocation = null;
        this.setEditable(true);
        this.setSelectedIndex(-1);
        this.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        selectedLocation = (String) this.getSelectedItem();
        if (!locations.contains(selectedLocation)) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(history, true));
                writer.write(selectedLocation + '\n');
                writer.close();
                locations.add(selectedLocation);
                this.addItem(selectedLocation);
            } catch (IOException ex) {
                System.out.println("[!] Can't find history file: WeatherApp/data/history.txt");
            }
        }
    }

    public String getSelected() {
        return selectedLocation;
    }
}