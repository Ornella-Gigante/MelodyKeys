package es.ifp.melodykeys;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PlayingActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private Button record1, record2, record3, record4, record5, record6;

    // boolean variables

    private  boolean isplaying1;;
    private  boolean isplaying2;
    private  boolean isplaying3;
    private  boolean isplaying4;
    private  boolean isplaying5;
    private  boolean isplaying6;

    private boolean isplaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_playing);

        record1 = (Button)findViewById(R.id.button_playing_1);
        record2 = (Button)findViewById(R.id.button_playing_2);
        record3 = (Button)findViewById(R.id.button_playing_3);
        record4 = (Button)findViewById(R.id.button_playing_4);
        record5 = (Button)findViewById(R.id.button_playing_5);
        record6 = (Button)findViewById(R.id.button_playing_6);

        record1.setBackgroundResource(R.drawable.playsongshape);
        record2.setBackgroundResource(R.drawable.playsongshape);
        record3.setBackgroundResource(R.drawable.playsongshape);
        record4.setBackgroundResource(R.drawable.playsongshape);
        record5.setBackgroundResource(R.drawable.playsongshape);
        record6.setBackgroundResource(R.drawable.playsongshape);

        isplaying = false;
        isplaying1 = false;
        isplaying2 =false;
        isplaying3 = false;
        isplaying4 = false;
        isplaying5 = false;
        isplaying6 = false;

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void play1(View view){

    }

    public void play2(View view) {
    }

    public void play3(View view) {
    }

    public void play4(View view) {
    }

    public void play5(View view) {
    }


    public void play6(View view) {
    }
}