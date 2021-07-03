package com.vientamthuong.eatsimple.menuNotify;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.SharedReferences.DataLocalManager;
import com.vientamthuong.eatsimple.beans.DetailOrder;
import com.vientamthuong.eatsimple.beans.ThongBao;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.detailPay.DetailPayActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NotifiAdapter extends RecyclerView.Adapter<NotifyHolder> {

    private List<ThongBao> list;

    public NotifiAdapter(List<ThongBao> list) {
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public NotifyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_notify_one_item,parent,false);

        NotifyHolder notifyHolder = new NotifyHolder(view);

        notifyHolder.getLayout().setOnClickListener(v -> {

            String ma_dh = list.get(notifyHolder.getAdapterPosition()).getNoi_dung_quan_trong();
            Intent intent = new Intent(v.getContext(), DetailPayActivity.class);
            intent.putExtra("ma_don_hang",ma_dh);

            v.getContext().startActivity(intent);

        });

        return notifyHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotifyHolder holder, int position) {

        ThongBao thongBao = list.get(position);

        holder.getTitle().setText("Chào " + DataLocalManager.getAccount().getName());
        holder.getContent().setText(thongBao.getNoi_dung() + " " + thongBao.getNoi_dung_quan_trong());
        holder.getTime().setText(thongBao.getNgay_tao().toStringDateTypeNumberStringNumber());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
