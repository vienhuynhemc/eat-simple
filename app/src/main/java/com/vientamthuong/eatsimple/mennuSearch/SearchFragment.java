package com.vientamthuong.eatsimple.mennuSearch;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.beans.Product;
import com.vientamthuong.eatsimple.beans.Tag;
import com.vientamthuong.eatsimple.loadProductByID.GetListProduct;
import com.vientamthuong.eatsimple.loadProductByID.LoadProductHandler;
import com.vientamthuong.eatsimple.loadProductByID.LoadProductHelp;
import com.vientamthuong.eatsimple.loadProductByID.LoadProductViewAdapter;
import com.vientamthuong.eatsimple.menuNotify.EventRing;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private RecyclerView reTag,recyclerView;
    private List<Tag> tagList;
    private HashTagAdapter adapter;
    private CardView cardView;
    private EditText editText;
    private LoadProductViewAdapter loadProductViewAdapter;
    private ArrayList<Product> productList;
    private ImageView ring;
    private ScrollView scrollView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        getView(view);
        init();
        event();

        return view;
    }
    public void event(){

        EventRing.getInstance().setView(ring);
        EventRing.getInstance().startAnim();

        cardView.setOnClickListener(v -> {

            String edit = editText.getText().toString().trim();
            if (!edit.isEmpty()){
                SearchHelp.getLoadProductHelp().setState(0);
                SearchHelp.getLoadProductHelp().setNum(0);
                SearchHelp.getLoadProductHelp().setEdit(edit);
                GetProduct.getData(getContext());
                Log.e("Click seatch ",edit);
                System.out.println("Click seatch " + edit);

            }else {
                Toast.makeText(getContext(), "Vùi lòng nhập từ khóa", Toast.LENGTH_SHORT).show();
            }
           

        });


            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (productList.size()!=0){
                        String edit = editText.getText().toString().trim();
                        System.out.println("on seatch " + edit);
                        loadProductViewAdapter.getFilter().filter(edit);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {

            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                System.out.println("OK ff" + scrollView.getBottom());
                System.out.println("Cuoii " + scrollY);
                System.out.println("Đầu " + oldScrollY);
                System.out.println("Zô chỗ này" + SearchHelp.getLoadProductHelp().getYMIN());
                System.out.println("TRANG: " + SearchHelp.getLoadProductHelp().getNum());
                if ((scrollY >= SearchHelp.getLoadProductHelp().getYMIN())){
                    SearchHelp.getLoadProductHelp().setState(1);
                    SearchHelp.getLoadProductHelp().setNum(LoadProductHelp.getLoadProductHelp().getNum()+1);
                    GetProduct.getData(getContext());
                    SearchHelp.getLoadProductHelp().setYMIN(LoadProductHelp.getLoadProductHelp().getYMIN() + 262);
                }
            }
        });




    }


    public void init(){
        getData();
        initProducts();
        adapter = new HashTagAdapter(tagList);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
        reTag.setLayoutManager(staggeredGridLayoutManager);
        reTag.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setAdapter(loadProductViewAdapter);
        adapter.setProductList(productList);
        adapter.setSearchFragment(this);

        LoadProductHandler handler = LoadProductHandler.getLoadPoductHandler();
        handler.setProductList2(productList);
        handler.setLoadProductViewAdapter2(loadProductViewAdapter);
        handler.getHandler();


    }

    public void initProducts(){
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(appCompatActivity);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
       // recyclerView.setHasFixedSize(true);
        loadProductViewAdapter = new LoadProductViewAdapter(productList);
        recyclerView.setAdapter(loadProductViewAdapter);
        loadProductViewAdapter.notifyDataSetChanged();
    }

    public void getData(){
        tagList.add(new Tag("tag_1","Nấu nhanh",""));
        tagList.add(new Tag("tag_2","Mới nhất",""));
        tagList.add(new Tag("tag_3","Giá rẻ",""));
        tagList.add(new Tag("tag_4","Còn lại",""));
        tagList.add(new Tag("tag_5","Bán chạy",""));
//        tagList.add(new Tag("tag_6","Đánh giá",""));
    }
    public void changeColor(int index){

        for (int i =0;i<reTag.getChildCount();i++){

            View view = reTag.getChildAt(i);
            TextView button1 = view.findViewById(R.id.name_tag);

            if (i == index){
                button1.setBackgroundResource(R.color.color_main);
            }else {
                button1.setBackgroundResource(R.color.white);
            }
        }

    }

    public void getView(View view){

        reTag = view.findViewById(R.id.reTag);
        tagList = new ArrayList<>();
        cardView = view.findViewById(R.id.cart_search);
        editText = view.findViewById(R.id.search);
        recyclerView = view.findViewById(R.id.list_sp);
        productList = new ArrayList<>();
        ring = view.findViewById(R.id.notify);
        scrollView = view.findViewById(R.id.scroll_sp);
    }

}