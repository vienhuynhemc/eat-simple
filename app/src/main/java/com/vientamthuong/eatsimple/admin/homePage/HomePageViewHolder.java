package com.vientamthuong.eatsimple.admin.homePage;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.R;

import org.jetbrains.annotations.NotNull;

public class HomePageViewHolder extends RecyclerView.ViewHolder {

    private CardView c1;
    private TextView c2;
    private TextView ngay_tao;
    private TextView noi_dung;
    // lottie
    private CardView l1;
    private CardView l2;
    private CardView l3;
    private CardView l4;
    private CardView l5;
    private CardView l6;


    public HomePageViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        getView(itemView);
    }

    private void getView(View itemView) {
        c1 = itemView.findViewById(R.id.c1);
        c2 = itemView.findViewById(R.id.c2);
        ngay_tao = itemView.findViewById(R.id.ngay_tao);
        noi_dung = itemView.findViewById(R.id.noi_dung);
        l1 = itemView.findViewById(R.id.l1);
        l2 = itemView.findViewById(R.id.l2);
        l3 = itemView.findViewById(R.id.l3);
        l4 = itemView.findViewById(R.id.l4);
        l5 = itemView.findViewById(R.id.l5);
        l6 = itemView.findViewById(R.id.l6);
    }

    public CardView getC1() {
        return c1;
    }

    public void setC1(CardView c1) {
        this.c1 = c1;
    }

    public TextView getC2() {
        return c2;
    }

    public void setC2(TextView c2) {
        this.c2 = c2;
    }

    public TextView getNgay_tao() {
        return ngay_tao;
    }

    public void setNgay_tao(TextView ngay_tao) {
        this.ngay_tao = ngay_tao;
    }

    public TextView getNoi_dung() {
        return noi_dung;
    }

    public void setNoi_dung(TextView noi_dung) {
        this.noi_dung = noi_dung;
    }

    public void updateNgayTao() {
        l1.setVisibility(View.INVISIBLE);
        c1.setVisibility(View.VISIBLE);
        l2.setVisibility(View.INVISIBLE);
        c2.setVisibility(View.VISIBLE);
        l3.setVisibility(View.INVISIBLE);
        ngay_tao.setVisibility(View.VISIBLE);
    }

    public void updateNoiDung() {
        l4.setVisibility(View.INVISIBLE);
        l5.setVisibility(View.INVISIBLE);
        l6.setVisibility(View.INVISIBLE);
        noi_dung.setVisibility(View.VISIBLE);
    }
}
