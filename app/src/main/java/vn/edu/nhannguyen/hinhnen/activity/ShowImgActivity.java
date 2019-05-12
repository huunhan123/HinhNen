package vn.edu.nhannguyen.hinhnen.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.squareup.picasso.Picasso;


import java.io.IOException;
import java.util.List;
import java.util.UUID;

import dmax.dialog.SpotsDialog;
import vn.edu.nhannguyen.hinhnen.R;
import vn.edu.nhannguyen.hinhnen.adapter.SanPhamAdapter;
import vn.edu.nhannguyen.hinhnen.adapter.SaveImageHelper;
import vn.edu.nhannguyen.hinhnen.fragment.ReviewGreenBlockFragment;

import vn.edu.nhannguyen.hinhnen.model.SanPham;

import static android.app.WallpaperManager.getInstance;

public class ShowImgActivity extends AppCompatActivity {
    ImageView img;
    FloatingActionButton fabOption, favorites, fab_set, fab_save, fab_share, un_option,fabChinh, fabKhoa, fabCaHai;
    BottomNavigationView bottomNavigationView;
    String urlImage;
    String tenImage;
    int maImage;
    SQLiteDatabase database = null;

    boolean  Click = false;
    boolean  flagDownload = false;
    private static final int PERMISSION_REQUEST_CODE = 1000 ;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case PERMISSION_REQUEST_CODE:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_img);

        //Ánh x?
        addConTrol();

        addEvents();
        showDataBase();

    }

    private void showDataBase() {
        database = openOrCreateDatabase(MainActivity.DATABASE_NAME,MODE_PRIVATE,null);
    }


    private void addConTrol() {
        img = findViewById(R.id.showImg);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        fabChinh = findViewById(R.id.fabChinh);
        fabKhoa = findViewById(R.id.fabKhoa);
        fabCaHai = findViewById(R.id.fabCaHai);

    }

    private void addEvents() {
        // truy?n ?nh và l?y ?nh
        final Intent intent = getIntent();
        //final SanPham sanPham = (SanPham) intent.getSerializableExtra("sp");
        urlImage = intent.getStringExtra("urlImage");
        tenImage = intent.getStringExtra("tenImage");
        maImage = intent.getIntExtra("maImage",0);
        //Log.e("ID", String.valueOf(maImage));
        //Toast.makeText(this, String.valueOf(maImage), Toast.LENGTH_SHORT).show();
        final Menu menu = bottomNavigationView.getMenu();
        //set menu cho fragment tải về
        if(getIntent().getStringExtra("flag").equals("Off")){
            //Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show();
            menu.findItem(R.id.action_luu).setVisible(false);
            menu.findItem(R.id.action_yeuthich).setVisible(false);
        }
        // set menu cho fragment yêu thích
        if(getIntent().getStringExtra("flag").equals("Onn")){
            //Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show();
            menu.findItem(R.id.action_yeuthich).setIcon(R.drawable.ic_action_favoutite);
        }
//        Toast.makeText(this, "UK "+sanPham.getHinhSP(), Toast.LENGTH_SHORT).show();

        //set icon khi ảnh đã yêu thích
        database = openOrCreateDatabase("dbYeuThich.db", MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("select maSP from YeuThich where maSP = "+maImage,null);
        cursor.moveToFirst();
        if(cursor.moveToFirst()){
            menu.findItem(R.id.action_yeuthich).setIcon(R.drawable.ic_action_favoutite);
            //Toast.makeText(this, "Có data", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        Glide.with(ShowImgActivity.this).load(urlImage).into(img);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_thietlap:
                        if(Click == false) {
                            HienThiIcon();

                            fabChinh.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // AlertDialog.Builder builder = new AlertDialog.Builder(ShowImgActivity.this,R.style.AlertDialogCustom);
                                    AnIcon();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ShowImgActivity.this);
                                    builder.setTitle("Xác Nhận");
                                    builder.setMessage("Bạn có muốn cài hình nền không?");
                                    builder.setCancelable(true);
                                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            Glide.with(getApplicationContext()).asBitmap()
                                                    .load(urlImage)
                                                    .into(new SimpleTarget<Bitmap>() {
                                                        @Override
                                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                            // img.setImageBitmap(resource);
                                                            try {
                                                                WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
                                                                manager.setBitmap(resource);
                                                                Toast.makeText(ShowImgActivity.this, "Cài đặt thành công", Toast.LENGTH_SHORT).show();

                                                            } catch (IOException e) {
                                                                Toast.makeText(ShowImgActivity.this, "Cài đặt không thành công", Toast.LENGTH_SHORT).show();
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    });

                                        }
                                    });
                                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    final AlertDialog alertDialog = builder.create();
                                    alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
                                        @SuppressLint("ResourceAsColor")
                                        @Override
                                        public void onShow(DialogInterface arg0) {
                                            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#000000"));
                                            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#000000"));
                                        }
                                    });
                                    alertDialog.show();
                                    //set màu cho ch? xác nh?n
                                    int textViewId = alertDialog.getContext().getResources().getIdentifier("android:id/alertTitle", null, null);
                                    TextView tv = (TextView) alertDialog.findViewById(textViewId);
                                    tv.setTextColor(Color.parseColor("#FF2121"));
                                }
                            });
                            fabKhoa.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AnIcon();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ShowImgActivity.this);
                                    builder.setTitle("Xác Nhận");
                                    builder.setMessage("Bạn có muốn cài hình nền không?");
                                    builder.setCancelable(true);
                                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            Glide.with(getApplicationContext()).asBitmap()
                                                    .load(urlImage)
                                                    .into(new SimpleTarget<Bitmap>() {
                                                @Override
                                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                            // img.setImageBitmap(resource);
                                                            try {
                                                                WallpaperManager manager = getInstance(getApplicationContext());
                                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                                                        // API 24 trở len
                                                                        manager.setBitmap(resource,null, true, WallpaperManager.FLAG_LOCK);
                                                                 }
                                                                Toast.makeText(ShowImgActivity.this, "Cài đặt thành công", Toast.LENGTH_SHORT).show();

                                                            } catch (IOException e) {
                                                                Toast.makeText(ShowImgActivity.this, "Cài đặt không thành công", Toast.LENGTH_SHORT).show();
                                                                e.printStackTrace();
                                                            }
                                                }
                                            });
                                        }
                                    });
                                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    final AlertDialog alertDialog = builder.create();
                                    alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
                                        @SuppressLint("ResourceAsColor")
                                        @Override
                                        public void onShow(DialogInterface arg0) {
                                            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#000000"));
                                            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#000000"));
                                        }
                                    });
                                    alertDialog.show();
                                    //set màu cho ch? xác nh?n
                                    int textViewId = alertDialog.getContext().getResources().getIdentifier("android:id/alertTitle", null, null);
                                    TextView tv = (TextView) alertDialog.findViewById(textViewId);
                                    tv.setTextColor(Color.parseColor("#FF2121"));
                                }
                            });
                            fabCaHai.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AnIcon();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ShowImgActivity.this);
                                    builder.setTitle("Xác Nhận");
                                    builder.setMessage("Bạn có muốn cài hình nền không?");
                                    builder.setCancelable(true);
                                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Glide.with(getApplicationContext()).asBitmap()
                                                    .load(urlImage)
                                                    .into(new SimpleTarget<Bitmap>() {
                                                            @Override
                                                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                                // img.setImageBitmap(resource);
                                                                 try {
                                                                    WallpaperManager manager = getInstance(getApplicationContext());
                                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                                                        // API 24 trở len
                                                                        manager.setBitmap(resource);
                                                                        manager.setBitmap(resource,null, true, WallpaperManager.FLAG_LOCK);
                                                                    }
                                                                    Toast.makeText(ShowImgActivity.this, "Cài đặt thành công", Toast.LENGTH_SHORT).show();

                                                                } catch (IOException e) {
                                                                    Toast.makeText(ShowImgActivity.this, "Cài đặt không thành công", Toast.LENGTH_SHORT).show();
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                    });
                                            }
                                        });
                                        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        });
                                        final AlertDialog alertDialog = builder.create();
                                        alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
                                            @SuppressLint("ResourceAsColor")
                                            @Override
                                            public void onShow(DialogInterface arg0) {
                                                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#000000"));
                                                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#000000"));
                                            }
                                        });
                                        alertDialog.show();
                                        //set màu cho ch? xác nh?n
                                        int textViewId = alertDialog.getContext().getResources().getIdentifier("android:id/alertTitle", null, null);
                                        TextView tv = (TextView) alertDialog.findViewById(textViewId);
                                        tv.setTextColor(Color.parseColor("#FF2121"));
                                    }

                            });

                        }
                        else{
                            AnIcon();
                        }
                        break;
                       // Toast.makeText(ShowImgActivity.this, "Recents", Toast.LENGTH_SHORT).show();
                    case R.id.action_yeuthich:
                        AnIcon();
                        Cursor cursor = database.rawQuery("select maSP from YeuThich where maSP = "+maImage,null);
                        ContentValues values = new ContentValues();
                        values.put("maSP", maImage);
                        values.put("tenSP", tenImage);
                        values.put("urlSP", urlImage);
                        String msg = "";
                        //cursor.moveToFirst();
                        if(!cursor.moveToFirst()) {
                            if (database.insert("YeuThich",null,values) == -1){
                                msg = "Thêm thất bại";
                            }
                            else {
                                Log.e("databasssssse",database.getPath());
                                msg = "Thêm thành công";
                            }
                            //Toast.makeText(ShowImgActivity.this, msg, Toast.LENGTH_SHORT).show();
                            menu.findItem(R.id.action_yeuthich).setIcon(R.drawable.ic_action_favoutite);
                        }
                        else {
                            database.delete("YeuThich","maSP =?", new String[]{String.valueOf(maImage)});
                            menu.findItem(R.id.action_yeuthich).setIcon(R.drawable.ic_action_non_favoutite);
                            //Toast.makeText(ShowImgActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(ShowImgActivity.this, "Yêu thích", Toast.LENGTH_SHORT).show();
                        cursor.close();
                        break;
                    case R.id.action_luu:
                        AnIcon();
                        if(ActivityCompat.checkSelfPermission(ShowImgActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE


                                },PERMISSION_REQUEST_CODE);
                            }

                        if(flagDownload == false){
                            String url = urlImage;
                            String fileName = tenImage;
                            //Toast.makeText(ShowImgActivity.this, fileName, Toast.LENGTH_SHORT).show();

                            //Toast.makeText(ShowImgActivity.this, url, Toast.LENGTH_SHORT).show();
                            AlertDialog dialog = new SpotsDialog(ShowImgActivity.this);
                            dialog.show();
                            dialog.setMessage("Đang tải...");

                            //String fileName = UUID.randomUUID().toString() + ".jpg";
                            Picasso.with(getBaseContext())
                                    .load(url)
                                    .into(new SaveImageHelper(getBaseContext(),dialog, getApplicationContext().getContentResolver()
                                            , fileName,
                                            "Image description"));
                            Toast.makeText(ShowImgActivity.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                            flagDownload = true;
                            break;
                        }
                        else {
                            Toast.makeText(ShowImgActivity.this, "Bạn đã tải hình này rồi", Toast.LENGTH_SHORT).show();
                            break;
                        }

                    case R.id.action_share:
                        AnIcon();

                        Intent ShareIntent = new Intent(Intent.ACTION_SEND);
                        ShareIntent.setType("text/plain");
                        ShareIntent.putExtra(Intent.EXTRA_SUBJECT, "the title");
                        ShareIntent.putExtra(Intent.EXTRA_TEXT, urlImage);
                        startActivity(Intent.createChooser(ShareIntent, "Share Using"));
                        //Toast.makeText(ShowImgActivity.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_xemtruoc:
                        AnIcon();
                        String urlHinh;
                        Intent intent = new Intent(ShowImgActivity.this, ReviewActivity.class);
                        urlHinh = urlImage;
                        intent.putExtra("sp", urlHinh);
                        startActivity(intent);
                        //Toast.makeText(ShowImgActivity.this,urlHinh, Toast.LENGTH_SHORT).show();


                        //v.getContext().startActivity(intent);
                        //Toast.makeText(ShowImgActivity.this, sanPham.getHinhSP(), Toast.LENGTH_SHORT).show();

                }
                return true;
            }

            private void HienThiIcon() {
                fabChinh.show();
                fabKhoa.show();
                fabCaHai.show();
                Click = true;
            }
            private void AnIcon() {
                fabChinh.hide();
                fabKhoa.hide();
                fabCaHai.hide();
                Click =false;
            }
        });

        /*fabOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HienThiIcon();
            }
        });
        //?n các icon save, share, set
        un_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnIcon();
            }
        });

        fab_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        //S? ki?n share
        fab_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ShareIntent = new Intent(Intent.ACTION_SEND);
                ShareIntent.setType("text/plain");
                ShareIntent.putExtra(Intent.EXTRA_SUBJECT, "the title");
                ShareIntent.putExtra(Intent.EXTRA_TEXT, sanPham.getHinhSP());
                startActivity(Intent.createChooser(ShareIntent, "Share Using"));
            }
        });
        //s? ki?n set
        fab_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // AlertDialog.Builder builder = new AlertDialog.Builder(ShowImgActivity.this,R.style.AlertDialogCustom);
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowImgActivity.this);
                builder.setTitle("Xác Nhận");
                builder.setMessage("Bạn có muốn cài hình nền không?");
                builder.setCancelable(true);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Glide.with(getApplicationContext()).asBitmap()
                                .load(sanPham.getHinhSP())
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        // img.setImageBitmap(resource);
                                        try {
                                            WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
                                            manager.setBitmap(resource);
                                            Toast.makeText(ShowImgActivity.this, "Cài đặt thành công", Toast.LENGTH_SHORT).show();

                                        } catch (IOException e) {
                                            Toast.makeText(ShowImgActivity.this, "Cài đặt không thành công", Toast.LENGTH_SHORT).show();
                                            e.printStackTrace();
                                        }
                                    }
                                });

                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                final AlertDialog alertDialog = builder.create();
                alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onShow(DialogInterface arg0) {
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#ecf0f1"));
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#ecf0f1"));
                    }
                });
                alertDialog.show();
                //set màu cho ch? xác nh?n
                int textViewId = alertDialog.getContext().getResources().getIdentifier("android:id/alertTitle", null, null);
                TextView tv = (TextView) alertDialog.findViewById(textViewId);
                tv.setTextColor(Color.parseColor("#ecf0f1"));
            }
        });
        //set img

    }


    private void HienThiIcon() {
        fab_set.show();
        fab_save.show();
        fab_share.show();
        un_option.show();
    }

    private void AnIcon() {
        fab_set.hide();
        fab_save.hide();
        fab_share.hide();
        un_option.hide();
    }*/
}
}




