package app;

import com.google.gson.*;
import java.lang.reflect.Type;

// Custom deserializer for SalableProduct to handle polymorphic deserialization
class SalableProductDeserializer implements JsonDeserializer<SalableProduct> {
    @Override
    public SalableProduct deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Check which type of product it is by looking for a field unique to each subclass
        if (jsonObject.has("damage")) {
            return context.deserialize(jsonObject, Weapon.class); // Deserialize as Weapon
        } else if (jsonObject.has("defense")) {
            return context.deserialize(jsonObject, Armor.class); // Deserialize as Armor
        } else if (jsonObject.has("healingAmount")) {
            return context.deserialize(jsonObject, Health.class); // Deserialize as Health
        } else {
            throw new JsonParseException("Unknown type of SalableProduct");
        }
    }
}
