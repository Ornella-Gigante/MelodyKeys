package es.ifp.melodykeys;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

import static es.ifp.melodykeys.MainActivity.mFilename1;
import static es.ifp.melodykeys.MainActivity.mFilename2;
import static es.ifp.melodykeys.MainActivity.mFilename3;
import static es.ifp.melodykeys.MainActivity.mFilename4;
import static es.ifp.melodykeys.MainActivity.mFilename5;
import static es.ifp.melodykeys.MainActivity.mFilename6;


public class PlayingActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private Button record1, record2, record3, record4, record5, record6;

    // boolean variables
    private boolean isplaying1;
    private boolean isplaying2;
    private boolean isplaying3;
    private boolean isplaying4;
    private boolean isplaying5;
    private boolean isplaying6;

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
        isplaying2 = false;
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
        if (!isplaying1 && !isplaying){
            record1.setBackgroundResource(R.drawable.playsongshapepressed);
            startplaying(1);
            isplaying = true;
            isplaying1 = true;
        } else if (!isplaying1 && isplaying){
            stopPlaying();

            record1.setBackgroundResource(R.drawable.playsongshape);
            record2.setBackgroundResource(R.drawable.playsongshape);
            record3.setBackgroundResource(R.drawable.playsongshape);
            record4.setBackgroundResource(R.drawable.playsongshape);
            record5.setBackgroundResource(R.drawable.playsongshape);
            record6.setBackgroundResource(R.drawable.playsongshape);

            isplaying1 = false;
            isplaying2 = false;
            isplaying3 = false;
            isplaying4 = false;
            isplaying5 = false;
            isplaying6 = false;

            record1.setBackgroundResource(R.drawable.playsongshapepressed);
            startplaying(1);
            isplaying1 = true;
            isplaying = true;
        } else {
            record1.setBackgroundResource(R.drawable.playsongshape);
            stopPlaying();
            isplaying1 = false;
            isplaying = false;
        }
    }

    public void play2(View view){
        if (!isplaying2 && !isplaying){
            record2.setBackgroundResource(R.drawable.playsongshapepressed);
            startplaying(2);
            isplaying = true;
            isplaying2 = true;
        } else if (!isplaying2 && isplaying){
            stopPlaying();

            record1.setBackgroundResource(R.drawable.playsongshape);
            record2.setBackgroundResource(R.drawable.playsongshape);
            record3.setBackgroundResource(R.drawable.playsongshape);
            record4.setBackgroundResource(R.drawable.playsongshape);
            record5.setBackgroundResource(R.drawable.playsongshape);
            record6.setBackgroundResource(R.drawable.playsongshape);

            isplaying1 = false;
            isplaying2 = false;
            isplaying3 = false;
            isplaying4 = false;
            isplaying5 = false;
            isplaying6 = false;

            record2.setBackgroundResource(R.drawable.playsongshapepressed);
            startplaying(2);
            isplaying2 = true;
            isplaying = true;
        } else {
            record2.setBackgroundResource(R.drawable.playsongshape);
            stopPlaying();
            isplaying2 = false;
            isplaying = false;
        }
    }

    public void play3(View view){
        if (!isplaying3 && !isplaying){
            record3.setBackgroundResource(R.drawable.playsongshapepressed);
            startplaying(3);
            isplaying = true;
            isplaying3 = true;
        } else if (!isplaying3 && isplaying){
            stopPlaying();

            record1.setBackgroundResource(R.drawable.playsongshape);
            record2.setBackgroundResource(R.drawable.playsongshape);
            record3.setBackgroundResource(R.drawable.playsongshape);
            record4.setBackgroundResource(R.drawable.playsongshape);
            record5.setBackgroundResource(R.drawable.playsongshape);
            record6.setBackgroundResource(R.drawable.playsongshape);

            isplaying1 = false;
            isplaying2 = false;
            isplaying3 = false;
            isplaying4 = false;
            isplaying5 = false;
            isplaying6 = false;

            record3.setBackgroundResource(R.drawable.playsongshapepressed);
            startplaying(3);
            isplaying3 = true;
            isplaying = true;
        } else {
            record3.setBackgroundResource(R.drawable.playsongshape);
            stopPlaying();
            isplaying3 = false;
            isplaying = false;
        }
    }

    public void play4(View view){
        if (!isplaying4 && !isplaying){
            record4.setBackgroundResource(R.drawable.playsongshapepressed);
            startplaying(4);
            isplaying = true;
            isplaying4 = true;
        } else if (!isplaying4 && isplaying){
            stopPlaying();

            record1.setBackgroundResource(R.drawable.playsongshape);
            record2.setBackgroundResource(R.drawable.playsongshape);
            record3.setBackgroundResource(R.drawable.playsongshape);
            record4.setBackgroundResource(R.drawable.playsongshape);
            record5.setBackgroundResource(R.drawable.playsongshape);
            record6.setBackgroundResource(R.drawable.playsongshape);

            isplaying1 = false;
            isplaying2 = false;
            isplaying3 = false;
            isplaying4 = false;
            isplaying5 = false;
            isplaying6 = false;

            record4.setBackgroundResource(R.drawable.playsongshapepressed);
            startplaying(4);
            isplaying4 = true;
            isplaying = true;
        } else {
            record4.setBackgroundResource(R.drawable.playsongshape);
            stopPlaying();
            isplaying4 = false;
            isplaying = false;
        }
    }

    public void play5(View view){
        if (!isplaying5 && !isplaying){
            record5.setBackgroundResource(R.drawable.playsongshapepressed);
            startplaying(5);
            isplaying = true;
            isplaying5 = true;
        } else if (!isplaying5 && isplaying){
            stopPlaying();

            record1.setBackgroundResource(R.drawable.playsongshape);
            record2.setBackgroundResource(R.drawable.playsongshape);
            record3.setBackgroundResource(R.drawable.playsongshape);
            record4.setBackgroundResource(R.drawable.playsongshape);
            record5.setBackgroundResource(R.drawable.playsongshape);
            record6.setBackgroundResource(R.drawable.playsongshape);

            isplaying1 = false;
            isplaying2 = false;
            isplaying3 = false;
            isplaying4 = false;
            isplaying5 = false;
            isplaying6 = false;

            record5.setBackgroundResource(R.drawable.playsongshapepressed);
            startplaying(5);
            isplaying5 = true;
            isplaying = true;
        } else {
            record5.setBackgroundResource(R.drawable.playsongshape);
            stopPlaying();
            isplaying5 = false;
            isplaying = false;
        }
    }

    public void play6(View view){
        if (!isplaying6 && !isplaying){
            record6.setBackgroundResource(R.drawable.playsongshapepressed);
            startplaying(6);
            isplaying = true;
            isplaying6 = true;
        } else if (!isplaying6 && isplaying){
            stopPlaying();

            record1.setBackgroundResource(R.drawable.playsongshape);
            record2.setBackgroundResource(R.drawable.playsongshape);
            record3.setBackgroundResource(R.drawable.playsongshape);
            record4.setBackgroundResource(R.drawable.playsongshape);
            record5.setBackgroundResource(R.drawable.playsongshape);
            record6.setBackgroundResource(R.drawable.playsongshape);

            isplaying1 = false;
            isplaying2 = false;
            isplaying3 = false;
            isplaying4 = false;
            isplaying5 = false;
            isplaying6 = false;

            record6.setBackgroundResource(R.drawable.playsongshapepressed);
            startplaying(6);
            isplaying6 = true;
            isplaying = true;
        } else {
            record6.setBackgroundResource(R.drawable.playsongshape);
            stopPlaying();
            isplaying6 = false;
            isplaying = false;
        }
    }

    private void startplaying(int recordingno){
        mediaPlayer = new MediaPlayer();

        try{
            switch (recordingno){
                case 1:
                    mediaPlayer.setDataSource(mFilename1);
                    break;
                case 2:
                    mediaPlayer.setDataSource(mFilename2);
                    break;
                case 3:
                    mediaPlayer.setDataSource(mFilename3);
                    break;
                case 4:
                    mediaPlayer.setDataSource(mFilename4);
                    break;
                case 5:
                    mediaPlayer.setDataSource(mFilename5);
                    break;
                case 6:
                    mediaPlayer.setDataSource(mFilename6);
                    break;
            }

            // Añadir listener para cuando termine la reproducción
            mediaPlayer.setOnCompletionListener(mp -> {
                resetPlaybackState();
            });

            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e){
            Log.e("failed", "Failed to play recording");
        }
    }

    private void resetPlaybackState() {
        record1.setBackgroundResource(R.drawable.playsongshape);
        record2.setBackgroundResource(R.drawable.playsongshape);
        record3.setBackgroundResource(R.drawable.playsongshape);
        record4.setBackgroundResource(R.drawable.playsongshape);
        record5.setBackgroundResource(R.drawable.playsongshape);
        record6.setBackgroundResource(R.drawable.playsongshape);

        isplaying = false;
        isplaying1 = false;
        isplaying2 = false;
        isplaying3 = false;
        isplaying4 = false;
        isplaying5 = false;
        isplaying6 = false;
    }

    public void stopPlaying(){
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
