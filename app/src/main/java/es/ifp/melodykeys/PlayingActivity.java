package es.ifp.melodykeys;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
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
    private AnimationDrawable animationDrawable;
    private ObjectAnimator shineAnimator;

    private Button record1, record2, record3, record4, record5, record6;

    // Boolean variables for tracking playback state
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

        // Log to verify file paths
        Log.d(TAG, "File paths: " +
                "\n1: " + mFilename1 +
                "\n2: " + mFilename2 +
                "\n3: " + mFilename3 +
                "\n4: " + mFilename4 +
                "\n5: " + mFilename5 +
                "\n6: " + mFilename6);

        // Initialize buttons
        record1 = findViewById(R.id.button_playing_1);
        record2 = findViewById(R.id.button_playing_2);
        record3 = findViewById(R.id.button_playing_3);
        record4 = findViewById(R.id.button_playing_4);
        record5 = findViewById(R.id.button_playing_5);
        record6 = findViewById(R.id.button_playing_6);

        // Set button backgrounds
        record1.setBackgroundResource(R.drawable.playsongshape);
        record2.setBackgroundResource(R.drawable.playsongshape);
        record3.setBackgroundResource(R.drawable.playsongshape);
        record4.setBackgroundResource(R.drawable.playsongshape);
        record5.setBackgroundResource(R.drawable.playsongshape);
        record6.setBackgroundResource(R.drawable.playsongshape);

        // Initialize playback state
        isplaying = false;
        isplaying1 = false;
        isplaying2 = false;
        isplaying3 = false;
        isplaying4 = false;
        isplaying5 = false;
        isplaying6 = false;

        // Initialize animated background if it exists
        View backgroundView = findViewById(R.id.animated_background);
        if (backgroundView != null) {
            animationDrawable = (AnimationDrawable) backgroundView.getBackground();
            animationDrawable.setEnterFadeDuration(2000);
            animationDrawable.setExitFadeDuration(4000);
            animationDrawable.start();
        }

        // Initialize shine effect if it exists
        View shineView = findViewById(R.id.shine_effect);
        if (shineView != null) {
            shineAnimator = ObjectAnimator.ofFloat(shineView, "translationX",
                    -200f, getResources().getDisplayMetrics().widthPixels + 200f);
            shineAnimator.setDuration(3000);
            shineAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            shineAnimator.setRepeatCount(ValueAnimator.INFINITE);
            shineAnimator.start();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Update button states based on file existence
        updateButtonStates();
    }

    /**
     * Updates the visual state of buttons based on file availability
     */
    private void updateButtonStates() {
        // Verify each file and update button state accordingly
        updateButtonState(record1, mFilename1, 1);
        updateButtonState(record2, mFilename2, 2);
        updateButtonState(record3, mFilename3, 3);
        updateButtonState(record4, mFilename4, 4);
        updateButtonState(record5, mFilename5, 5);
        updateButtonState(record6, mFilename6, 6);

        // Highlight the last recorded button
        SharedPreferences prefs = getSharedPreferences("RECORDING_DATA", MODE_PRIVATE);
        int lastRecorded = prefs.getInt("LAST_RECORDED", -1);

        if (lastRecorded > 0 && lastRecorded <= 6) {
            Button lastButton = null;
            switch (lastRecorded) {
                case 1: lastButton = record1; break;
                case 2: lastButton = record2; break;
                case 3: lastButton = record3; break;
                case 4: lastButton = record4; break;
                case 5: lastButton = record5; break;
                case 6: lastButton = record6; break;
            }

            if (lastButton != null) {
                // Optionally highlight the last recorded button
                // lastButton.setBackgroundResource(R.drawable.last_recorded_button);
                Log.d(TAG, "Last recorded button: " + lastRecorded);
            }
        }

        Log.d(TAG, "Button states updated based on file existence");
    }

    /**
     * Updates a single button's state based on file existence
     */
    private void updateButtonState(Button button, String filename, int recordNumber) {
        File file = new File(filename);
        boolean exists = file.exists() && file.length() > 0;

        button.setEnabled(exists);
        button.setAlpha(exists ? 1.0f : 0.5f);

        if (exists) {
            button.setText("PLAY SONG " + recordNumber);
        } else {
            button.setText("NO SONG " + recordNumber);
        }
    }

    /**
     * Checks all recording files and logs their status
     */
    private void checkAllFiles() {
        // Use direct paths with BASE_DIR
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

    /**
     * Checks if a specific file exists and has content
     */
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

    /**
     * Resets all buttons to their default state
     */
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

    /**
     * Starts playing the selected recording
     */
    private void startplaying(int recordingno) {
        // Ensure previous resources are released
        if (mediaPlayer != null) {
            stopPlaying();
        }

        mediaPlayer = new MediaPlayer();

        // Set maximum volume
        mediaPlayer.setVolume(1.0f, 1.0f);

        // Add error listener
        mediaPlayer.setOnErrorListener((mp, what, extra) -> {
            Log.e(TAG, "MediaPlayer Error: " + what + ", " + extra);
            Toast.makeText(this, "Error playing recording", Toast.LENGTH_SHORT).show();
            resetAllButtons();
            isplaying = false;
            return true;
        });

        String fileToPlay = null;

        try {
            // Determine which file to play
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

            // Use FileInputStream for better compatibility
            FileInputStream fis = new FileInputStream(new File(fileToPlay));
            mediaPlayer.setDataSource(fis.getFD());
            fis.close();

            // Add listener for when playback completes
            mediaPlayer.setOnCompletionListener(mp -> {
                Log.d(TAG, "Playback completed");
                resetAllButtons();
                isplaying = false;
            });

            // Use synchronous prepare() for local files
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

    /**
     * Stops playback and releases resources
     */
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
    protected void onResume() {
        super.onResume();

        // Update button states when returning to this activity
        updateButtonStates();

        // Resume animations if they exist
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            animationDrawable.start();
        }

        if (shineAnimator != null && shineAnimator.isPaused()) {
            shineAnimator.resume();
        }
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause called");
        super.onPause();
        stopPlaying();
        resetAllButtons();
        isplaying = false;

        // Pause animations
        if (shineAnimator != null) {
            shineAnimator.pause();
        }
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop called");
        super.onStop();
        stopPlaying();
        resetAllButtons();
        isplaying = false;

        // Ensure complete release of MediaPlayer
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy called");
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        // Clean up animations
        if (shineAnimator != null) {
            shineAnimator.cancel();
            shineAnimator = null;
        }
    }
}