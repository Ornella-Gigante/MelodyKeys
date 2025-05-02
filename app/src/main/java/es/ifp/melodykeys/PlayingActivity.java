package es.ifp.melodykeys;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static es.ifp.melodykeys.MainActivity.BASE_DIR;
import static es.ifp.melodykeys.MainActivity.mFilename1;
import static es.ifp.melodykeys.MainActivity.mFilename2;
import static es.ifp.melodykeys.MainActivity.mFilename3;
import static es.ifp.melodykeys.MainActivity.mFilename4;
import static es.ifp.melodykeys.MainActivity.mFilename5;
import static es.ifp.melodykeys.MainActivity.mFilename6;

public class PlayingActivity extends AppCompatActivity {

    private static final String TAG = "PlayingActivity";
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

        // Log para verificar rutas de archivos
        Log.d(TAG, "File paths: " +
                "\n1: " + mFilename1 +
                "\n2: " + mFilename2 +
                "\n3: " + mFilename3 +
                "\n4: " + mFilename4 +
                "\n5: " + mFilename5 +
                "\n6: " + mFilename6);

        record1 = findViewById(R.id.button_playing_1);
        record2 = findViewById(R.id.button_playing_2);
        record3 = findViewById(R.id.button_playing_3);
        record4 = findViewById(R.id.button_playing_4);
        record5 = findViewById(R.id.button_playing_5);
        record6 = findViewById(R.id.button_playing_6);

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

        // Verificar existencia de archivos al inicio
        checkAllFiles();
    }

    private void checkAllFiles() {
        // Usar rutas directas con BASE_DIR
        File file1 = new File(mFilename1);
        File file2 = new File(mFilename2);
        File file3 = new File(mFilename3);
        File file4 = new File(mFilename4);
        File file5 = new File(mFilename5);
        File file6 = new File(mFilename6);

        Log.d(TAG, "File existence check:" +
                "\nFile1 exists: " + file1.exists() + " size: " + file1.length() +
                "\nFile2 exists: " + file2.exists() + " size: " + file2.length() +
                "\nFile3 exists: " + file3.exists() + " size: " + file3.length() +
                "\nFile4 exists: " + file4.exists() + " size: " + file4.length() +
                "\nFile5 exists: " + file5.exists() + " size: " + file5.length() +
                "\nFile6 exists: " + file6.exists() + " size: " + file6.length());
    }

    private boolean checkFileExists(String filePath) {
        File file = new File(filePath);
        boolean exists = file.exists() && file.length() > 0;
        Log.d(TAG, "Checking file: " + filePath + ", exists: " + exists + ", size: " + file.length());

        if (!exists) {
            Toast.makeText(this, "No recording found", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void play1(View view){
        if (!isplaying1 && !isplaying){
            if (checkFileExists(mFilename1)) {
                record1.setBackgroundResource(R.drawable.playsongshapepressed);
                startplaying(1);
                isplaying = true;
                isplaying1 = true;
            }
        } else if (isplaying1 && isplaying){
            record1.setBackgroundResource(R.drawable.playsongshape);
            stopPlaying();
            isplaying1 = false;
            isplaying = false;
        } else if (!isplaying1 && isplaying) {
            stopPlaying();
            resetAllButtons();
            if (checkFileExists(mFilename1)) {
                record1.setBackgroundResource(R.drawable.playsongshapepressed);
                startplaying(1);
                isplaying1 = true;
                isplaying = true;
            }
        }
    }

    public void play2(View view){
        if (!isplaying2 && !isplaying){
            if (checkFileExists(mFilename2)) {
                record2.setBackgroundResource(R.drawable.playsongshapepressed);
                startplaying(2);
                isplaying = true;
                isplaying2 = true;
            }
        } else if (isplaying2 && isplaying){
            record2.setBackgroundResource(R.drawable.playsongshape);
            stopPlaying();
            isplaying2 = false;
            isplaying = false;
        } else if (!isplaying2 && isplaying) {
            stopPlaying();
            resetAllButtons();
            if (checkFileExists(mFilename2)) {
                record2.setBackgroundResource(R.drawable.playsongshapepressed);
                startplaying(2);
                isplaying2 = true;
                isplaying = true;
            }
        }
    }

    public void play3(View view){
        if (!isplaying3 && !isplaying){
            if (checkFileExists(mFilename3)) {
                record3.setBackgroundResource(R.drawable.playsongshapepressed);
                startplaying(3);
                isplaying = true;
                isplaying3 = true;
            }
        } else if (isplaying3 && isplaying){
            record3.setBackgroundResource(R.drawable.playsongshape);
            stopPlaying();
            isplaying3 = false;
            isplaying = false;
        } else if (!isplaying3 && isplaying) {
            stopPlaying();
            resetAllButtons();
            if (checkFileExists(mFilename3)) {
                record3.setBackgroundResource(R.drawable.playsongshapepressed);
                startplaying(3);
                isplaying3 = true;
                isplaying = true;
            }
        }
    }

    public void play4(View view){
        if (!isplaying4 && !isplaying){
            if (checkFileExists(mFilename4)) {
                record4.setBackgroundResource(R.drawable.playsongshapepressed);
                startplaying(4);
                isplaying = true;
                isplaying4 = true;
            }
        } else if (isplaying4 && isplaying){
            record4.setBackgroundResource(R.drawable.playsongshape);
            stopPlaying();
            isplaying4 = false;
            isplaying = false;
        } else if (!isplaying4 && isplaying) {
            stopPlaying();
            resetAllButtons();
            if (checkFileExists(mFilename4)) {
                record4.setBackgroundResource(R.drawable.playsongshapepressed);
                startplaying(4);
                isplaying4 = true;
                isplaying = true;
            }
        }
    }

    public void play5(View view){
        if (!isplaying5 && !isplaying){
            if (checkFileExists(mFilename5)) {
                record5.setBackgroundResource(R.drawable.playsongshapepressed);
                startplaying(5);
                isplaying = true;
                isplaying5 = true;
            }
        } else if (isplaying5 && isplaying){
            record5.setBackgroundResource(R.drawable.playsongshape);
            stopPlaying();
            isplaying5 = false;
            isplaying = false;
        } else if (!isplaying5 && isplaying) {
            stopPlaying();
            resetAllButtons();
            if (checkFileExists(mFilename5)) {
                record5.setBackgroundResource(R.drawable.playsongshapepressed);
                startplaying(5);
                isplaying5 = true;
                isplaying = true;
            }
        }
    }

    public void play6(View view){
        if (!isplaying6 && !isplaying){
            if (checkFileExists(mFilename6)) {
                record6.setBackgroundResource(R.drawable.playsongshapepressed);
                startplaying(6);
                isplaying = true;
                isplaying6 = true;
            }
        } else if (isplaying6 && isplaying){
            record6.setBackgroundResource(R.drawable.playsongshape);
            stopPlaying();
            isplaying6 = false;
            isplaying = false;
        } else if (!isplaying6 && isplaying) {
            stopPlaying();
            resetAllButtons();
            if (checkFileExists(mFilename6)) {
                record6.setBackgroundResource(R.drawable.playsongshapepressed);
                startplaying(6);
                isplaying6 = true;
                isplaying = true;
            }
        }
    }

    private void resetAllButtons() {
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
    }

    private void startplaying(int recordingno) {
        // Asegurar liberación de recursos previos
        if (mediaPlayer != null) {
            stopPlaying();
        }

        mediaPlayer = new MediaPlayer();

        // Establecer volumen máximo
        mediaPlayer.setVolume(1.0f, 1.0f);

        // Añadir listener de error
        mediaPlayer.setOnErrorListener((mp, what, extra) -> {
            Log.e(TAG, "MediaPlayer Error: " + what + ", " + extra);
            Toast.makeText(this, "Error playing recording", Toast.LENGTH_SHORT).show();
            resetAllButtons();
            isplaying = false;
            return true;
        });

        String fileToPlay = null;

        try {
            // Determinar qué archivo reproducir
            switch (recordingno) {
                case 1: fileToPlay = mFilename1; break;
                case 2: fileToPlay = mFilename2; break;
                case 3: fileToPlay = mFilename3; break;
                case 4: fileToPlay = mFilename4; break;
                case 5: fileToPlay = mFilename5; break;
                case 6: fileToPlay = mFilename6; break;
            }

            Log.d(TAG, "Starting playback for recording #" + recordingno);
            Log.d(TAG, "Playing file: " + fileToPlay + " size: " + new File(fileToPlay).length());

            // Usar FileInputStream para mejor compatibilidad
            FileInputStream fis = new FileInputStream(new File(fileToPlay));
            mediaPlayer.setDataSource(fis.getFD());
            fis.close();

            // Añadir listener para cuando termine la reproducción
            mediaPlayer.setOnCompletionListener(mp -> {
                Log.d(TAG, "Playback completed");
                resetAllButtons();
                isplaying = false;
            });

            // Usar prepare() síncrono para archivos locales
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            Log.e(TAG, "Error preparing MediaPlayer: " + e.getMessage(), e);
            Toast.makeText(this, "Failed to play recording: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            resetAllButtons();
            isplaying = false;
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error: " + e.getMessage(), e);
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            resetAllButtons();
            isplaying = false;
        }
    }

    private void stopPlaying() {
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    Log.d(TAG, "Stopping playback");
                    mediaPlayer.stop();
                }
                Log.d(TAG, "Resetting MediaPlayer");
                mediaPlayer.reset();
                Log.d(TAG, "Releasing MediaPlayer");
                mediaPlayer.release();
            } catch (IllegalStateException e) {
                Log.e(TAG, "Error stopping MediaPlayer: " + e.getMessage(), e);
            } catch (Exception e) {
                Log.e(TAG, "Unexpected error in stopPlaying: " + e.getMessage(), e);
            }

            mediaPlayer = null;
            Log.d(TAG, "MediaPlayer set to null");
        }
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause called");
        super.onPause();
        stopPlaying();
        resetAllButtons();
        isplaying = false;
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop called");
        super.onStop();
        stopPlaying();
        resetAllButtons();
        isplaying = false;
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy called");
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
