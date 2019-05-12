package vn.edu.nhannguyen.hinhnen.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
//import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import vn.edu.nhannguyen.hinhnen.R;
import vn.edu.nhannguyen.hinhnen.activity.ShowImgActivity;
import vn.edu.nhannguyen.hinhnen.model.SanPham;

import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {

    Context context;
    List<SanPham> sanPhams;

    public SanPhamAdapter(Context context, List<SanPham> sanPhams) {
        this.context = context;
        this.sanPhams = sanPhams;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        SanPham contact = sanPhams.get(i);
        Glide.with(context).load(contact.getHinhSP()).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return sanPhams.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        //CardView cardView;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            //cardView = itemView.findViewById(R.id.cvDiamond);
            imageView = itemView.findViewById(R.id.imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowImgActivity.class);
                    SanPham sanPham = sanPhams.get(getAdapterPosition());
//                    Toast.makeText(context,"DAY ROI "+ sanPham.getHinhSP(), Toast.LENGTH_SHORT).show();
                    //intent.putExtra("sp", sanPham);

                    String urlImage = sanPham.getHinhSP();
                    String tenImage = sanPham.getTenSP();
                    int maImage = sanPham.getMaSP();
                    intent.putExtra("urlImage",urlImage);
                    intent.putExtra("tenImage",tenImage);
                    intent.putExtra("maImage",maImage);
                    intent.putExtra("flag","On");
                    v.getContext().startActivity(intent);
                    // g?i l?nh d?n android system d? m? màn hình 2.
                }
            });
        }
    }
}
