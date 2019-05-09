import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;

public class CityList {

    public static void main(String[] args) {
        Gson gson = new Gson();
        try{
            JsonReader reader = new JsonReader(new FileReader("data/city.list.json"));
            JsonArray list = gson.fromJson(reader, JsonArray.class);

            String cityName = "";

            System.out.println(list.size());

            for(JsonElement e : list) {
                JsonObject j = e.getAsJsonObject();
                cityName = j.get("name").getAsString();
                //System.out.println(cityName);
            }




        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Done");
    }
}
