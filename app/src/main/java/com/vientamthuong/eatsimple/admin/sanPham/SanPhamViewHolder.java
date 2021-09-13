package com.vientamthuong.eatsimple.admin.sanPham;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.vientamthuong.eatsimple.R;

import org.jetbrains.annotations.NotNull;

public class SanPhamViewHolder extends RecyclerView.ViewHolder {
    private ImageView hinhDaiDien;
    private LottieAnimationView hinhDaiDienLottie;
    private TextView tenSp;
    private CardView tenLottie;
    private TextView maSp;
    private CardView maLottie;
    private TextView ngayTao;
    private CardView ngayLottie;
    private CardView sua;
    private CardView suaLottie;
    private CardView xoa;
    private CardView xoaLottie;
    private CheckBox chonXoa;
    private CardView chonXoaLottie;
    private CardView layout;
    private CardView dm_lottie;
    private ConstraintLayout dm_layout;
    private TextView dm;
    private CardView gia_lottie;
    private ConstraintLayout gia_layout;
    private TextView gia;
    private CardView size_lottie;
    private ConstraintLayout size_layout;
    private TextView size;
    private CardView kcal_lottie;
    private ConstraintLayout kcal_layout;
    private TextView kcal;
    private CardView thoi_gain_nau_lottie;
    private ConstraintLayout thoi_gian_nau_layout;
    private TextView thoi_gian_nau;
    private CardView so_danh_gia_lottie;
    private ConstraintLayout so_danh_gia_layout;
    private TextView so_danh_gia;
    private CardView sao_lottie;
    private ConstraintLayout sao_layout;
    private TextView sao;
    private CardView so_luong_ban_ra_lottie;
    private ConstraintLayout so_luong_ban_ra_layout;
    private TextView so_luong_ban_ra;
    private CardView so_luong_san_pham_lottie;
    private ConstraintLayout so_luong_san_pham_layout;
    private TextView so_luong_san_pham;

    public SanPhamViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        getView(itemView);
    }

    public void updateHinhDaiDien() {
        hinhDaiDienLottie.setVisibility(View.GONE);
        hinhDaiDien.setVisibility(View.VISIBLE);
    }

    public void updateTenSp(){
        tenLottie.setVisibility(View.GONE);
        tenSp.setVisibility(View.VISIBLE);
    }

    public void updateDm(){
        dm_lottie.setVisibility(View.GONE);
        dm_layout.setVisibility(View.VISIBLE);
    }

    public void updateSize(){
        size_lottie.setVisibility(View.GONE);
        size_layout.setVisibility(View.VISIBLE);
    }

    public void updateKcal(){
        kcal_lottie.setVisibility(View.GONE);
        kcal_layout.setVisibility(View.VISIBLE);
    }

    public void updateThoiGianNau(){
        thoi_gain_nau_lottie.setVisibility(View.GONE);
        thoi_gian_nau_layout.setVisibility(View.VISIBLE);
    }

    public void updateSoDanhGia(){
        so_danh_gia_lottie.setVisibility(View.GONE);
        so_danh_gia_layout.setVisibility(View.VISIBLE);
    }

    public void updateSoSao(){
        sao_lottie.setVisibility(View.GONE);
        sao_layout.setVisibility(View.VISIBLE);
    }

    public void updateGia(){
        gia_lottie.setVisibility(View.GONE);
        gia_layout.setVisibility(View.VISIBLE);
    }

    public void updateSoLuongSanPham(){
        so_luong_san_pham_lottie.setVisibility(View.GONE);
        so_luong_san_pham_layout.setVisibility(View.VISIBLE);
    }

    public void updateSoLuongBanRa(){
        so_luong_ban_ra_lottie.setVisibility(View.GONE);
        so_luong_ban_ra_layout.setVisibility(View.VISIBLE);
    }

    public void updateSanPham(){
        maLottie.setVisibility(View.GONE);
        maSp.setVisibility(View.VISIBLE);
        chonXoaLottie.setVisibility(View.GONE);
        chonXoa.setVisibility(View.VISIBLE);
        suaLottie.setVisibility(View.GONE);
        sua.setVisibility(View.VISIBLE);
        xoaLottie.setVisibility(View.GONE);
        xoa.setVisibility(View.VISIBLE);
    }

    public void updateNgayTao(){
        ngayLottie.setVisibility(View.GONE);
        ngayTao.setVisibility(View.VISIBLE);
    }

    private void getView(View view) {
        hinhDaiDien = view.findViewById(R.id.hinhDaiDien);
        hinhDaiDienLottie = view.findViewById(R.id.hinhDaiDienLottie);
        tenSp = view.findViewById(R.id.tenDanhMuc);
        tenLottie = view.findViewById(R.id.tenLottie);
        maSp = view.findViewById(R.id.maDanhMuc);
        maLottie = view.findViewById(R.id.maLottie);
        ngayTao = view.findViewById(R.id.ngayTao);
        ngayLottie = view.findViewById(R.id.ngayLottie);
        sua = view.findViewById(R.id.buttonSua);
        suaLottie = view.findViewById(R.id.suaLottie);
        xoa = view.findViewById(R.id.buttonXoa);
        xoaLottie = view.findViewById(R.id.xoaLottie);
        chonXoa = view.findViewById(R.id.chonXoa);
        chonXoaLottie = view.findViewById(R.id.chonXoaLottie);
        layout = view.findViewById(R.id.layout);
        dm_lottie = view.findViewById(R.id.ltt1);
        gia_lottie = view.findViewById(R.id.ltt2);
        size_lottie = view.findViewById(R.id.ltt3);
        kcal_lottie = view.findViewById(R.id.ltt4);
        thoi_gain_nau_lottie = view.findViewById(R.id.ltt5);
        so_danh_gia_lottie = view.findViewById(R.id.ltt6);
        sao_lottie = view.findViewById(R.id.ltt7);
        so_luong_ban_ra_lottie = view.findViewById(R.id.ltt8);
        so_luong_san_pham_lottie= view.findViewById(R.id.ltt9);
        dm = view.findViewById(R.id.thongTin1);
        gia = view.findViewById(R.id.thongTin2);
        size = view.findViewById(R.id.thongTin3);
        kcal = view.findViewById(R.id.thongTin4);
        thoi_gian_nau = view.findViewById(R.id.thongTin5);
        so_danh_gia = view.findViewById(R.id.thongTin6);
        sao = view.findViewById(R.id.thongTin7);
        so_luong_ban_ra = view.findViewById(R.id.thongTin8);
        so_luong_san_pham = view.findViewById(R.id.thongTin9);
        dm_layout = view.findViewById(R.id.noiDung1);
        gia_layout = view.findViewById(R.id.noiDung2);
        size_layout = view.findViewById(R.id.noiDung3);
        kcal_layout = view.findViewById(R.id.noiDung4);
        thoi_gian_nau_layout = view.findViewById(R.id.noiDung5);
        so_danh_gia_layout = view.findViewById(R.id.noiDung6);
        sao_layout = view.findViewById(R.id.noiDung7);
        so_luong_ban_ra_layout = view.findViewById(R.id.noiDung8);
        so_luong_san_pham_layout = view.findViewById(R.id.noiDung9);
    }

    public ImageView getHinhDaiDien() {
        return hinhDaiDien;
    }

    public void setHinhDaiDien(ImageView hinhDaiDien) {
        this.hinhDaiDien = hinhDaiDien;
    }

    public LottieAnimationView getHinhDaiDienLottie() {
        return hinhDaiDienLottie;
    }

    public void setHinhDaiDienLottie(LottieAnimationView hinhDaiDienLottie) {
        this.hinhDaiDienLottie = hinhDaiDienLottie;
    }

    public TextView getTenSp() {
        return tenSp;
    }

    public void setTenSp(TextView tenSp) {
        this.tenSp = tenSp;
    }

    public CardView getTenLottie() {
        return tenLottie;
    }

    public void setTenLottie(CardView tenLottie) {
        this.tenLottie = tenLottie;
    }

    public TextView getMaSp() {
        return maSp;
    }

    public void setMaSp(TextView maSp) {
        this.maSp = maSp;
    }

    public CardView getMaLottie() {
        return maLottie;
    }

    public void setMaLottie(CardView maLottie) {
        this.maLottie = maLottie;
    }

    public TextView getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(TextView ngayTao) {
        this.ngayTao = ngayTao;
    }

    public CardView getNgayLottie() {
        return ngayLottie;
    }

    public void setNgayLottie(CardView ngayLottie) {
        this.ngayLottie = ngayLottie;
    }

    public CardView getSua() {
        return sua;
    }

    public void setSua(CardView sua) {
        this.sua = sua;
    }

    public CardView getSuaLottie() {
        return suaLottie;
    }

    public void setSuaLottie(CardView suaLottie) {
        this.suaLottie = suaLottie;
    }

    public CardView getXoa() {
        return xoa;
    }

    public void setXoa(CardView xoa) {
        this.xoa = xoa;
    }

    public CardView getXoaLottie() {
        return xoaLottie;
    }

    public void setXoaLottie(CardView xoaLottie) {
        this.xoaLottie = xoaLottie;
    }

    public CheckBox getChonXoa() {
        return chonXoa;
    }

    public void setChonXoa(CheckBox chonXoa) {
        this.chonXoa = chonXoa;
    }

    public CardView getChonXoaLottie() {
        return chonXoaLottie;
    }

    public void setChonXoaLottie(CardView chonXoaLottie) {
        this.chonXoaLottie = chonXoaLottie;
    }

    public CardView getLayout() {
        return layout;
    }

    public void setLayout(CardView layout) {
        this.layout = layout;
    }

    public CardView getDm_lottie() {
        return dm_lottie;
    }

    public void setDm_lottie(CardView dm_lottie) {
        this.dm_lottie = dm_lottie;
    }

    public ConstraintLayout getDm_layout() {
        return dm_layout;
    }

    public void setDm_layout(ConstraintLayout dm_layout) {
        this.dm_layout = dm_layout;
    }

    public TextView getDm() {
        return dm;
    }

    public void setDm(TextView dm) {
        this.dm = dm;
    }

    public CardView getGia_lottie() {
        return gia_lottie;
    }

    public void setGia_lottie(CardView gia_lottie) {
        this.gia_lottie = gia_lottie;
    }

    public ConstraintLayout getGia_layout() {
        return gia_layout;
    }

    public void setGia_layout(ConstraintLayout gia_layout) {
        this.gia_layout = gia_layout;
    }

    public TextView getGia() {
        return gia;
    }

    public void setGia(TextView gia) {
        this.gia = gia;
    }

    public CardView getSize_lottie() {
        return size_lottie;
    }

    public void setSize_lottie(CardView size_lottie) {
        this.size_lottie = size_lottie;
    }

    public ConstraintLayout getSize_layout() {
        return size_layout;
    }

    public void setSize_layout(ConstraintLayout size_layout) {
        this.size_layout = size_layout;
    }

    public TextView getSize() {
        return size;
    }

    public void setSize(TextView size) {
        this.size = size;
    }

    public CardView getKcal_lottie() {
        return kcal_lottie;
    }

    public void setKcal_lottie(CardView kcal_lottie) {
        this.kcal_lottie = kcal_lottie;
    }

    public ConstraintLayout getKcal_layout() {
        return kcal_layout;
    }

    public void setKcal_layout(ConstraintLayout kcal_layout) {
        this.kcal_layout = kcal_layout;
    }

    public TextView getKcal() {
        return kcal;
    }

    public void setKcal(TextView kcal) {
        this.kcal = kcal;
    }

    public CardView getThoi_gain_nau_lottie() {
        return thoi_gain_nau_lottie;
    }

    public void setThoi_gain_nau_lottie(CardView thoi_gain_nau_lottie) {
        this.thoi_gain_nau_lottie = thoi_gain_nau_lottie;
    }

    public ConstraintLayout getThoi_gian_nau_layout() {
        return thoi_gian_nau_layout;
    }

    public void setThoi_gian_nau_layout(ConstraintLayout thoi_gian_nau_layout) {
        this.thoi_gian_nau_layout = thoi_gian_nau_layout;
    }

    public TextView getThoi_gian_nau() {
        return thoi_gian_nau;
    }

    public void setThoi_gian_nau(TextView thoi_gian_nau) {
        this.thoi_gian_nau = thoi_gian_nau;
    }

    public CardView getSo_danh_gia_lottie() {
        return so_danh_gia_lottie;
    }

    public void setSo_danh_gia_lottie(CardView so_danh_gia_lottie) {
        this.so_danh_gia_lottie = so_danh_gia_lottie;
    }

    public ConstraintLayout getSo_danh_gia_layout() {
        return so_danh_gia_layout;
    }

    public void setSo_danh_gia_layout(ConstraintLayout so_danh_gia_layout) {
        this.so_danh_gia_layout = so_danh_gia_layout;
    }

    public TextView getSo_danh_gia() {
        return so_danh_gia;
    }

    public void setSo_danh_gia(TextView so_danh_gia) {
        this.so_danh_gia = so_danh_gia;
    }

    public CardView getSao_lottie() {
        return sao_lottie;
    }

    public void setSao_lottie(CardView sao_lottie) {
        this.sao_lottie = sao_lottie;
    }

    public ConstraintLayout getSao_layout() {
        return sao_layout;
    }

    public void setSao_layout(ConstraintLayout sao_layout) {
        this.sao_layout = sao_layout;
    }

    public TextView getSao() {
        return sao;
    }

    public void setSao(TextView sao) {
        this.sao = sao;
    }

    public CardView getSo_luong_ban_ra_lottie() {
        return so_luong_ban_ra_lottie;
    }

    public void setSo_luong_ban_ra_lottie(CardView so_luong_ban_ra_lottie) {
        this.so_luong_ban_ra_lottie = so_luong_ban_ra_lottie;
    }

    public ConstraintLayout getSo_luong_ban_ra_layout() {
        return so_luong_ban_ra_layout;
    }

    public void setSo_luong_ban_ra_layout(ConstraintLayout so_luong_ban_ra_layout) {
        this.so_luong_ban_ra_layout = so_luong_ban_ra_layout;
    }

    public TextView getSo_luong_ban_ra() {
        return so_luong_ban_ra;
    }

    public void setSo_luong_ban_ra(TextView so_luong_ban_ra) {
        this.so_luong_ban_ra = so_luong_ban_ra;
    }

    public CardView getSo_luong_san_pham_lottie() {
        return so_luong_san_pham_lottie;
    }

    public void setSo_luong_san_pham_lottie(CardView so_luong_san_pham_lottie) {
        this.so_luong_san_pham_lottie = so_luong_san_pham_lottie;
    }

    public ConstraintLayout getSo_luong_san_pham_layout() {
        return so_luong_san_pham_layout;
    }

    public void setSo_luong_san_pham_layout(ConstraintLayout so_luong_san_pham_layout) {
        this.so_luong_san_pham_layout = so_luong_san_pham_layout;
    }

    public TextView getSo_luong_san_pham() {
        return so_luong_san_pham;
    }

    public void setSo_luong_san_pham(TextView so_luong_san_pham) {
        this.so_luong_san_pham = so_luong_san_pham;
    }
}
