package vn.edu.nhannguyen.hinhnen.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class LoadImage  {
    private String urlImg;
    private  String tenImg;

    public LoadImage() {
        super();
        this.urlImg = urlImg;
        this.tenImg = tenImg;
    }


    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
    public String getTenImg() {
        return tenImg;
    }

    public void setTenImg(String tenImg) {
        this.tenImg = tenImg;
    }

}
