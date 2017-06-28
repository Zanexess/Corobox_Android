package me.labs.corobox.corobox.model.realm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import me.labs.corobox.corobox.model.realm.common.IntegerWrap;

public class CategoryNumberModel extends RealmObject {
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("number")
    @Expose
    Integer number;

    public Category getCategory() {
        return category;
    }

    public Integer getNumber() {
        return number;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
