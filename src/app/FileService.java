package app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

class CustomFileException extends Exception {
    private static final long serialVersionUID = 1L;

    public CustomFileException(String message) {
        super(message);
    }
}

class FileService {
    public static ArrayList<SalableProduct> loadInventory(String filePath) throws CustomFileException {
        try {
            String json = new String(Files.readAllBytes(Paths.get(filePath)));

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(SalableProduct.class, new SalableProductDeserializer())
                    .create();

            return gson.fromJson(json, new TypeToken<ArrayList<SalableProduct>>() {}.getType());
        } catch (IOException e) {
            throw new CustomFileException("Error loading inventory file: " + e.getMessage());
        }
    }
}
