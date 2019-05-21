package Demos;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import WeatherApp.CitySearchBar;
import WeatherApp.HistorySearchBar;

import java.awt.*;
import javax.swing.*;
import java.io.IOException;

// a demo to display how the SearchBar classes operate and how to use them
public class SearchBarDemo extends JPanel {

    public SearchBarDemo() throws IOException {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        //Set up the UI for selecting a location.
        JLabel locationLabel1 = new JLabel("History:");
        JLabel locationLabel2 = new JLabel("City:");

        HistorySearchBar locationList = new HistorySearchBar();
        CitySearchBar locationList2 = new CitySearchBar();

        //Lay out everything.
        JPanel locationPanel = new JPanel();
        locationPanel.setLayout(new BoxLayout(locationPanel,
                BoxLayout.PAGE_AXIS));
        locationPanel.add(locationLabel1);
        locationList.setAlignmentX(Component.LEFT_ALIGNMENT);
        locationPanel.add(locationList);

        locationPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel locationPanel2 = new JPanel();
        locationPanel2.setLayout(new BoxLayout(locationPanel2,
                BoxLayout.PAGE_AXIS));
        locationPanel2.add(locationLabel2);
        locationList2.setAlignmentX(Component.LEFT_ALIGNMENT);
        locationPanel2.add(locationList2);

        locationPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(locationPanel);
        add(locationPanel2);
        add(Box.createRigidArea(new Dimension(0, 10)));

        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

    } //constructor

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() throws IOException {
        //Create and set up the window.
        JFrame frame = new JFrame("SearchBar Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new SearchBarDemo();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

