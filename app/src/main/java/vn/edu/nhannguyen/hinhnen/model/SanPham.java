package vn.edu.nhannguyen.hinhnen.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SanPham implements Serializable {
    // String url = "http://192.168.1.3/";
    @SerializedName("maSP")
    @Expose
    private Integer maSP;
    @SerializedName("maLSP")
    @Expose
    private Integer maLSP;
    @SerializedName("TenSP")
    @Expose
    private String tenSP;
    @SerializedName("HinhSP")
    @Expose
    private String hinhSP;

    @SerializedName("YeuThich")
    @Expose
    private Integer yeuThich;

    public Integer getMaSP() {
        return maSP;
    }

    public void setMaSP(Integer maSP) {
        this.maSP = maSP;
    }

    public Integer getMaLSP() {
        return maLSP;
    }

    public void setMaLSP(Integer maLSP) {
        this.maLSP = maLSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }
    public String getHinhSP() {
        //String hinh = url +hinhSP;
        //return hinh;
        return hinhSP;
    }

    public void setHinhSP(String hinhSP) {

        this.hinhSP =  hinhSP;
    }

    public Integer getYeuThich() {
        return yeuThich;
    }

    public void setYeuThich(Integer yeuThich) {
        this.yeuThich = yeuThich;
    }
}

