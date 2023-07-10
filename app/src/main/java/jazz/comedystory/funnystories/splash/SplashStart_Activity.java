package jazz.comedystory.funnystories.splash;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import jazz.comedystory.funnystories.BuildConfig;
import jazz.comedystory.funnystories.CatageoryActivity;
import jazz.comedystory.funnystories.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplashStart_Activity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQ_CODE_GALLERY_CAMERA = 111;
    private Dialog dialog;
    private Uri urishare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashstart);

        if (Build.VERSION.SDK_INT >= 23) {
            checkMultiplePermissions();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_rate:
                Uri uri1 = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri1);
                try {
                    startActivity(myAppLinkToMarket);
                } catch (ActivityNotFoundException e) {
                    //the device hasn't installed Google Play
                    Toast.makeText(SplashStart_Activity.this, "You don't have Google Play installed", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btn_start:
                startActivity(new Intent(SplashStart_Activity.this, CatageoryActivity.class));
                break;


            case R.id.btn_share:
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                File f = new File(getExternalCacheDir() + "/image.png");
                try {
                    FileOutputStream outStream = new FileOutputStream(f);
                    bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                    outStream.flush();
                    outStream.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getPackageName());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    urishare = FileProvider.getUriForFile(SplashStart_Activity.this, BuildConfig.APPLICATION_ID +
                            ".provider", f);
                } else {
                    urishare = Uri.fromFile(f);
                }

                shareIntent.putExtra(Intent.EXTRA_STREAM, urishare);
                startActivity(Intent.createChooser(shareIntent, "Share Image using"));
                break;

        }
    }

    private void checkMultiplePermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissionsNeeded = new ArrayList();
            List<String> permissionsList = new ArrayList();
            if (!addPermission(permissionsList, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                permissionsNeeded.add("Write Storage");
            }
            if (!addPermission(permissionsList, "android.permission.READ_EXTERNAL_STORAGE")) {
                permissionsNeeded.add("Read Storage");
            }
            if (permissionsList.size() > 0) {
                requestPermissions((String[]) permissionsList.toArray(new String[permissionsList.size()]), REQ_CODE_GALLERY_CAMERA);
                return;
            }
        }
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            if (!shouldShowRequestPermissionRationale(permission)) {
                return false;
            }
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQ_CODE_GALLERY_CAMERA:
                Map<String, Integer> perms = new HashMap();
                perms.put("android.permission.WRITE_EXTERNAL_STORAGE", Integer.valueOf(0));
                perms.put("android.permission.READ_EXTERNAL_STORAGE", Integer.valueOf(0));
                for (int i = 0; i < permissions.length; i++) {
                    perms.put(permissions[i], Integer.valueOf(grantResults[i]));
                }

                if (((Integer) perms.get("android.permission.READ_EXTERNAL_STORAGE")).intValue() == 0
                        && ((Integer) perms.get("android.permission.WRITE_EXTERNAL_STORAGE")).intValue() == 0) {
                    break;
                } else if (Build.VERSION.SDK_INT >= 23) {
                    Toast.makeText(getApplicationContext(), "My App cannot run without Storage Permissions.\nRelaunch My App or allow permissions in Applications Settings", Toast.LENGTH_LONG).show();
                    finish();
                    break;
                } else {
                    break;
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                return;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        startActivityForResult(new Intent(this, ExitActivity.class), 1010);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1010) {
            finish();
        }
    }







    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
