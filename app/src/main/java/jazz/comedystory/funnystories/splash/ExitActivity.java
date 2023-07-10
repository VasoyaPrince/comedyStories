package jazz.comedystory.funnystories.splash;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import jazz.comedystory.funnystories.R;

public class ExitActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivNo, ivYes;
    ImageView ivrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_last);

    }

    private void init() {
        ivYes = (ImageView) findViewById(R.id.ivYes);
        ivYes.setOnClickListener(this);
        ivNo = (ImageView) findViewById(R.id.ivNo);
        ivNo.setOnClickListener(this);
        ivrate = (ImageView) findViewById(R.id.ivrate);
        ivrate.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivYes:
                setResult(RESULT_OK);
                finish();
                break;

            case R.id.ivNo:
                finish();
                break;

            case R.id.ivrate:
                Uri uri1 = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri1);
                try {
                    startActivity(myAppLinkToMarket);
                } catch (ActivityNotFoundException e) {
                    //the device hasn't installed Google Play
                    Toast.makeText(ExitActivity.this, "You don't have Google Play installed", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {

    }

}
