package com.vientamthuong.eatsimple.homePage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.vientamthuong.eatsimple.R;

import java.io.Serializable;

public class HomeMeowBottom extends AppCompatActivity {

    private MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_meow_bottom);


        getView();
        addView();
       // eventBottom();

    }

//    void eventBottom() {
//        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
//            @Override
//            public void onShowItem(MeowBottomNavigation.Model item) {
//                Fragment frameLayout = null;
//
//                switch (item.getId()){
//                    case 1:
//                        frameLayout = new SearchFragment();
//                        break;
//                    case 2:
//                        frameLayout = new CartPageFragment();
//                        break;
//                    case 3:
//
//                        frameLayout = new HomePageFragment();
//
//                        break;
//                    case 4:
//                        frameLayout = new WishlistFragment();
//                        break;
//                    case 5:
//                        frameLayout = new NotifyPageFragment();
//                        break;
//                }
//                loadFragment(frameLayout);
//            }
//        });
//
//        bottomNavigation.setCount(1,"10");
//        bottomNavigation.show(3,true);
//        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
//            @Override
//            public void onClickItem(MeowBottomNavigation.Model item) {
//
//                Toast.makeText(getApplicationContext(), "You onclick" + item.getId(), Toast.LENGTH_SHORT).show();
//
//
//
//            }
//        });
//
//        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
//            @Override
//            public void onReselectItem(MeowBottomNavigation.Model item) {
//                Toast.makeText(getApplicationContext(), "You reselect" + item.getId(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

    void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_home,fragment).commit();
    }

    private void addView() {
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.activity_home_page_icon_home_select));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.activity_home_page_icon_heart_select));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.activity_home_page_search));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.activity_home_page_icon_ring_select));
        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.activity_home_page_icon_cart_select));

    }

    void getView() {
        bottomNavigation = findViewById(R.id.bottom_navigation_home);
    }
}