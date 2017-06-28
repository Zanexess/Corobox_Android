package me.labs.corobox.corobox.common.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import me.labs.corobox.corobox.model.realm.AddressModel;

public class AddressSerializer implements JsonSerializer<AddressModel> {
    @Override
    public JsonElement serialize(AddressModel src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("city", src.getCity());
        jsonObject.addProperty("address", src.getAddress());
        jsonObject.addProperty("access", src.getAccess());
        jsonObject.addProperty("floor", src.getFloor());
        jsonObject.addProperty("flat", src.getFlat());
        return jsonObject;
    }
}
