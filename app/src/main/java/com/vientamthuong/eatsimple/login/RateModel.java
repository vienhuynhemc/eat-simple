package com.vientamthuong.eatsimple.login;

import com.vientamthuong.eatsimple.date.DateTime;

public class RateModel {
    private String idCustomer;
    private String idDish;
    private String name;
    private int price;
    private int priceSale;
    private String idSize;
    private String nameSize;
    private int star;
    private String content;
    private DateTime dateRated;
    private String url;


    public RateModel() {

    }

    public RateModel(String idCustomer, String idDish, String name, int price, int priceSale, String idSize, String nameSize, int star, String content, DateTime dateRated, String url) {
        this.idCustomer = idCustomer;
        this.idDish = idDish;
        this.name = name;
        this.price = price;
        this.priceSale = priceSale;
        this.idSize = idSize;
        this.nameSize = nameSize;
        this.star = star;
        this.content = content;
        this.dateRated = dateRated;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getIdDish() {
        return idDish;
    }

    public void setIdDish(String idDish) {
        this.idDish = idDish;
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

    public String getIdSize() {
        return idSize;
    }

    public void setIdSize(String idSize) {
        this.idSize = idSize;
    }

    public String getNameSize() {
        return nameSize;
    }

    public void setNameSize(String nameSize) {
        this.nameSize = nameSize;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DateTime getDateRated() {
        return dateRated;
    }

    public void setDateRated(DateTime dateRated) {
        this.dateRated = dateRated;
    }

    @Override
    public String toString() {
        return "RateModel{" +
                "idCustomer='" + idCustomer + '\'' +
                ", idDish='" + idDish + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", priceSale=" + priceSale +
                ", idSize='" + idSize + '\'' +
                ", nameSize='" + nameSize + '\'' +
                ", star=" + star +
                ", content='" + content + '\'' +
                ", dateRated=" + dateRated +
                '}';
    }
}
