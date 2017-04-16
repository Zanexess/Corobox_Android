package me.labs.corobox.corobox.model;

public class Category {
    private String title;
    private Integer price;
    private String id;
    private Integer picture;

    public Category() {
    }

    public Category(String title, Integer price, String id, Integer picture) {
        this.title = title;
        this.price = price;
        this.id = id;
        this.picture = picture;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPicture() {
        return picture;
    }

    public void setPicture(Integer picture) {
        this.picture = picture;
    }
}
