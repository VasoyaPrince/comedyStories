package jazz.comedystory.funnystories;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.InputStream;

public class MainListview extends AppCompatActivity {
    private EfficientAdapter adapter;
    public String[] category = new String[1000];
    String file_name;
    int k = 0;
    private ListView list;
    public String[] mTitle = new String[1000];
    public String[] story = new String[1000];
    String sub_title;
    private String[] textFile;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main_listview);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        this.file_name = getIntent().getExtras().getString("file_name");
        this.sub_title = getIntent().getExtras().getString("title");
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(sub_title);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        list = (ListView) findViewById(R.id.listView);
        setTextDisplay();
        this.adapter = new EfficientAdapter(this);
        this.list.setAdapter(this.adapter);
        this.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Intent intent = new Intent(MainListview.this, StoryActivity.class);
                intent.putExtra("story", MainListview.this.story[i]);
                intent.putExtra("title", MainListview.this.mTitle[i]);
                MainListview.this.startActivity(intent);
            }
        });
    }

    public class EfficientAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public long getItemId(int i) {
            return (long) i;
        }

        public EfficientAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return MainListview.this.mTitle.length;
        }

        public Object getItem(int i) {
            return Integer.valueOf(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                view = this.mInflater.inflate(R.layout.list_item, (ViewGroup) null);
                viewHolder = new ViewHolder();
                viewHolder.title = (TextView) view.findViewById(R.id.title);
                viewHolder.name = (TextView) view.findViewById(R.id.subtitle);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.title.setText(MainListview.this.mTitle[i]);
            viewHolder.name.setText(MainListview.this.category[i]);
            return view;
        }

        class ViewHolder {
            TextView date;
            TextView name;
            TextView title;

            ViewHolder() {
            }
        }
    }

    private void setTextDisplay() {
        try {
            InputStream open = getAssets().open(this.file_name);
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            this.textFile = new String(bArr).split("@xyz@");
            int length = this.textFile.length / 3;
            this.mTitle = new String[length];
            this.category = new String[length];
            this.story = new String[length];
            while (true) {
                if (this.k % 3 == 0) {
                    this.mTitle[this.k / 3] = this.textFile[this.k];
                } else if (this.k % 3 == 1) {
                    this.category[this.k / 3] = this.textFile[this.k];
                } else if (this.k % 3 == 2) {
                    this.story[this.k / 3] = this.textFile[this.k];
                }
                this.k++;
            }
        } catch (Exception e) {
            System.out.println("excepton occur" + e);
        }
    }
}
