package com.vientamthuong.eatsimple.admin.donHang;

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
import com.vientamthuong.eatsimple.admin.session.DataSession;

import org.jetbrains.annotations.NotNull;

public class DonHangViewHolder extends RecyclerView.ViewHolder {
    private ImageView hinhDaiDien;
    private LottieAnimationView hinhDaiDienLottie;
    private TextView tenKh;
    private CardView tenLottie;
    private TextView maDh;
    private CardView maLottie;
    private TextView ngayTao;
    private CardView ngayLottie;
    private CardView sua;
    private CardView suaLottie;
    private CardView xoa;
    private CardView xoaLottie;
    private CardView layout;
    private CardView sdt_lottie;
    private ConstraintLayout sdt_layout;
    private TextView sdt;
    private CardView nguoi_nhan_lottie;
    private ConstraintLayout nguoi_nhan_layout;
    private TextView nguoi_nhan;
    private CardView dia_chi_lottie;
    private ConstraintLayout dia_chi_layout;
    private TextView dia_chi;
    private CardView mgg_lottie;
    private ConstraintLayout mgg_layout;
    private TextView mgg;
    private CardView tien_lottie;
    private ConstraintLayout tien_layout;
    private TextView tien;
    private CardView trang_thai_lottie;
    private ConstraintLayout trang_thai_layout;
    private TextView trang_thai;
    private CardView so_luong_san_pham_lottie;
    private ConstraintLayout so_luong_san_pham_layout;
    private TextView so_luong_san_pham;

    public DonHangViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        getView(itemView);
    }

    public void updateHinhDaiDien() {
        hinhDaiDienLottie.setVisibility(View.GONE);
        hinhDaiDien.setVisibility(View.VISIBLE);
    }

    public void updateTenKh() {
        tenLottie.setVisibility(View.GONE);
        tenKh.setVisibility(View.VISIBLE);
    }

    public void updateSdt() {
        sdt_lottie.setVisibility(View.GONE);
        sdt_layout.setVisibility(View.VISIBLE);
    }

    public void updateNguoiNhan() {
        nguoi_nhan_lottie.setVisibility(View.GONE);
        nguoi_nhan_layout.setVisibility(View.VISIBLE);
    }

    public void updateDiaChi() {
        dia_chi_lottie.setVisibility(View.GONE);
        dia_chi_layout.setVisibility(View.VISIBLE);
    }

    public void updateMgg() {
        mgg_lottie.setVisibility(View.GONE);
        mgg_layout.setVisibility(View.VISIBLE);
    }

    public void updateTien() {
        tien_lottie.setVisibility(View.GONE);
        tien_layout.setVisibility(View.VISIBLE);
    }

    public void updateTrangThai(int trangThai) {
        trang_thai_lottie.setVisibility(View.GONE);
        trang_thai_layout.setVisibility(View.VISIBLE);
        int capDo = DataSession.getInstance().getCap_do();
        if ((capDo == 0 && trangThai == 1) || (capDo == 1 && trangThai == 2) || (capDo == 2 && trangThai == 3)) {
            xoaLottie.setVisibility(View.GONE);
            xoa.setVisibility(View.VISIBLE);
        } else {
            xoaLottie.setVisibility(View.GONE);
            xoa.setVisibility(View.GONE);
        }
    }


    public void updateSoLuongSanPham() {
        so_luong_san_pham_lottie.setVisibility(View.GONE);
        so_luong_san_pham_layout.setVisibility(View.VISIBLE);
    }

    public void updateMaDh() {
        maLottie.setVisibility(View.GONE);
        maDh.setVisibility(View.VISIBLE);
        suaLottie.setVisibility(View.GONE);
        sua.setVisibility(View.VISIBLE);
    }


    public void updateNgayTao() {
        ngayLottie.setVisibility(View.GONE);
        ngayTao.setVisibility(View.VISIBLE);
    }

    private void getView(View view) {
        hinhDaiDien = view.findViewById(R.id.hinhDaiDien);
        hinhDaiDienLottie = view.findViewById(R.id.hinhDaiDienLottie);
        tenKh = view.findViewById(R.id.tenDanhMuc);
        tenLottie = view.findViewById(R.id.tenLottie);
        maDh = view.findViewById(R.id.maDanhMuc);
        maLottie = view.findViewById(R.id.maLottie);
        ngayTao = view.findViewById(R.id.ngayTao);
        ngayLottie = view.findViewById(R.id.ngayLottie);
        sua = view.findViewById(R.id.buttonSua);
        suaLottie = view.findViewById(R.id.suaLottie);
        xoa = view.findViewById(R.id.buttonXoa);
        xoaLottie = view.findViewById(R.id.xoaLottie);
        layout = view.findViewById(R.id.layout);
        sdt_lottie = view.findViewById(R.id.ltt1);
        nguoi_nhan_lottie = view.findViewById(R.id.ltt2);
        dia_chi_lottie = view.findViewById(R.id.ltt3);
        mgg_lottie = view.findViewById(R.id.ltt4);
        tien_lottie = view.findViewById(R.id.ltt5);
        trang_thai_lottie = view.findViewById(R.id.ltt6);
        so_luong_san_pham_lottie = view.findViewById(R.id.ltt7);
        sdt = view.findViewById(R.id.thongTin1);
        nguoi_nhan = view.findViewById(R.id.thongTin2);
        dia_chi = view.findViewById(R.id.thongTin3);
        mgg = view.findViewById(R.id.thongTin4);
        tien = view.findViewById(R.id.thongTin5);
        trang_thai = view.findViewById(R.id.thongTin6);
        so_luong_san_pham = view.findViewById(R.id.thongTin7);
        sdt_layout = view.findViewById(R.id.noiDung1);
        nguoi_nhan_layout = view.findViewById(R.id.noiDung2);
        dia_chi_layout = view.findViewById(R.id.noiDung3);
        mgg_layout = view.findViewById(R.id.noiDung4);
        tien_layout = view.findViewById(R.id.noiDung5);
        trang_thai_layout = view.findViewById(R.id.noiDung6);
        so_luong_san_pham_layout = view.findViewById(R.id.noiDung7);
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

    public TextView getTenKh() {
        return tenKh;
    }

    public void setTenKh(TextView tenKh) {
        this.tenKh = tenKh;
    }

    public CardView getTenLottie() {
        return tenLottie;
    }

    public void setTenLottie(CardView tenLottie) {
        this.tenLottie = tenLottie;
    }

    public TextView getMaDh() {
        return maDh;
    }

    public void setMaDh(TextView maDh) {
        this.maDh = maDh;
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

    public CardView getLayout() {
        return layout;
    }

    public void setLayout(CardView layout) {
        this.layout = layout;
    }

    public CardView getSdt_lottie() {
        return sdt_lottie;
    }

    public void setSdt_lottie(CardView sdt_lottie) {
        this.sdt_lottie = sdt_lottie;
    }

    public ConstraintLayout getSdt_layout() {
        return sdt_layout;
    }

    public void setSdt_layout(ConstraintLayout sdt_layout) {
        this.sdt_layout = sdt_layout;
    }

    public TextView getSdt() {
        return sdt;
    }

    public void setSdt(TextView sdt) {
        this.sdt = sdt;
    }

    public CardView getNguoi_nhan_lottie() {
        return nguoi_nhan_lottie;
    }

    public void setNguoi_nhan_lottie(CardView nguoi_nhan_lottie) {
        this.nguoi_nhan_lottie = nguoi_nhan_lottie;
    }

    public ConstraintLayout getNguoi_nhan_layout() {
        return nguoi_nhan_layout;
    }

    public void setNguoi_nhan_layout(ConstraintLayout nguoi_nhan_layout) {
        this.nguoi_nhan_layout = nguoi_nhan_layout;
    }

    public TextView getNguoi_nhan() {
        return nguoi_nhan;
    }

    public void setNguoi_nhan(TextView nguoi_nhan) {
        this.nguoi_nhan = nguoi_nhan;
    }

    public CardView getDia_chi_lottie() {
        return dia_chi_lottie;
    }

    public void setDia_chi_lottie(CardView dia_chi_lottie) {
        this.dia_chi_lottie = dia_chi_lottie;
    }

    public ConstraintLayout getDia_chi_layout() {
        return dia_chi_layout;
    }

    public void setDia_chi_layout(ConstraintLayout dia_chi_layout) {
        this.dia_chi_layout = dia_chi_layout;
    }

    public TextView getDia_chi() {
        return dia_chi;
    }

    public void setDia_chi(TextView dia_chi) {
        this.dia_chi = dia_chi;
    }

    public CardView getMgg_lottie() {
        return mgg_lottie;
    }

    public void setMgg_lottie(CardView mgg_lottie) {
        this.mgg_lottie = mgg_lottie;
    }

    public ConstraintLayout getMgg_layout() {
        return mgg_layout;
    }

    public void setMgg_layout(ConstraintLayout mgg_layout) {
        this.mgg_layout = mgg_layout;
    }

    public TextView getMgg() {
        return mgg;
    }

    public void setMgg(TextView mgg) {
        this.mgg = mgg;
    }

    public CardView getTien_lottie() {
        return tien_lottie;
    }

    public void setTien_lottie(CardView tien_lottie) {
        this.tien_lottie = tien_lottie;
    }

    public ConstraintLayout getTien_layout() {
        return tien_layout;
    }

    public void setTien_layout(ConstraintLayout tien_layout) {
        this.tien_layout = tien_layout;
    }

    public TextView getTien() {
        return tien;
    }

    public void setTien(TextView tien) {
        this.tien = tien;
    }

    public CardView getTrang_thai_lottie() {
        return trang_thai_lottie;
    }

    public void setTrang_thai_lottie(CardView trang_thai_lottie) {
        this.trang_thai_lottie = trang_thai_lottie;
    }

    public ConstraintLayout getTrang_thai_layout() {
        return trang_thai_layout;
    }

    public void setTrang_thai_layout(ConstraintLayout trang_thai_layout) {
        this.trang_thai_layout = trang_thai_layout;
    }

    public TextView getTrang_thai() {
        return trang_thai;
    }

    public void setTrang_thai(TextView trang_thai) {
        this.trang_thai = trang_thai;
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
