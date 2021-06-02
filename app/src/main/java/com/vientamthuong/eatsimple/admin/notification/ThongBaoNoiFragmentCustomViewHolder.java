package com.vientamthuong.eatsimple.admin.notification;

import android.graphics.Bitmap;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.date.DateTime;

public class ThongBaoNoiFragmentCustomViewHolder extends RecyclerView.ViewHolder {

    private CardView cardViewHinhDaiDien;
    private ImageView imageViewHinhDaiDien;
    private TextView textViewNoiDung;
    private TextView textViewNgayTao;
    private CardView cardViewHinhDaiDienLottie;
    private CardView cardViewNoiDungLottie;
    private CardView cardViewNgayTaoLottie;

    public ThongBaoNoiFragmentCustomViewHolder(@NonNull View itemView) {
        super(itemView);
        getView(itemView);
    }

    public void updateNgayTao(DateTime dateTime) {
        textViewNgayTao.setText(dateTime.toStrngRingNotification());
        textViewNgayTao.setVisibility(View.VISIBLE);
        cardViewNgayTaoLottie.setVisibility(View.GONE);
    }

    private void getView(View view) {
        cardViewHinhDaiDien = view.findViewById(R.id.hinh_dai_dien);
        imageViewHinhDaiDien = view.findViewById(R.id.hinh_dai_dien_image_view);
        textViewNoiDung = view.findViewById(R.id.noi_dung);
        textViewNgayTao = view.findViewById(R.id.ngay_tao);
        cardViewHinhDaiDienLottie = view.findViewById(R.id.hinh_dai_dien_lottie);
        cardViewNoiDungLottie = view.findViewById(R.id.noi_dung_lottie);
        cardViewNgayTaoLottie = view.findViewById(R.id.ngay_tao_lottie);
    }

    public CardView getCardViewHinhDaiDien() {
        return cardViewHinhDaiDien;
    }

    public void setCardViewHinhDaiDien(CardView cardViewHinhDaiDien) {
        this.cardViewHinhDaiDien = cardViewHinhDaiDien;
    }

    public ImageView getImageViewHinhDaiDien() {
        return imageViewHinhDaiDien;
    }

    public void setImageViewHinhDaiDien(ImageView imageViewHinhDaiDien) {
        this.imageViewHinhDaiDien = imageViewHinhDaiDien;
    }

    public TextView getTextViewNoiDung() {
        return textViewNoiDung;
    }

    public void setTextViewNoiDung(TextView textViewNoiDung) {
        this.textViewNoiDung = textViewNoiDung;
    }

    public TextView getTextViewNgayTao() {
        return textViewNgayTao;
    }

    public void setTextViewNgayTao(TextView textViewNgayTao) {
        this.textViewNgayTao = textViewNgayTao;
    }

    public CardView getCardViewHinhDaiDienLottie() {
        return cardViewHinhDaiDienLottie;
    }

    public void setCardViewHinhDaiDienLottie(CardView cardViewHinhDaiDienLottie) {
        this.cardViewHinhDaiDienLottie = cardViewHinhDaiDienLottie;
    }

    public CardView getCardViewNoiDungLottie() {
        return cardViewNoiDungLottie;
    }

    public void setCardViewNoiDungLottie(CardView cardViewNoiDungLottie) {
        this.cardViewNoiDungLottie = cardViewNoiDungLottie;
    }

    public CardView getCardViewNgayTaoLottie() {
        return cardViewNgayTaoLottie;
    }

    public void setCardViewNgayTaoLottie(CardView cardViewNgayTaoLottie) {
        this.cardViewNgayTaoLottie = cardViewNgayTaoLottie;
    }

    public void updateHinh(Bitmap hinh_nguoi_gui) {
        imageViewHinhDaiDien.setImageBitmap(hinh_nguoi_gui);
        cardViewHinhDaiDien.setVisibility(View.VISIBLE);
        cardViewHinhDaiDienLottie.setVisibility(View.GONE);
    }

    public void updateTen(String ten_nguoi_gui, String noi_dung) {
        String[] array= noi_dung.split("#");
        String source = "<b>"+ten_nguoi_gui+"</b> "+array[0]+" <b>#"+array[1]+"</b>";
        textViewNoiDung.setText(Html.fromHtml(source));
        textViewNoiDung.setVisibility(View.VISIBLE);
        cardViewNoiDungLottie.setVisibility(View.GONE);
    }
}
