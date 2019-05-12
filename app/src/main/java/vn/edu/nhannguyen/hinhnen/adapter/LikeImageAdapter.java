package vn.edu.nhannguyen.hinhnen.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.nhannguyen.hinhnen.R;
import vn.edu.nhannguyen.hinhnen.activity.ShowImgActivity;
import vn.edu.nhannguyen.hinhnen.model.LikeImage;

public class LikeImageAdapter extends RecyclerView.Adapter<LikeImageAdapter.ViewHolder> {
    private Context context;
    private List <LikeImage> likeImages;

    public LikeImageAdapter(Context context, List<LikeImage> likeImages) {
        this.context = context;
        this.likeImages = likeImages;
    }

    @NonNull
    @Override
    public LikeImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.row_item, viewGroup, false);
        return new LikeImageAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LikeImageAdapter.ViewHolder viewHolder, int i) {
        LikeImage contact = likeImages.get(i);
        Glide.with(context).load(contact.getUrlSP()).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return likeImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowImgActivity.class);
                    LikeImage likeImage = likeImages.get(getAdapterPosition());
                    Integer maSP = likeImage.getMaSP();
                    String tenSP = likeImage.getTenSP();
                    String urlSP = likeImage.getUrlSP();

                    intent.putExtra("maImage", maSP);
                    intent.putExtra("tenImage",tenSP);
                    intent.putExtra("urlImage", urlSP);
                    intent.putExtra("flag","Onn");
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
