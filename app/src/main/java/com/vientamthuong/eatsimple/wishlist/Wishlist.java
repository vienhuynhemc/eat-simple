
package com.vientamthuong.eatsimple.wishlist;

public class Wishlist {
    private String id;
    private String name;
    private String desP;
    private int priceP;
    private String img;

    public Wishlist(String id, String name, String desP, int priceP, String img) {
        this.id = id;
        this.name = name;
        this.desP = desP;
        this.priceP = priceP;
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



    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desP='" + desP + '\'' +
                ", priceP=" + priceP +
                ", img='" + img + '\'' +
                '}';
    }
}
