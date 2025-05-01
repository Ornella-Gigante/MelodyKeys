package es.ifp.melodykeys;

import android.media.MediaPlayer;
import android.os.Bundle;
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


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}