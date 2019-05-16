/*
 * Credits to:
 * Oracle - ComboBoxDemo - https://docs.oracle.com/javase/tutorial/uiswing/components/combobox.html
 *
 */
package WeatherApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class App extends JPanel implements ActionListener {
    private JLabel temperature, type, description;
    private Forecast currentLocation;
    private List<String> locations;
    private JComboBox patternList;

    public App() throws IOException {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        locations = Files.readAllLines(Paths.get("WeatherApp/data/history.txt"));

        //Set up the UI for selecting a pattern.
        JLabel patternLabel1 = new JLabel("Enter your destination: ");
        patternList = new JComboBox(locations.toArray());
        patternList.setEditable(true);
        patternList.setSelectedIndex(-1);
        patternList.addActionListener(this);

        //Create the UI for displaying result.
        JLabel temperatureLabel = new JLabel("Temperature", JLabel.LEADING);
        JLabel typeLabel = new JLabel("Type", JLabel.LEADING);
        JLabel descriptionLabel = new JLabel("Description", JLabel.LEADING);
        temperature = new JLabel(" ");
        temperature.setForeground(Color.black);
        temperature.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black),
                BorderFactory.createEmptyBorder(5,5,5,5)
        ));

        type = new JLabel(" ");
        type.setForeground(Color.black);
        type.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black),
                BorderFactory.createEmptyBorder(5,5,5,5)
        ));

        description = new JLabel(" ");
        description.setForeground(Color.black);
        description.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black),
                BorderFactory.createEmptyBorder(5,5,5,5)
        ));

        //Lay out everything.
        JPanel patternPanel = new JPanel();
        patternPanel.setLayout(new BoxLayout(patternPanel,
                BoxLayout.PAGE_AXIS));
        patternPanel.add(patternLabel1);
        patternList.setAlignmentX(Component.LEFT_ALIGNMENT);
        patternPanel.add(patternList);

        JPanel resultPanel = new JPanel(new GridLayout(0, 1));
        resultPanel.add(temperatureLabel);
        resultPanel.add(temperature);
        resultPanel.add(typeLabel);
        resultPanel.add(type);
        resultPanel.add(descriptionLabel);
        resultPanel.add(description);

        patternPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        resultPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(patternPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(resultPanel);

        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

    }

    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        String newSelection = (String)cb.getSelectedItem();
        currentLocation = Weather.getCurrentWeather(newSelection);
        temperature.setForeground(Color.black);
        type.setForeground(Color.black);
        description.setForeground(Color.black);
        temperature.setText(Double.toString(currentLocation.getTemp()));
        type.setText(currentLocation.getType());
        description.setText(currentLocation.getDesc());

        if (!locations.contains(newSelection)) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("WeatherApp/data/history.txt", true));
                writer.write(newSelection + '\n');
                writer.close();
                locations.add(newSelection);
                patternList.addItem(newSelection);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    private static void createAndShowGUI() throws IOException {
        //Create and set up the window.
        JFrame frame = new JFrame("Weather App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new App();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                createAndShowGUI();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
