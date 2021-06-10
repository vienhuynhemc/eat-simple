package com.vientamthuong.eatsimple.admin.homePage;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.admin.model.ThongBaoCaNhanTrangChu;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomePageAdapter extends RecyclerView.Adapter<HomePageViewHolder> {

    private int resource;
    private Context context;
    private List<ThongBaoCaNhanTrangChu> thongBaoCaNhanTrangChus;

    public HomePageAdapter(int resource, Context context, List<ThongBaoCaNhanTrangChu> thongBaoCaNhanTrangChus) {
        this.resource = resource;
        this.context = context;
        this.thongBaoCaNhanTrangChus = thongBaoCaNhanTrangChus;
    }

    @NonNull
    @NotNull
    @Override
    public HomePageViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new HomePageViewHolder(LayoutInflater.from(context).inflate(resource, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HomePageViewHolder holder, int position) {
        updateColor(position, holder);
        ThongBaoCaNhanTrangChu t = thongBaoCaNhanTrangChus.get(position);
        if (t.getNgay_tao() != null) {
            holder.getNgay_tao().setText(t.getNgay_tao().getOverTime());
            holder.updateNgayTao();
        }
        if (t.getCap_do_nv_thuc_hien()!= null ) {
            holder.getNoi_dung().setText(Html.fromHtml(t.toResult()));
            holder.updateNoiDung();
        }
    }

    private void updateColor(int position, HomePageViewHolder holder) {
        int type = position % 4;
        switch (type) {
            case 0:
                holder.getC1().setCardBackgroundColor(context.getColor(R.color.admin_home_page_1));
                holder.getC2().setBackgroundColor(context.getColor(R.color.admin_home_page_1));
                break;
            case 1:
                holder.getC1().setCardBackgroundColor(context.getColor(R.color.admin_home_page_2));
                holder.getC2().setBackgroundColor(context.getColor(R.color.admin_home_page_2));
                break;
            case 2:
                holder.getC1().setCardBackgroundColor(context.getColor(R.color.admin_home_page_3));
                holder.getC2().setBackgroundColor(context.getColor(R.color.admin_home_page_3));
                break;
            case 3:
                holder.getC1().setCardBackgroundColor(context.getColor(R.color.admin_home_page_4));
                holder.getC2().setBackgroundColor(context.getColor(R.color.admin_home_page_4));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return thongBaoCaNhanTrangChus.size();
    }
}
