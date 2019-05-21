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

// A wrapper class for JComboBox that automatically sets various settings and handles selections from drop down list.
// The drop down list contains a list of locations stored in citylist.txt
// The user's search is autocompleted to prevent typing in entire name.
public class CitySearchBar extends JComboBox implements ActionListener {
    static final String cities = "/home/archie/Documents/weather-app/data/citylist.txt"; // file containing cities
    private List<String> locations; // city file read into a List
    private String selectedLocation; // currently selected city

    public CitySearchBar() throws IOException {
        super((Files.readAllLines(Paths.get(cities))).toArray()); // create a new JComboBox
        locations = Files.readAllLines(Paths.get(cities)); // read in city file
        selectedLocation = null; // nothing is selected initially
        AutoCompletion.enable(this); // credits: http://www.orbital-computer.de/JComboBox/
        this.setEditable(true); // user can type into the search bar
        this.setSelectedIndex(-1); // search bar is empty initially
        this.addActionListener(this); // call this.actionPerformed() when a city is selected
    }

    // when a city is selected, update the currently selected location
    public void actionPerformed(ActionEvent e) {
        selectedLocation = (String) this.getSelectedItem();
    }

    // return the selected location
    public String getSelected() {
        return selectedLocation;
    }

}