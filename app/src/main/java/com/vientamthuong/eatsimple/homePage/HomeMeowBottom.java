package com.vientamthuong.eatsimple.homePage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.cartPage.CartPageFragment;
import com.vientamthuong.eatsimple.connection.CheckConnection;
import com.vientamthuong.eatsimple.diaLog.DiaLogLoader;
import com.vientamthuong.eatsimple.diaLog.DiaLogLostConnection;
import com.vientamthuong.eatsimple.header.HeaderPublicFragment;
import com.vientamthuong.eatsimple.loadData.LoadDataConfiguration;
import com.vientamthuong.eatsimple.loadData.LoadImageForView;
import com.vientamthuong.eatsimple.loadProductByID.LoadProductHelp;
import com.vientamthuong.eatsimple.mennuSearch.SearchFragment;
import com.vientamthuong.eatsimple.menuNotify.NotifyPageFragment;
import com.vientamthuong.eatsimple.menuWishlist.WishlistFragment;
import com.vientamthuong.eatsimple.protocol.ActivityProtocol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeMeowBottom extends AppCompatActivity implements ActivityProtocol {

    private MeowBottomNavigation bottomNavigation;
    private List<LoadImageForView> imagesNeedLoad;
    private HomePageDanhMucFragment homePageDanhMucFragment;
    private DiaLogLostConnection diaLogLostConnection;
   // private DiaLogLoader diaLogLoader;
    private boolean isRunningVolley;
    private boolean isRun = false;
    HomePageActivity homePageActivity;
    // Thời gian thoát activity
    private long lastTimePressBack;
    private CartPageFragment cartPageFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_meow_bottom);

        getView();
        addView();
        eventBottom();

    }

    void eventBottom() {
        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment frameLayout = null;

                switch (item.getId()){
                    case 1:

                        frameLayout = new SearchFragment();

                        break;
                    case 2:
                        frameLayout = new WishlistFragment();

                        break;
                    case 3:

                        frameLayout = homePageActivity;
                        LoadProductHelp.getLoadProductHelp().setNum(0);
                        LoadProductHelp.getLoadProductHelp().setYMIN(140);
                        break;
                    case 4:
                        frameLayout =new NotifyPageFragment();
                        break;
                    case 5:
                        frameLayout = cartPageFragment;

                        break;
                }

                loadFragment(frameLayout);
            }
        });

       // bottomNavigation.setCount(3,"10");


        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("call");

        if (bundle != null){
            String re = bundle.getString("dichuyen");
            switch (re){
                case "cart":
                    bottomNavigation.show(5,true);
                 //   loadFragment(new CartPageFragment());
                    break;
                default:
                    bottomNavigation.show(3,true);
                    break;
            }
        }else {
            bottomNavigation.show(3,true);
        }

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

                Toast.makeText(getApplicationContext(), "You onclick" + item.getId(), Toast.LENGTH_SHORT).show();

            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "You reselect" + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    void loadFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_home,fragment).commit();
    }
    @Override
    public boolean isRunningVolley() {
        return isRunningVolley;
    }

    @Override
    public void setRunningVolley(boolean isRunningVolley) {
        this.isRunningVolley = isRunningVolley;
    }

    @Override
    public void loadImageFromIntenet() {
        // Tải hình về
      //  System.out.println("Size hinh 1:"+imagesNeedLoad.size());

        if (imagesNeedLoad.size() > 0) {
            System.out.println("Size hinh 1:"+imagesNeedLoad.size());
            Thread thread = new Thread(() -> {
                boolean isError = false;
                do {
                    int count = 0;
                    while (count < imagesNeedLoad.size()) {
                        System.out.println("Size hinh:"+imagesNeedLoad.size());
                        LoadImageForView loadImageForView = imagesNeedLoad.get(count);
                        if (loadImageForView!= null &&!loadImageForView.isStart()) {
                            loadImageForView.setStart(true);
                            loadImageForView.run();
                            count++;
                        } else if(loadImageForView!= null)  {
                            if (loadImageForView.isComplete()) {
                                // Kiểm tra nếu thằng xong này type là danh mục thì thôgn báo cho adpater danh mục
                                if (imagesNeedLoad.get(count).getType() == LoadDataConfiguration.IMAGE_DANH_MUC) {
                                    runOnUiThread(() -> homePageDanhMucFragment.update());
                                }
                                imagesNeedLoad.remove(count);
                            } else if (loadImageForView.isError()) {
                                isError = true;
                                break;
                            } else {
                                count++;
                            }
                        }
                    }
                } while (!isError && imagesNeedLoad.size() != 0);
                // Cho biến là hết chạy volley
                isRunningVolley = false;
                // Lỗi mạng
                if (isError) {
                    runOnUiThread(() -> {
                        diaLogLostConnection.show();
                        diaLogLostConnection.getBtTry().setOnClickListener(v -> {
                            if (CheckConnection.getInstance().isConnected(this)) {
                                diaLogLostConnection.dismiss();
                                // Cho các đối tượng hiện tại trong list về trạng thái ban đầu
                                for (LoadImageForView loadImageForView : imagesNeedLoad) {
                                    if (loadImageForView.isStart()) {
                                        loadImageForView.setStart(false);
                                    }
                                    if (loadImageForView.isError()) {
                                        loadImageForView.setError(false);
                                    }
                                }
                                if (!isRunningVolley) {
                                    isRunningVolley = true;
                                    loadImageFromIntenet();
                                }
                            } else {
                                Toast.makeText(this, "Không tìm thấy kết nối!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    });
                }
            });
            thread.start();
        }
    }


    private void addView() {
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.activity_home_page_search));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.activity_home_page_icon_heart_select));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.activity_home_page_icon_home_select));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.activity_home_page_icon_ring_select));
        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.activity_home_page_icon_cart_select));
    }

    void getView() {
        bottomNavigation = findViewById(R.id.bottom_navigation_home);
        homePageActivity = new HomePageActivity();
        homePageActivity.setHomeMeowBottom(HomeMeowBottom.this);
        homePageActivity.setAppCompatActivity(HomeMeowBottom.this);

        cartPageFragment = new CartPageFragment();

    }

        @Override
    public void onBackPressed() {
        if (lastTimePressBack == 0 || System.currentTimeMillis() - lastTimePressBack > 2000) {
            lastTimePressBack = System.currentTimeMillis();
            Toast.makeText(this, getString(R.string.exit_app), Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }

    public HomePageDanhMucFragment getHomePageDanhMucFragment() {
        return homePageDanhMucFragment;
    }

    public void setHomePageDanhMucFragment(HomePageDanhMucFragment homePageDanhMucFragment) {
        this.homePageDanhMucFragment = homePageDanhMucFragment;
    }

    public void setImagesNeedLoad(List<LoadImageForView> imagesNeedLoad) {
        this.imagesNeedLoad = imagesNeedLoad;
    }
}