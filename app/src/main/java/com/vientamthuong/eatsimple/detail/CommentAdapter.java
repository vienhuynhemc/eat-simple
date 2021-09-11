package com.vientamthuong.eatsimple.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.beans.Comment;
import com.vientamthuong.eatsimple.detailOrder.GetDetailOrder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentHolder> {

    private List<Comment> comments;

    public CommentAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @NotNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_detail_comment_one_item,parent,false);
        CommentHolder commentHolder = new CommentHolder(view);

        return commentHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CommentHolder holder, int position) {

        Comment comment = comments.get(position);
        holder.getTen().setText(comment.getTen());
        holder.getTen_sp().setText("Sản phẩm: " + comment.getTen_sp() + " Size: " + comment.getSize());
        holder.getNoi_dung().setText(comment.getNoidung());
        holder.getSo_sao().setText(String.valueOf(comment.getSosao()));
        holder.getNgay_thang().setText(comment.getTime().toStrngRingNotification());

        if (comment.getBitmap() == null && !comment.isIsload()){
            GetComment.getBitmapImage(comment,holder.getImg().getContext(),this);
        }else  if (comment.getBitmap() != null && comment.isIsload()){
            holder.getImg().setImageBitmap(comment.getBitmap());
        }


    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
