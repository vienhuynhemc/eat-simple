package com.vientamthuong.eatsimple.thanh;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.R;

import java.util.List;

class LoadingViewHolder extends RecyclerView.ViewHolder{

    private ProgressBar progressBar;

    public LoadingViewHolder(@NonNull View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.proccess_bar);
    }
}
class ItemViewHolder extends RecyclerView.ViewHolder{

     TextView name;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name_item);
    }
}

public class DynamicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int VIEW_TYPE_ITEM = 0,VIEW_TYPE_LOADING = 1;
    private LoadMore loadMore;
    private  boolean isLoad;
    private Activity activity;
    private List<DynamicRVModel> items;
    private int visibleThrehold = 5;
    private int lasVisible, totalItemCount;

    public DynamicAdapter(RecyclerView recyclerView,Activity activity, List<DynamicRVModel> items) {
        this.activity = activity;
        this.items = items;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lasVisible = linearLayoutManager.findLastVisibleItemPosition();
                if (isLoad && totalItemCount <= (lasVisible+visibleThrehold)){
                    if(loadMore != null){
                        loadMore.onLoadMore();
                        isLoad = true;
                    }
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }

    public void setLoadMore(LoadMore loadMore){
        this.loadMore = loadMore;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(activity).inflate(R.layout.dynamic_rv_item,parent,false);
            return new LoadingViewHolder(view);
        }else if (viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(activity).inflate(R.layout.dynamic_rv_processbar,parent,false);
            return new LoadingViewHolder(view);
        }

          return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder){
            DynamicRVModel item = items.get(position);
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            viewHolder.name.setText(items.get(position).getName());
        }
        else if (holder instanceof LoadingViewHolder){
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;

        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void setLoaded(){
        isLoad = false;
    }
}
