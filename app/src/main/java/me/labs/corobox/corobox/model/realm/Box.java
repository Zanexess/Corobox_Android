package me.labs.corobox.corobox.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class Box extends RealmObject {
    private String title;
    private Long dateCreated;
    private Long dateTill;
    private Integer price;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getDateTill() {
        return dateTill;
    }

    public void setDateTill(Long dateTill) {
        this.dateTill = dateTill;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
