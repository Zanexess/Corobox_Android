package me.labs.corobox.corobox.model.realm;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Box extends RealmObject {

    @PrimaryKey
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("stored_timestamp")
    private Long dateCreated;
    @SerializedName("till")
    private Long dateTill;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("category")
    private Category category;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
