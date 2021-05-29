package com.vientamthuong.eatsimple.header;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.diaLog.DiaLogLoader;
import com.vientamthuong.eatsimple.loadData.LoadDataConfiguration;
import com.vientamthuong.eatsimple.loadData.LoadImageForView;
import com.vientamthuong.eatsimple.protocol.ActivityProtocol;

import java.util.List;
import java.util.Objects;

public class HeaderPublicFragment extends Fragment {

    // Button Menu
    private AppCompatButton appCompatButtonMenu;
    private LottieAnimationView lottieAnimationMenu;
    // Button avatar
    private AppCompatButton appCompatButtonAvatar;
    private LottieAnimationView lottieAnimationViewAvatar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_header_public, container, false);
        getView(view);
        return view;
    }

    public void getData(DatabaseReference root, DiaLogLoader diaLogLoader, List<LoadImageForView> imagesNeedLoad, AppCompatActivity appCompatActivity) {
        // Lay activity protocol
        ActivityProtocol activityProtocol = (ActivityProtocol) appCompatActivity;
        // Load hình cho header
        DatabaseReference databaseHomePage = root.child("activity_home_page");
        databaseHomePage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Hiện màn hình chờ
                diaLogLoader.show();
                imagesNeedLoad.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    switch (Objects.requireNonNull(dataSnapshot.getKey())) {
                        case "activity_home_page_button_menu":
                            LoadImageForView loadImageForView = new LoadImageForView(appCompatButtonMenu,
                                    appCompatActivity,
                                    lottieAnimationMenu,
                                    LoadDataConfiguration.VIEW_NORMAL,
                                    Objects.requireNonNull(dataSnapshot.getValue()).toString());
                            imagesNeedLoad.add(loadImageForView);
                            break;
                        case "activity_home_page_avatar":
                            loadImageForView = new LoadImageForView(appCompatButtonAvatar,
                                    appCompatActivity,
                                    lottieAnimationViewAvatar,
                                    LoadDataConfiguration.VIEW_NORMAL,
                                    Objects.requireNonNull(dataSnapshot.getValue()).toString());
                            imagesNeedLoad.add(loadImageForView);
                            break;
                    }
                }
                // Tải dữ liệu từ firebase về thành công
                // Và giờ tải hình từ các link hình
                if (!activityProtocol.isRunningVolley()) {
                    activityProtocol.setRunningVolley(true);
                    activityProtocol.loadImageFromIntenet();
                }
                // Tắt màn hình chờ
                diaLogLoader.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(appCompatActivity, "Lỗi tải dữ liệu từ firebase !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getView(View view) {
        // Button menu
        appCompatButtonMenu = view.findViewById(R.id.activity_home_page_button_menu_button);
        lottieAnimationMenu = view.findViewById(R.id.activity_home_page_button_menu_animation);
        // Button avatar
        appCompatButtonAvatar = view.findViewById(R.id.activity_home_page_avatar_button);
        lottieAnimationViewAvatar = view.findViewById(R.id.activity_home_page_avatar_animation);
    }
}
