package vn.edu.nhannguyen.hinhnen.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.edu.nhannguyen.hinhnen.R;
import vn.edu.nhannguyen.hinhnen.adapter.LoadImageAdapter;
import vn.edu.nhannguyen.hinhnen.model.LoadImage;


public class LoadImgFragment extends Fragment{

    GridLayoutManager gridLayoutManager;
    RecyclerView recyclerView;
    LoadImageAdapter loadImageAdapter;
    List<LoadImage> loadImages;


    Uri Image_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    public LoadImgFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        loadImages = new ArrayList<>();
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = contentResolver.query(Image_URI, null, null, null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String urlImg =cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            String tenImg =cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));


            LoadImage item = new LoadImage();
            item.setUrlImg(urlImg);
            item.setTenImg(tenImg);
            loadImages.add(item);

            //Log.e("GT",urlImg);
            cursor.moveToNext();
        }


        View view = inflater.inflate(R.layout.fragment_two, container, false);
        recyclerView =(RecyclerView) view.findViewById(R.id.rcLoad);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);


        loadImageAdapter = new LoadImageAdapter(getContext(), loadImages);
        recyclerView.setAdapter(loadImageAdapter);
        loadImageAdapter.notifyDataSetChanged();
        return view;
    }
}


