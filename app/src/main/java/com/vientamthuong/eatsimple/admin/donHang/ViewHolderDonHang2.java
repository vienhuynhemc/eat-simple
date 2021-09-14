package com.vientamthuong.eatsimple.admin.donHang;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.R;

import org.w3c.dom.Text;

public class ViewHolderDonHang2 extends RecyclerView.ViewHolder {

    private ImageView hinh_sp;
    private TextView ten_sp;
    private TextView ma_sp;
    private TextView tensize;
    private TextView noi_dung_1;
    private TextView noi_dung_2;


    public ViewHolderDonHang2(@NonNull View itemView) {
        super(itemView);
        hinh_sp = itemView.findViewById(R.id.hinhDaiDien);
        ten_sp = itemView.findViewById(R.id.tenDanhMuc);
        ma_sp = itemView.findViewById(R.id.maDanhMuc);
        noi_dung_1 = itemView.findViewById(R.id.thongTin);
        noi_dung_2 = itemView.findViewById(R.id.thongTin2);
        tensize = itemView.findViewById(R.id.ngayTao);
    }

    public ImageView getHinh_sp() {
        return hinh_sp;
    }

    public TextView getTen_sp() {
        return ten_sp;
    }

    public TextView getMa_sp() {
        return ma_sp;
    }

    public TextView getNoi_dung_1() {
        return noi_dung_1;
    }

    public TextView getNoi_dung_2() {
        return noi_dung_2;
    }

    public void setHinh_sp(ImageView hinh_sp) {
        this.hinh_sp = hinh_sp;
    }

    public void setTen_sp(TextView ten_sp) {
        this.ten_sp = ten_sp;
    }

    public void setMa_sp(TextView ma_sp) {
        this.ma_sp = ma_sp;
    }

    public TextView getTensize() {
        return tensize;
    }


    public void setNoi_dung_1(TextView noi_dung_1) {
        this.noi_dung_1 = noi_dung_1;
    }

    public void setNoi_dung_2(TextView noi_dung_2) {
        this.noi_dung_2 = noi_dung_2;
    }
}
