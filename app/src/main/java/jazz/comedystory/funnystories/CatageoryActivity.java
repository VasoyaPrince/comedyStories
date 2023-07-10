package jazz.comedystory.funnystories;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class CatageoryActivity extends AppCompatActivity {
    private static final int ADS_POST_TIME = 10000;
    private static final int SDK_READY_TIME = 1000;
    public Context caramelContext = this;
    public Handler caramelHandler = new Handler();
    public String[] file_name = {"Akabar.txt", "betaal.txt", "tenaliraman.txt", "Singhasan.txt", "mullashort.txt", "shekchalli.txt", "comedy.txt", "dranth.txt", "panchatantra.txt", "buddha.txt"};
    private GridViewAdapter gridAdapter;
    private GridView gridView;
    Boolean isFirstRun;
    String[] sub_title = {"अकबर-बीरबल", "विक्रम - बेताल", "तेनालीराम", "सिंहासन बत्तीसी", "मुल्ला नसरुद्दीन किस्से", "शेख चिल्ली", "हास्य कहानियाँ", "द्रन्त कथाए", "पंचतन्त्र कहानियां", "महात्मा बुद्ध"};

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_catageory);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        this.gridView = (GridView) findViewById(R.id.gridView);
        this.gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        this.gridView.setAdapter(this.gridAdapter);
        this.isFirstRun = Boolean.valueOf(getSharedPreferences("PREFERENCE", 0).getBoolean("isFirstRun", true));
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Intent intent = new Intent(CatageoryActivity.this, MainListview.class);
                intent.putExtra("file_name", CatageoryActivity.this.file_name[i]);
                intent.putExtra("title", CatageoryActivity.this.sub_title[i]);
                CatageoryActivity.this.startActivity(intent);

            }
        });
    }

    private ArrayList<ImageItem> getData() {
        ArrayList<ImageItem> arrayList = new ArrayList<>();
        TypedArray obtainTypedArray = getResources().obtainTypedArray(R.array.grid_image);
        for (int i = 0; i < obtainTypedArray.length(); i++) {
            arrayList.add(new ImageItem(BitmapFactory.decodeResource(getResources(), obtainTypedArray.getResourceId(i, -1)), this.sub_title[i]));
        }
        return arrayList;
    }
}
