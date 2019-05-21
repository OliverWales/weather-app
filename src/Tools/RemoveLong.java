package Tools;

import java.io.*;

public class RemoveLong {
    public static void main(String[] args) throws FileNotFoundException {
        File fout = new File("/home/archie/Documents/weather-app/data/citylistShortened.txt");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("/home/archie/Documents/weather-app/data/citylist.txt"));
            String line = reader.readLine();
            while (line != null) {
                if (line.length() < 13) bw.write(line + "\n");
                line = reader.readLine();
            }
            reader.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
