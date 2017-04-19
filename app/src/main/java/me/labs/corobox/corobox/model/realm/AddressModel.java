package me.labs.corobox.corobox.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class AddressModel extends RealmObject {

    @PrimaryKey
    private String uuid;
    private String street;
    private String access;
    private String floor;
    private String flat;
    private boolean useAsDefault;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public boolean isUseAsDefault() {
        return useAsDefault;
    }

    public void setUseAsDefault(boolean useAsDefault) {
        this.useAsDefault = useAsDefault;
    }
}
