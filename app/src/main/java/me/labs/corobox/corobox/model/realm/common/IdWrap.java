package me.labs.corobox.corobox.model.realm.common;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

public class IdWrap {
    private String category_id;

    public IdWrap(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }
}
