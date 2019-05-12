package vn.edu.nhannguyen.hinhnen.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.nhannguyen.hinhnen.API.APIService;
import vn.edu.nhannguyen.hinhnen.API.APIUntil;
import vn.edu.nhannguyen.hinhnen.R;
import vn.edu.nhannguyen.hinhnen.adapter.SanPhamAdapter;
import vn.edu.nhannguyen.hinhnen.model.SanPham;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class WallFragment extends Fragment {

    GridLayoutManager gridLayoutManager;
    RecyclerView recyclerView;
    SanPhamAdapter sanPhamAdapter;
    List<SanPham> sanPhams;
    APIService apiServiceSP;

    public WallFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wall, container, false);
        apiServiceSP = APIUntil.getServer();
        recyclerView = view.findViewById(R.id.rcWall);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        //kéo lên đầu trang
       /* FloatingActionButton moveTop = view.findViewById(R.id.fab);
        moveTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(0);
            }
        });*/
        //lấy dữ liệu từ service
        apiServiceSP.APISPTangDan().enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                sanPhams =  response.body();
                sanPhamAdapter = new SanPhamAdapter(getContext(), sanPhams);
                recyclerView.setAdapter(sanPhamAdapter);
            }
            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {

            }
        });
        return view;
    }
    
}
