package me.labs.corobox.corobox.model.realm.common;

public class UuidWrap {
    private String uuid;

    public UuidWrap(String category_id) {
        this.uuid = category_id;
    }

    public String getCategory_id() {
        return uuid;
    }

    public void setCategory_id(String category_id) {
        this.uuid = category_id;
    }
}
