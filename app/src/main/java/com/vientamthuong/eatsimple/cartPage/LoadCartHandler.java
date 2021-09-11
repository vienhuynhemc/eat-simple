package com.vientamthuong.eatsimple.cartPage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.vientamthuong.eatsimple.beans.Cart;
import com.vientamthuong.eatsimple.beans.Comment;
import com.vientamthuong.eatsimple.beans.Product;
import com.vientamthuong.eatsimple.detail.CommentAdapter;
import com.vientamthuong.eatsimple.detailOrder.DetailOrderAdapter;
import com.vientamthuong.eatsimple.loadProductByID.LoadProductHandler;
import com.vientamthuong.eatsimple.loadProductByID.LoadProductHelp;
import com.vientamthuong.eatsimple.loadProductByID.LoadProductViewAdapter;

import java.util.List;

public class LoadCartHandler {

    private Handler handler;
    private List<Cart> carts;
    private List<Cart> order;
    private CartAdapter cartAdapter;
    private static LoadCartHandler loadCartHandler;
    private DetailOrderAdapter orderAdapter;
    private List<Comment> comment;
    private CommentAdapter commentAdapter;

    private LoadCartHandler(){
        // getHandler();
    }
    public static LoadCartHandler getInstance(){
        if (loadCartHandler == null){
            loadCartHandler = new LoadCartHandler();
        }
        return loadCartHandler;
    }

    public Handler getHandler() {

        if (handler == null){
            handler = new Handler(){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == 100){
                        Bundle bundle = msg.getData();
                        List<Cart> cart = (List<Cart>) bundle.getSerializable("carts");
                        carts.clear();
                        carts.addAll(cart);
                        cartAdapter.notifyDataSetChanged();
                    }
                    if (msg.what == 149){
                        Bundle bundle = msg.getData();
                        List<Cart> cart = (List<Cart>) bundle.getSerializable("carts");
                        order.addAll(cart);
                        orderAdapter.notifyDataSetChanged();
                    }

                    if (msg.what == 190){
                        Bundle bundle = msg.getData();
                        List<Comment> comments = (List<Comment>) bundle.getSerializable("comments");
                        comment.clear();
                        comment.addAll(comments);
                        commentAdapter.notifyDataSetChanged();
                    }
                }
            };

        }

        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public CartAdapter getCartAdapter() {
        return cartAdapter;
    }

    public void setCartAdapter(CartAdapter cartAdapter) {
        this.cartAdapter = cartAdapter;
    }

    public DetailOrderAdapter getOrderAdapter() {
        return orderAdapter;
    }

    public List<Cart> getOrder() {
        return order;
    }

    public void setOrder(List<Cart> order) {
        this.order = order;
    }

    public void setOrderAdapter(DetailOrderAdapter orderAdapter) {
        this.orderAdapter = orderAdapter;
    }

    public static LoadCartHandler getLoadCartHandler() {
        return loadCartHandler;
    }

    public static void setLoadCartHandler(LoadCartHandler loadCartHandler) {
        LoadCartHandler.loadCartHandler = loadCartHandler;
    }

    public List<Comment> getComments() {
        return comment;
    }

    public void setComments(List<Comment> comments) {
        this.comment = comments;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public CommentAdapter getCommentAdapter() {
        return commentAdapter;
    }

    public void setCommentAdapter(CommentAdapter commentAdapter) {
        this.commentAdapter = commentAdapter;
    }
}
