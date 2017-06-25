package me.labs.corobox.corobox.common.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import me.labs.corobox.corobox.model.realm.OrderModelFrom;
import me.labs.corobox.corobox.model.realm.OrderModelTo;

public class OrderModelFromSerializer implements JsonSerializer<OrderModelFrom> {
    @Override
    public JsonElement serialize(OrderModelFrom src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("till", src.getTill());
        jsonObject.addProperty("order_id", src.getOrder_id());
        jsonObject.add("address", context.serialize(src.getAddressModel()));
        jsonObject.add("stuff", context.serialize(src.getCategoryNumberModel()));
        return jsonObject;
    }
}
