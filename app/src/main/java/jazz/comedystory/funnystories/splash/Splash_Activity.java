package jazz.comedystory.funnystories.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import jazz.comedystory.funnystories.R;

public class Splash_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent i = new Intent(Splash_Activity.this, SplashStart_Activity.class);
                    i.putExtra("fromSplash", true);
                    startActivity(i);
                    finish();
                }
            }, 2000);

    }




    @Override
    public void onBackPressed() {

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
