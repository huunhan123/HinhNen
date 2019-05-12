package vn.edu.nhannguyen.hinhnen.fragment;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.IOException;

import vn.edu.nhannguyen.hinhnen.R;
import vn.edu.nhannguyen.hinhnen.activity.ReviewActivity;
import vn.edu.nhannguyen.hinhnen.activity.ShowImgActivity;
import vn.edu.nhannguyen.hinhnen.model.SanPham;

import static android.app.WallpaperManager.getInstance;

public class ReviewGreenBlockFragment extends Fragment {
    Button btnSetGreenBlock;
    ImageView imgHinh;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_review_green_block, container, false);
        final Intent intent = getActivity().getIntent();
        final String url = intent.getStringExtra("sp");
        //final SanPham sanPham = (SanPham) intent.getSerializableExtra("sp");
        //Toast.makeText(getActivity(), url, Toast.LENGTH_SHORT).show();
        imgHinh = view.findViewById(R.id.imgHinh);
        Glide.with(ReviewGreenBlockFragment.this).load(url).into(imgHinh);

        btnSetGreenBlock =  view.findViewById(R.id.btnSetGreenBlock);
        btnSetGreenBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Xác Nhận");
                builder.setMessage("Bạn có muốn cài hình nền không?");
                builder.setCancelable(true);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Glide.with(getActivity().getApplicationContext()).asBitmap()
                                .load(url)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        // img.setImageBitmap(resource);
                                        try {
                                            WallpaperManager manager = getInstance(getActivity().getApplicationContext());
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                                // API 24 trở len
                                                manager.setBitmap(resource);
                                                manager.setBitmap(resource,null, true, WallpaperManager.FLAG_LOCK);
                                            }
                                            Toast.makeText(getActivity(), "Cài đặt thành công", Toast.LENGTH_SHORT).show();

                                        } catch (IOException e) {
                                            Toast.makeText(getActivity(), "Cài đặt không thành công", Toast.LENGTH_SHORT).show();
                                            e.printStackTrace();
                                        }
                                    }
                                });
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                final AlertDialog alertDialog = builder.create();
                alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onShow(DialogInterface arg0) {
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#000000"));
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#000000"));
                    }
                });
                alertDialog.show();
                //set màu cho ch? xác nh?n
                int textViewId = alertDialog.getContext().getResources().getIdentifier("android:id/alertTitle", null, null);
                TextView tv = (TextView) alertDialog.findViewById(textViewId);
                if (tv != null) {
                    tv.setTextColor(Color.parseColor("#FF2121"));
                }
            }
        });
        return view;
    }

}
