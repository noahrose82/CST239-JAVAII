package app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

// Custom Exception for File Handling
class CustomFileException extends Exception {
    public CustomFileException(String message) {
        super(message);
    }
}

// File Service to Load JSON Data
class FileService {
    public static ArrayList<SalableProduct> loadInventory(String filePath) throws CustomFileException {
        try {
            // Read JSON file into a String
            String json = new String(Files.readAllBytes(Paths.get(filePath)));

            // Create Gson with custom configuration to handle polymorphic types
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(SalableProduct.class, new SalableProductDeserializer())
                    .create();

            // Deserialize the JSON into a list of SalableProduct objects (including subclasses)
            return gson.fromJson(json, new TypeToken<ArrayList<SalableProduct>>() {}.getType());
        } catch (IOException e) {
            throw new CustomFileException("Error loading inventory file: " + e.getMessage());
        }
    }
}
