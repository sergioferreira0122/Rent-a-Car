package aluguer.veiculos.aluguerveiculos.Utils;

import aluguer.veiculos.aluguerveiculos.Preferences.Preferences;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Json {
    public static String preferencesToJson(Preferences preferences) {
        Gson gson = new Gson();
        return gson.toJson(preferences);
    }

    public static Preferences jsonToPreferences(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Preferences.class);
    }

    public static Preferences readPreferences() {
        try {
            Gson gson = new Gson();
            FileReader fileReader = new FileReader("preferences.json");

            return gson.fromJson(fileReader, Preferences.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void savePreferences(Preferences preferences) {
        try {
            FileWriter writer = new FileWriter("preferences.json");
            String json = preferencesToJson(preferences);
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}