package me.labs.corobox.corobox.common.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import me.labs.corobox.corobox.model.realm.CategoryNumberModel;
import me.labs.corobox.corobox.model.realm.OrderModelTo;
import me.labs.corobox.corobox.model.realm.common.IdWrap;
import me.labs.corobox.corobox.model.realm.common.IntegerWrap;

public class CategoryNumberModelSerializer implements JsonSerializer<CategoryNumberModel> {
    @Override
    public JsonElement serialize(CategoryNumberModel src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        IdWrap idWrap = new IdWrap(src.getCategory().getCategory_id());
        jsonObject.add("category", context.serialize(idWrap));
        jsonObject.addProperty("number", src.getNumber().getCount());
        return jsonObject;
    }
}
