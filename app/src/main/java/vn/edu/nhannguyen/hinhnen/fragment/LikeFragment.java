package vn.edu.nhannguyen.hinhnen.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import vn.edu.nhannguyen.hinhnen.R;
import vn.edu.nhannguyen.hinhnen.adapter.LikeImageAdapter;
import vn.edu.nhannguyen.hinhnen.model.LikeImage;




@SuppressLint("ValidFragment")
public class LikeFragment extends Fragment {
    GridLayoutManager gridLayoutManager;
    RecyclerView recyclerView;
    LikeImageAdapter likeImageAdapter;



    ArrayList<LikeImage> likeImages;
    SQLiteDatabase database = null;
    private Context context;


    /*public LikeFragment (Context context) {
        this.context = context;
    }*/

    public LikeFragment(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //database = SQLiteDatabase.openDatabase("/data/user/0/vn.edu.nhannguyen.hinhnen/databases/dbYeuThich.db",null,SQLiteDatabase.OPEN_READWRITE);
        database = context.openOrCreateDatabase("dbYeuThich.db", Context.MODE_PRIVATE,null);
        likeImages = new ArrayList<>();

        Cursor cursor = database.rawQuery("select *from YeuThich", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Integer maImage = cursor.getInt(0);
            String tenImage = cursor.getString(1);
            String urlImage = cursor.getString(2);

            LikeImage item = new LikeImage();
            item.setMaSP(maImage);
            item.setTenSP(tenImage);
            item.setUrlSP(urlImage);
            likeImages.add(item);

            cursor.moveToNext();
        }
        cursor.close();


        View view = inflater.inflate(R.layout.fragment_three, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rcLike);
        gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        likeImageAdapter = new LikeImageAdapter(getContext(), likeImages);
        recyclerView.setAdapter(likeImageAdapter);
        likeImageAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        database = context.openOrCreateDatabase("dbYeuThich.db", Context.MODE_PRIVATE,null);
        likeImages = new ArrayList<>();

        Cursor cursor = database.rawQuery("select *from YeuThich", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Integer maImage = cursor.getInt(0);
            String tenImage = cursor.getString(1);
            String urlImage = cursor.getString(2);

            LikeImage item = new LikeImage();
            item.setMaSP(maImage);
            item.setTenSP(tenImage);
            item.setUrlSP(urlImage);
            likeImages.add(item);

            cursor.moveToNext();
        }
        cursor.close();

        likeImageAdapter = new LikeImageAdapter(getContext(), likeImages);
        recyclerView.setAdapter(likeImageAdapter);
        likeImageAdapter.notifyDataSetChanged();
    }
}
