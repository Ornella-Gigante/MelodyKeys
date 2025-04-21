package es.ifp.melodykeys;

/**
 * This class defines the Test activity for the MelodyKeys application.
 *
 * It extends AppCompatActivity and sets up the layout defined in test.xml.
 * The activity enables edge-to-edge display using EdgeToEdge.enable(this) to allow the UI
 * to draw behind system bars, providing a more immersive experience on modern Android devices.
 *
 * It also applies window insets to the root view with ViewCompat.setOnApplyWindowInsetsListener,
 * ensuring that the content is padded correctly to avoid being overlapped by system bars
 * (such as the status bar and navigation bar), according to Android's edge-to-edge guidelines.
 *
 * This setup is standard for activities that want to provide a modern, full-screen user experience
 * while maintaining proper layout and usability across different devices and Android versions.
 */



import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Playingmp3 extends AppCompatActivity implements View.OnClickListener{

    Button button, button2, button3, button4;

    MediaPlayer mp;

    SoundPool soundpool;

    int c3, c3black;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.test);


        button =(Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);
        button3 =(Button) findViewById(R.id.button3);
        button4 =(Button) findViewById(R.id.button4);


        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        soundpool = new SoundPool.Builder().setMaxStreams(6).build();
        c3 = soundpool.load(this, R.raw.c3,1);
        c3black = soundpool.load(this,R.raw.c3black,1);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.button) {
            mp = MediaPlayer.create(getApplicationContext(), R.raw.c3);
            mp.start();

        } else if (id == R.id.button2) {

            mp= MediaPlayer.create(getApplicationContext(),R.raw.c3);
            mp.start();

        } else if (id == R.id.button3) {

            soundpool.play(c3,1,1,0,0,1);


        } else if (id == R.id.button4) {

            soundpool.play(c3black,1,1,0,0,1);
        }
    }

}
