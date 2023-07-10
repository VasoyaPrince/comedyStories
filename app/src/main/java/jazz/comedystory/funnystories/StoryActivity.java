package jazz.comedystory.funnystories;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Locale;

public class StoryActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private String storyNew;
    private String storyTitleNew;
    private TextView story, tv_title;
    private TextToSpeech tts;
    private ImageView iv_back, iv_sound;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.series);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        PreferenceManager.getDefaultSharedPreferences(this);
        storyTitleNew = getIntent().getStringExtra("title");
        storyNew = getIntent().getStringExtra("story");
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_sound = (ImageView) findViewById(R.id.iv_sound);
        tv_title = (TextView) findViewById(R.id.tv_title);
        story = (TextView) findViewById(R.id.story);
        story.setText(storyNew.trim());
        tv_title.setText(storyTitleNew);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tts = new TextToSpeech(this, this);
        iv_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut();
            }
        });
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(new Locale("hi", "IN"));
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                iv_sound.setEnabled(true);
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    private void speakOut() {
        String text1 = storyTitleNew;
        String text = story.getText().toString();
        if (!tts.isSpeaking()) {
            tts.speak(text1, TextToSpeech.QUEUE_ADD, null);
            tts.speak(text, TextToSpeech.QUEUE_ADD, null);
            iv_sound.setImageResource(R.drawable.ic_sound);
            Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show();
        } else if (tts != null) {
            iv_sound.setImageResource(R.drawable.ic_mute);
            Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show();
            tts.stop();
        }
    }
}
