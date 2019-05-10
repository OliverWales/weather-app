


import javax.swing.*;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class testingshit {
    private JFrame frame;
    private static int Pwidth = 1080/3;
    private static int Pheight = 1920/3;

    private testingshit create() {
        frame = createFrame();
        frame.getContentPane().add(createHomeContent("snow"));
        frame.getContentPane().add(createHolContent("thunder"));

        return this;
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("Holiday Weather App");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setSize(Pwidth,Pheight);
        frame.setPreferredSize(new Dimension(Pwidth,Pheight));
        return frame;
    }

    private void show() {
        frame.pack();
//        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private Component createHomeContent(String weather) {
        final Image image = requestImage(weather);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
        };

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (String label : new String[]{"One", "Dois", "Drei", "Quatro", "Peace"}) {
            JButton button = new JButton(label);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(Box.createRigidArea(new Dimension(15, 15)));
//            JLabel test = new JLabel("test");
            panel.add(button);
//            panel.add(test);
        }

        panel.setBounds(0,0,Pwidth, Pheight/2);

        return panel;
    }
    private Component createHolContent(String weather) {
         Image image = requestImage(weather);

        JPanel holpanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, Pheight/2, null);
            }
        };

        holpanel.setBounds(0,Pheight/2, Pwidth, Pheight/2);
        holpanel.setLayout(new BoxLayout(holpanel,BoxLayout.PAGE_AXIS));
        holpanel.add(Box.createRigidArea(new Dimension(Pwidth,Pheight/2)));
            JLabel temp = new JLabel("21 C");
            temp.setAlignmentX(Component.CENTER_ALIGNMENT);
//            holpanel.add(Box.createRigidArea(new Dimension(15, 15)));
            holpanel.add(temp);




        return holpanel;
    }
    private Image requestImage(String weather) {
        Image image = null;

        try {
            if(weather == "thunder") {
                image = ImageIO.read(new File("src/thunder.jpg"));
            }
            else {
                image = ImageIO.read(new File("src/snow.jpg"));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new testingshit().create().show();
            }
        });
    }
}


