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
// The drop down list contains a list of the user's previous searches.
// This list is stored in a file which is updated upon entering a new search.
public class HistorySearchBar extends JComboBox implements ActionListener {
    static final String history = "/home/archie/Documents/weather-app/data/history.txt"; // file containing previous searches
    private List<String> locations; // previous searches loaded into a List
    private String selectedLocation; // currently selected city

    public HistorySearchBar() throws IOException {
        super((Files.readAllLines(Paths.get(history))).toArray()); // create a new JComboBox
        locations = Files.readAllLines(Paths.get(history)); // read in history file
        selectedLocation = null; // nothing is selected initially
        this.setEditable(true); // user can type into the search bar
        this.setSelectedIndex(-1); // search bar is empty initially
        this.addActionListener(this); // call this.actionPerformed() when a city is selected
    }

    public void actionPerformed(ActionEvent e) {
        selectedLocation = (String) this.getSelectedItem(); // update the selected location
        // check if user hasn't searched for the selected location before
        if (!locations.contains(selectedLocation)) {
            // if so, update our history file (append new search to the end) and update our history List
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(history, true));
                writer.write(selectedLocation + '\n');
                writer.close();
                locations.add(selectedLocation);
                this.addItem(selectedLocation);
            } catch (IOException ex) {
                System.out.println("[!] Can't find history file: " + history);
            }
        }
    }

    public String getSelected() {
        return selectedLocation;
    }

}