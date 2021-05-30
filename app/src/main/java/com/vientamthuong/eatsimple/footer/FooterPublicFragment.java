package com.vientamthuong.eatsimple.footer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.homePage.HomePageConfiguration;

public class FooterPublicFragment extends Fragment {

    // Hình phía trên
    private ImageButton imageButtonHome;
    private ImageButton imageButtonHeart;
    private ImageButton imageButtonRing;
    private ImageButton imageButtonCart;
    // Chấm vàng phía dưới
    private CardView cardViewHome;
    private CardView cardViewHeart;
    private CardView cardViewRing;
    private CardView cardViewCart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_footer_public, container, false);
        getView(view);
        // Xử lý giao diện
        handleView();
        return view;
    }

    private void handleView() {
        Bundle bundle = getArguments();
        int data = bundle.getInt("data");
        switch (data) {
            case HomePageConfiguration.HOME:
                imageButtonHome.setBackground(getActivity().getDrawable(R.drawable.activity_home_page_icon_home_select));
                cardViewHome.setVisibility(View.VISIBLE);
                break;
            case HomePageConfiguration.HEART:
                imageButtonHeart.setBackground(getActivity().getDrawable(R.drawable.activity_home_page_icon_heart_select));
                cardViewHeart.setVisibility(View.VISIBLE);
                break;
            case HomePageConfiguration.RING:
                imageButtonRing.setBackground(getActivity().getDrawable(R.drawable.activity_home_page_icon_ring_select));
                cardViewRing.setVisibility(View.VISIBLE);
                break;
            case HomePageConfiguration.CART:
                imageButtonCart.setBackground(getActivity().getDrawable(R.drawable.activity_home_page_icon_cart_select));
                cardViewCart.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void getView(View view) {
        imageButtonHome = view.findViewById(R.id.activity_home_page_home);
        imageButtonHeart = view.findViewById(R.id.activity_home_page_heart);
        imageButtonRing = view.findViewById(R.id.activity_home_page_ring);
        imageButtonCart = view.findViewById(R.id.activity_home_page_cart);
        cardViewHome = view.findViewById(R.id.activity_home_page_home_select);
        cardViewHeart = view.findViewById(R.id.activity_home_page_heart_select);
        cardViewRing = view.findViewById(R.id.activity_home_page_ring_select);
        cardViewCart = view.findViewById(R.id.activity_home_page_cart_select);
    }
}
