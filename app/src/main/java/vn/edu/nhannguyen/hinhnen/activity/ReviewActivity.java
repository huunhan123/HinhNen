package vn.edu.nhannguyen.hinhnen.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.edu.nhannguyen.hinhnen.R;
import vn.edu.nhannguyen.hinhnen.fragment.LoadImgFragment;
import vn.edu.nhannguyen.hinhnen.fragment.ReviewGreenBlockFragment;
import vn.edu.nhannguyen.hinhnen.fragment.ReviewGreenMainFragment;
import vn.edu.nhannguyen.hinhnen.fragment.LikeFragment;
import vn.edu.nhannguyen.hinhnen.fragment.WallFragment;
import vn.edu.nhannguyen.hinhnen.model.SanPham;

public class ReviewActivity extends AppCompatActivity {
    private TabLayout tabtabLayout;
    private ViewPager vPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        vPager = (ViewPager) findViewById(R.id.vpager);
        setupViewPager(vPager);

        tabtabLayout = (TabLayout) findViewById(R.id.tabtab);
        tabtabLayout.setupWithViewPager(vPager);


        //nhan su lieu tu ShowImgActivity

       // nhanDuLieu();

    }

   /* private void nhanDuLieu() {
        final Intent intent = getIntent();
        final String url = intent.getStringExtra("sp");
        //Toast.makeText(ReviewActivity.this, url, Toast.LENGTH_SHORT).show();
    }*/


    private void setupViewPager(ViewPager viewPager) {
        ReviewActivity.ViewPagerAdapter adapter = new ReviewActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ReviewGreenMainFragment(), "Màn hình chính");
        adapter.addFragment(new ReviewGreenBlockFragment(), "Màn hình khóa");
        viewPager.setAdapter(adapter);
    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}


