package com.vientamthuong.eatsimple.mennuSearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.beans.Product;
import com.vientamthuong.eatsimple.beans.Tag;
import com.vientamthuong.eatsimple.loadProductByID.LoadProductViewAdapter;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HashTagAdapter extends RecyclerView.Adapter<HashtagHolder> {

    private List<Tag> tagList;
    private List<Product> productList;
    private LoadProductViewAdapter adapter;
    private int index;
    private SearchFragment searchFragment;

    public HashTagAdapter(List<Tag> tagList) {
        this.tagList = tagList;
    }

    @NonNull
    @NotNull
    @Override
    public HashtagHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_search_tag,parent,false);

        HashtagHolder hashtagHolder = new HashtagHolder(view);

        hashtagHolder.getCardView().setOnClickListener(v -> {
            searchFragment.changeColor(hashtagHolder.getAdapterPosition());
           // hashtagHolder.getName().setBackgroundResource(R.color.color_main);

            switch (tagList.get(hashtagHolder.getAdapterPosition()).getId()){

                case "tag_1":
                    Collections.sort(productList, new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return o1.getThoi_gian_nau() - o2.getThoi_gian_nau();
                        }
                    });
                    adapter.notifyDataSetChanged();
                    break;
                case "tag_2":
                    DateFormat f = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
                    Collections.sort(productList, new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            try {
                                 return f.parse(o1.getNgay_tao().toString()).compareTo(f.parse(o2.getNgay_tao().toString()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return 0;
                            }
                        }
                    });
                    adapter.notifyDataSetChanged();
                    break;
                case "tag_3":
                    Collections.sort(productList, new Comparator<Product>() {

                        @Override
                        public int compare(Product o1, Product o2) {
                            return o1.getGia_km() - o2.getGia_km();
                        }
                    });
                    adapter.notifyDataSetChanged();
                    break;
                case "tag_4":
                    Collections.sort(productList, new Comparator<Product>() {

                        @Override
                        public int compare(Product o1, Product o2) {
                            return o2.getSo_luong_con_lai() - o1.getSo_luong_con_lai();
                        }
                    });
                    adapter.notifyDataSetChanged();
                    break;

                case "tag_5":
                    Collections.sort(productList, new Comparator<Product>() {

                        @Override
                        public int compare(Product o1, Product o2) {
                            System.out.println("ZO TAG_5");
                            return o2.getSo_luong_ban_ra() - o1.getSo_luong_ban_ra();
                        }
                    });
                    adapter.notifyDataSetChanged();
                    break;
            }

        });

        return hashtagHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HashtagHolder holder, int position) {

        Tag tag = tagList.get(position);

        holder.getName().setText(tag.getName());


    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public LoadProductViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(LoadProductViewAdapter adapter) {
        this.adapter = adapter;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public SearchFragment getSearchFragment() {
        return searchFragment;
    }

    public void setSearchFragment(SearchFragment searchFragment) {
        this.searchFragment = searchFragment;
    }
}
