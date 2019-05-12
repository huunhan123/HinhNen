package vn.edu.nhannguyen.hinhnen.adapter;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import vn.edu.nhannguyen.hinhnen.R;
import vn.edu.nhannguyen.hinhnen.activity.ShowImgActivity;
import vn.edu.nhannguyen.hinhnen.model.LoadImage;


public class LoadImageAdapter extends RecyclerView.Adapter<LoadImageAdapter.ViewHolder> {

    private Context context;
    private List<LoadImage> loadImages;

    public LoadImageAdapter(Context context, List<LoadImage> loadImages) {
        this.context = context;
        this.loadImages = loadImages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.row_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        LoadImage contact = loadImages.get(i);
        Glide.with(context).load(contact.getUrlImg()).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return loadImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowImgActivity.class);
                    LoadImage loadImage = loadImages.get(getAdapterPosition());
                    String urlImage = loadImage.getUrlImg();
                    String tenImage = loadImage.getTenImg();
                    //Toast.makeText(context,"DAY ROI "+ loadImage.getUrlImg(), Toast.LENGTH_SHORT).show();
                    intent.putExtra("urlImage",urlImage);
                    intent.putExtra("tenImage",tenImage);
                    intent.putExtra("flag","Off");
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

}
