package com.vientamthuong.eatsimple.beans;

import com.vientamthuong.eatsimple.date.DateTime;

import java.util.Date;

public class MaGiamGia {
    private String magg;
    private int kieugg;
    private int giatri;
    private int solansudung;
    private int solansudungtoida;
    private DateTime hansudung;
    private DateTime ngaytao;
    private int tontai;

    public MaGiamGia() {
    }

    public MaGiamGia(String magg, int kieugg, int giatri, int solansudung, int solansudungtoida, DateTime hansudung, DateTime ngaytao, int tontai) {
        this.magg = magg;
        this.kieugg = kieugg;
        this.giatri = giatri;
        this.solansudung = solansudung;
        this.solansudungtoida = solansudungtoida;
        this.hansudung = hansudung;
        this.ngaytao = ngaytao;
        this.tontai = tontai;
    }

    public String getMagg() {
        return magg;
    }

    public void setMagg(String magg) {
        this.magg = magg;
    }

    public int getKieugg() {
        return kieugg;
    }

    public void setKieugg(int kieugg) {
        this.kieugg = kieugg;
    }

    public int getGiatri() {
        return giatri;
    }

    public void setGiatri(int giatri) {
        this.giatri = giatri;
    }

    public int getSolansudung() {
        return solansudung;
    }

    public void setSolansudung(int solansudung) {
        this.solansudung = solansudung;
    }

    public int getSolansudungtoida() {
        return solansudungtoida;
    }

    public void setSolansudungtoida(int solansudungtoida) {
        this.solansudungtoida = solansudungtoida;
    }

    public DateTime getHansudung() {
        return hansudung;
    }

    public void setHansudung(DateTime hansudung) {
        this.hansudung = hansudung;
    }

    public DateTime getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(DateTime ngaytao) {
        this.ngaytao = ngaytao;
    }

    public int getTontai() {
        return tontai;
    }

    public void setTontai(int tontai) {
        this.tontai = tontai;
    }

   public int convert(int tiencu){
        switch (kieugg){
            case 0:
                tiencu -= MaGiamGiaConfiguration.PHIVANCHUYEN;
                break;
            case 1:
                tiencu -= getGiatri();
                break;
            case 2:
                double tienmoi = (getGiatri()*tiencu)/100;
                tiencu -= (int) tienmoi;
                System.out.println("TIEN MOI: " + tienmoi);
                break;
            default:
                break;
        }
        return tiencu;
    }
    public String print(){
        switch (kieugg){
            case 0:
               return "Bạn được miễn phí chuyển";
            case 1:
                return "Bạn được giảm " + getGiatri() +" VND";
            case 2:
                return "Bạn được giảm " + getGiatri() + " %";

            default:
                return "Không xác định";
        }

    }

}
