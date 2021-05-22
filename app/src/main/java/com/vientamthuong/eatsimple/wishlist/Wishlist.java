
package com.vientamthuong.eatsimple.wishlist;

public class Wishlist {
    private String name;
    private String desP;
    private int priceP;
    private int quantity;
    private String img;

    public Wishlist(String name, String desP, int priceP, int quantity, String img) {
        this.name = name;
        this.desP = desP;
        this.priceP = priceP;
        this.quantity = quantity;
        this.img = img;
    }

    public Wishlist(){

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "name='" + name + '\'' +
                ", desP='" + desP + '\'' +
                ", priceP=" + priceP +
                ", quantity=" + quantity +
                ", img='" + img + '\'' +
                '}';
    }
}
