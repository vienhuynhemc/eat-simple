
package com.vientamthuong.eatsimple.wishlist;

public class Wishlist {
    private String id;
    private String name;
    private int priceP;
    private int priceS;
    private String idCustomer;
    private String img;
    private String size;
    private String nameSize;


    public Wishlist(String id, String name, int priceP, int priceS, String idCustomer, String img, String size, String nameSize) {
        this.id = id;
        this.name = name;
        this.priceP = priceP;
        this.priceS = priceS;
        this.idCustomer = idCustomer;
        this.img = img;
        this.size = size;
        this.nameSize = nameSize;
    }

    public Wishlist(){

    }

    public String getNameSize() {
        return nameSize;
    }

    public void setNameSize(String nameSize) {
        this.nameSize = nameSize;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getPriceS() {
        return priceS;
    }

    public void setPriceS(int priceS) {
        this.priceS = priceS;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
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
                ", priceP=" + priceP +
                ", priceS=" + priceS +
                ", idCustomer='" + idCustomer + '\'' +
                ", img='" + img + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
