package vn.edu.nhannguyen.hinhnen.API;


import vn.edu.nhannguyen.hinhnen.model.LoaiSanPham;
import vn.edu.nhannguyen.hinhnen.model.SanPham;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class APIUntil {
    private static String baseURL = "http://hinhnen.somee.com/api/"; // https://hinhnen.azurewebsites.net http://hinhnen.somee.com/api/

    public static APIService getServer() {
        return APIClient.getApiClientLSP(baseURL).create(APIService.class);
    }
}
