package me.labs.corobox.corobox.common.serializers;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import me.labs.corobox.corobox.model.realm.AddressModel;
import me.labs.corobox.corobox.model.realm.OrderModelTo;

public class OrderModelToSerializer implements JsonSerializer<OrderModelTo> {
    @Override
    public JsonElement serialize(OrderModelTo src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("order_id", src.getOrderId());
        jsonObject.addProperty("till", src.getTill());
        jsonObject.addProperty("paid_till", src.getPaid_till());
        jsonObject.add("address", context.serialize(src.getAddressModel()));
        jsonObject.add("order", context.serialize(src.getCategoryNumberModel()));
        return jsonObject;
    }
}
