package vn.edu.nhannguyen.hinhnen.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import vn.edu.nhannguyen.hinhnen.R;

import vn.edu.nhannguyen.hinhnen.fragment.LikeFragment;
import vn.edu.nhannguyen.hinhnen.fragment.LoadImgFragment;
import vn.edu.nhannguyen.hinhnen.fragment.WallFragment;



public class MainActivity extends AppCompatActivity {
    //Databasa databasa;
    public static String DATABASE_NAME = "dbYeuThich.db";
    public String DB_PATH_SUFFIX = "/databases/";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_image_black_24dp,
            R.drawable.ic_star_black_24dp,
            R.drawable.detail_save
    };
    private static final int PERMISSION_REQUEST_CODE = 1000 ;

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case PERMISSION_REQUEST_CODE:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toProcessCopyDatabaseFromAssetsToSystem();

        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE


                },PERMISSION_REQUEST_CODE);
            }


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        //setupTabIcons();

    }
    private void toProcessCopyDatabaseFromAssetsToSystem() {
        File file = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!file.exists()) {// kiểm tra cái thư mục databases đã tồn tại chưa nếu chưa thì tạo
            try {
                toCopyDatabaseFormAsset();
                Log.d("Thành công","1");
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "ABC" + e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void toCopyDatabaseFormAsset() {
        try {
            InputStream myInput = getAssets().open(DATABASE_NAME); // mở file lên
            String outFileName = toGetDatabasePath(); // lấy cái đường dẫn lưu trữ
            File file = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!file.exists()) {// kiểm tra cái thư mục databases đã tồn tại chưa nếu chưa thì tạo
                file.mkdir();
            }
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();

        } catch (Exception e) {
            Log.e("Lỗi Sao Chép !!!", e.toString());
        }
    }

    private String toGetDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME; // Trả về đường dẫn phải lưu trữ
    }

    /*private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }*/


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new WallFragment(), "Hình ảnh");
        adapter.addFragment(new LikeFragment(MainActivity.this), "Yêu thích");
        adapter.addFragment(new LoadImgFragment(), "Bộ sưu tập");
        viewPager.setAdapter(adapter);
    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        private  ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        private void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
