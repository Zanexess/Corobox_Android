package me.labs.corobox.corobox.model.realm.common;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class IntegerWrap extends RealmObject {
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
