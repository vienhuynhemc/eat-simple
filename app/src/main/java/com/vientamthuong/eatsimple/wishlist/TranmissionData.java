package com.vientamthuong.eatsimple.wishlist;

public class TranmissionData {
    private String id;
    private String name;
    private int price;
    private int priceSale;
    private int numberSaled;
    private int numberRest;
    private int kcal;
    private int time;
    private String information;
    private String url;


    public TranmissionData(String id, String name, int price, int priceSale, int numberSaled, int numberRest, int kcal, int time, String information, String url) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.priceSale = priceSale;
        this.numberSaled = numberSaled;
        this.numberRest = numberRest;
        this.kcal = kcal;
        this.time = time;
        this.information = information;
        this.url = url;
    }

    public TranmissionData() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(int priceSale) {
        this.priceSale = priceSale;
    }

    public int getNumberSaled() {
        return numberSaled;
    }

    public void setNumberSaled(int numberSaled) {
        this.numberSaled = numberSaled;
    }

    public int getNumberRest() {
        return numberRest;
    }

    public void setNumberRest(int numberRest) {
        this.numberRest = numberRest;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
