package com.vientamthuong.eatsimple.admin.model;

import android.graphics.Bitmap;

import com.vientamthuong.eatsimple.date.DateTime;

import java.util.ArrayList;
import java.util.List;

public class SanPham {

    private String maSanPham;
    private String tenSanPham;
    private DateTime ngayTao;
    private List<Size> sizes;
    private DanhMucSanPham danhMucSanPham;
    private boolean chonXoa;
    private String url;
    private String hinh_fb;
    private Bitmap hinh;
    private int kcal;
    private int thoi_gian_nau;
    private int so_luong_ban_ra;
    private String thong_tin;
    private int gia;
    private int gia_km;
    private List<DanhGiaSanPham> danhGiaSanPhams;

    public SanPham() {
    }

    public SanPham(SanPham sanPham) {
        this.maSanPham = sanPham.getMaSanPham();
        this.tenSanPham = sanPham.getTenSanPham();
        this.ngayTao = sanPham.getNgayTao();
        if (sanPham.getSizes() != null) {
            List<Size> size_new = new ArrayList<>();
            for (Size size : sanPham.getSizes()) {
                size_new.add(new Size(size));
            }
            this.sizes = size_new;
        }
        if(sanPham.getDanhMucSanPham() != null){
            this.danhMucSanPham = new DanhMucSanPham(sanPham.getDanhMucSanPham());
        }
        this.chonXoa = sanPham.isChonXoa();
        this.url = sanPham.getUrl();
        this.hinh_fb = sanPham.getHinh_fb();
        this.hinh = sanPham.getHinh();
        this.kcal = sanPham.getKcal();
        this.thoi_gian_nau = sanPham.getThoi_gian_nau();
        this.so_luong_ban_ra = sanPham.getSo_luong_ban_ra();
        this.thong_tin = sanPham.getThong_tin();
        this.gia = sanPham.getGia();
        this.gia_km = sanPham.getGia_km();
        if (sanPham.getDanhGiaSanPhams() != null) {
            List<DanhGiaSanPham> danhGiaSanPham_New = new ArrayList<>();
            for (DanhGiaSanPham danhGiaSanPham : sanPham.getDanhGiaSanPhams()) {
                danhGiaSanPham_New.add(new DanhGiaSanPham(danhGiaSanPham));
            }
            this.danhGiaSanPhams = danhGiaSanPham_New;
        }
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public DateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(DateTime ngayTao) {
        this.ngayTao = ngayTao;
    }

    public List<Size> getSizes() {
        return sizes;
    }

    public void setSizes(List<Size> sizes) {
        this.sizes = sizes;
    }

    public DanhMucSanPham getDanhMucSanPham() {
        return danhMucSanPham;
    }

    public void setDanhMucSanPham(DanhMucSanPham danhMucSanPham) {
        this.danhMucSanPham = danhMucSanPham;
    }

    public boolean isChonXoa() {
        return chonXoa;
    }

    public void setChonXoa(boolean chonXoa) {
        this.chonXoa = chonXoa;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHinh_fb() {
        return hinh_fb;
    }

    public void setHinh_fb(String hinh_fb) {
        this.hinh_fb = hinh_fb;
    }

    public Bitmap getHinh() {
        return hinh;
    }

    public void setHinh(Bitmap hinh) {
        this.hinh = hinh;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public int getThoi_gian_nau() {
        return thoi_gian_nau;
    }

    public void setThoi_gian_nau(int thoi_gian_nau) {
        this.thoi_gian_nau = thoi_gian_nau;
    }

    public int getSo_luong_ban_ra() {
        return so_luong_ban_ra;
    }

    public void setSo_luong_ban_ra(int so_luong_ban_ra) {
        this.so_luong_ban_ra = so_luong_ban_ra;
    }

    public String getThong_tin() {
        return thong_tin;
    }

    public void setThong_tin(String thong_tin) {
        this.thong_tin = thong_tin;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getGia_km() {
        return gia_km;
    }

    public void setGia_km(int gia_km) {
        this.gia_km = gia_km;
    }

    public List<DanhGiaSanPham> getDanhGiaSanPhams() {
        return danhGiaSanPhams;
    }

    public void setDanhGiaSanPhams(List<DanhGiaSanPham> danhGiaSanPhams) {
        this.danhGiaSanPhams = danhGiaSanPhams;
    }

    public String getSizeString() {
        String result = "";
        for (Size size : sizes) {
            result += size.getTen_size() + ", ";
        }
        result = result.substring(0, result.length() - 2);
        return result;
    }

    public int getSoLuongSanPham() {
        if (this.getSizes() == null) return -1;
        int result = 0;
        for (Size size : sizes) {
            result += size.getSo_luong_con_lai();
        }
        return result;
    }

    public String getSao() {
        double sum = 0;
        if (danhGiaSanPhams == null || danhGiaSanPhams.size() == 0) return "Chưa có đánh giá";
        for (DanhGiaSanPham danhGiaSanPham : danhGiaSanPhams) {
            sum += danhGiaSanPham.getSo_sao();
        }
        double sao = sum / danhGiaSanPhams.size();
        return String.format("%,.1f", sao);
    }

    public double getSaoInt() {
        double sum = 0;
        for (DanhGiaSanPham danhGiaSanPham : danhGiaSanPhams) {
            sum += danhGiaSanPham.getSo_sao();
        }
        return danhGiaSanPhams.size() == 0 ? 0 : sum / danhGiaSanPhams.size();
    }


}
