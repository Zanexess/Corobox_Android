package me.labs.corobox.corobox.common.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import me.labs.corobox.corobox.model.realm.Box;
import me.labs.corobox.corobox.model.realm.CategoryNumberModel;
import me.labs.corobox.corobox.model.realm.common.IdWrap;
import me.labs.corobox.corobox.model.realm.common.UuidWrap;

public class BoxSerializer implements JsonSerializer<Box> {
    @Override
    public JsonElement serialize(Box src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("uuid", src.getUuid());
        return jsonObject;
    }
}
