package com.vientamthuong.eatsimple.cartPage;

public class CartModel {
    private int image;
    private String title;
    private String content;
    private int price;
    private int number;

    public CartModel(int image, String title, String content, int price, int number) {
        this.image = image;
        this.title = title;
        this.content = content;
        this.price = price;
        this.number = number;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
