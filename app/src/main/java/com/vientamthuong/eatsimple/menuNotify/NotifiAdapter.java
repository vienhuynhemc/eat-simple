package com.vientamthuong.eatsimple.menuNotify;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.SharedReferences.DataLocalManager;
import com.vientamthuong.eatsimple.beans.DetailOrder;
import com.vientamthuong.eatsimple.beans.ThongBao;
import com.vientamthuong.eatsimple.checkout.CheckoutConfiguration;
import com.vientamthuong.eatsimple.checkout.PayActivity;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.detailPay.DetailPayActivity;
import com.vientamthuong.eatsimple.loadData.VolleyPool;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotifiAdapter extends RecyclerView.Adapter<NotifyHolder> {

    private List<ThongBao> list;
    private Context context;

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
        notifyHolder.getLayout().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Dialog dialog = openDialogDatabase(R.layout.activity_notify_dialog_delete);

                AppCompatButton xoa = dialog.findViewById(R.id.dialog_lost_connection_try);
                AppCompatButton kxoa = dialog.findViewById(R.id.dialog_lost_connection_ignore);

                kxoa.setOnClickListener(v1 -> {
                    dialog.dismiss();
                });

                xoa.setOnClickListener(v1 -> {

                    String urlLoad = "https://eat-simple-app.000webhostapp.com/deleteNotify.php";

                    StringRequest request = new StringRequest(Request.Method.POST, urlLoad,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if (response.equals("SUCCESS")) {
                                        Toast.makeText(context, "Đã xóa thành công", Toast.LENGTH_SHORT).show();

                                        list.remove(notifyHolder.getAdapterPosition());
                                        notifyDataSetChanged();
                                        dialog.dismiss();

                                    } else {
                                        Toast.makeText(context, "Xóa thất bại vui lòng thử lại", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }) {
                        @Nullable
                        @org.jetbrains.annotations.Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> params = new HashMap<>();
                            params.put("ma_kh", DataLocalManager.getAccount().getId());
                            params.put("ma_tt",list.get(notifyHolder.getAdapterPosition()).getMa_thong_bao());
                            return params;
                        }
                    };
                    VolleyPool.getInstance(context).addRequest(request);

                });


                return false;
            }
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

    Dialog openDialogDatabase(int layout) {
        final Dialog dialog = new Dialog(context);
        // ẩn title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // set background diaog
        dialog.setContentView(layout);

        Window window = dialog.getWindow();

        if (window == null) {
            return null;
        } else {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.CENTER;
            window.setAttributes(layoutParams);
            dialog.setCancelable(true);
            dialog.show();
            return dialog;
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
