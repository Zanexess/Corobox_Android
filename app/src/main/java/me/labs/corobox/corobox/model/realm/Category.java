package me.labs.corobox.corobox.model.realm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

//{
//        "id": 1,
//        "category_id": "bycicle",
//        "image_url": "http://185.143.172.79:8000/static/categories/bycicle_q.png",
//        "title": "Велосипеды",
//        "description": "",
//        "max_weight": 10.0,
//        "monthly_price": 200
//}

@RealmClass
public class Category extends RealmObject {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private Integer id;
    @SerializedName("category_id")
    @Expose
    private String category_id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("monthly_price")
    @Expose
    private Integer price;
    @SerializedName("image_url")
    @Expose
    private String picture;
    @SerializedName("max_weight")
    @Expose
    private Float weight;

    public Category() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }
}
