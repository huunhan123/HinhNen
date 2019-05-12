package vn.edu.nhannguyen.hinhnen.model;

public class LikeImage {
    private Integer maSP;
    private String tenSP;
    private String urlSP;

    public LikeImage() {
        super();
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.urlSP = urlSP;
    }

    public Integer getMaSP() {
        return maSP;
    }

    public void setMaSP(Integer maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getUrlSP() {
        return urlSP;
    }

    public void setUrlSP(String urlSP) {
        this.urlSP = urlSP;
    }
}
