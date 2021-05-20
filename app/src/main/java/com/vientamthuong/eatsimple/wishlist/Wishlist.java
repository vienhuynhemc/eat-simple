package com.vientamthuong.eatsimple.wishlist;

public class Wishlist {
    private String name;
    private String desP;
    private int priceP;
    private int quantity;
    private int img;

    public Wishlist(String name, String desP, int priceP, int quantity, int img) {
        this.name = name;
        this.desP = desP;
        this.priceP = priceP;
        this.quantity = quantity;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesP() {
        return desP;
    }

    public void setDesP(String desP) {
        this.desP = desP;
    }

    public int getPriceP() {
        return priceP;
    }

    public void setPriceP(int priceP) {
        this.priceP = priceP;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
