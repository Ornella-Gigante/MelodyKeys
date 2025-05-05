package es.ifp.melodykeys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final int PERMISSION_CODE = 100;

    // Base directory for recording files (internal)
    public static final String BASE_DIR = "/data/user/0/es.ifp.melodykeys/files/MelodyRecordings/";
    private HorizontalScrollView scrollView;
    public int recordingno = 0;

    // UI variables
    private Button recordButton;
    private Button left_navigation, right_navigation, playButton;
    private AnimationDrawable animationDrawable;
    private ObjectAnimator shineAnimator;

    // Recording variables
    private MediaRecorder mediaRecorder;
    public static String mFilename1 = null;
    public static String mFilename2 = null;
    public static String mFilename3 = null;
    public static String mFilename4 = null;
    public static String mFilename5 = null;
    public static String mFilename6 = null;

    private SoundPool soundPool;
    boolean mStartRecording = true;

    // Use ActivityResultContracts for Android 13+
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission granted, start recording
                    onRecord(true);
                    if (recordButton != null) {
                        recordButton.setText("Finish");
                    }
                    mStartRecording = false;
                } else {
                    Toast.makeText(this, "Recording permission required", Toast.LENGTH_SHORT).show();
                }
            });

    // White key sounds
    private int c3, d3, e3, f3, g3, a3, b3;
    private int c4, d4, e4, f4, g4, a4, b4;
    private int c5, d5, e5, f5, g5, a5, b5;
    private int c6, d6, e6, f6, g6, a6, b6;
    private int c7, d7, e7, f7, g7, a7, b7;

    // Black key sounds
    private int c3black, d3black, f3black, g3black, a3black;
    private int c4black, d4black, f4black, g4black, a4black;
    private int c5black, d5black, f5black, g5black, a5black;
    private int c6black, d6black, f6black, g6black, a6black;
    private int c7black, d7black, f7black, g7black, a7black;

    // Piano keys
    private Button keyC3, keyD3, keyE3, keyF3, keyG3, keyA3, keyB3;
    private Button keyC4, keyD4, keyE4, keyF4, keyG4, keyA4, keyB4;
    private Button keyC5, keyD5, keyE5, keyF5, keyG5, keyA5, keyB5;
    private Button keyC6, keyD6, keyE6, keyF6, keyG6, keyA6, keyB6;
    private Button keyC7, keyD7, keyE7, keyF7, keyG7, keyA7, keyB7;

    // Black keys
    private Button keyCs3, keyDs3, keyFs3, keyGs3, keyAs3;
    private Button keyCs4, keyDs4, keyFs4, keyGs4, keyAs4;
    private Button keyCs5, keyDs5, keyFs5, keyGs5, keyAs5;
    private Button keyCs6, keyDs6, keyFs6, keyGs6, keyAs6;
    private Button keyCs7, keyDs7, keyFs7, keyGs7, keyAs7;

    // TextViews (labels)
    private TextView labelC3, labelD3, labelE3, labelF3, labelG3, labelA3, labelB3;
    private TextView labelC4, labelD4, labelE4, labelF4, labelG4, labelA4, labelB4;
    private TextView labelC5, labelD5, labelE5, labelF5, labelG5, labelA5, labelB5;
    private TextView labelC6, labelD6, labelE6, labelF6, labelG6, labelA6, labelB6;
    private TextView labelC7, labelD7, labelE7, labelF7, labelG7, labelA7, labelB7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Init ScrollView
        scrollView = findViewById(R.id.scrollView);

        // Setup file paths and ensure directories exist
        setupRecordingPaths();

        // All keys init
        initializeAllPianoKeys();

        // TextViews init
        initializeTextViewLabelsOnPianoKeys();

        // Init SoundPool
        initializeSoundPool();

        // Init buttons
        left_navigation = findViewById(R.id.bt_left_navigation);
        right_navigation = findViewById(R.id.bt_right_navigation);
        recordButton = findViewById(R.id.bt_record);
        playButton = findViewById(R.id.bt_play_recording);

        // Ensure MediaRecorder is clean at startup
        if (mediaRecorder != null) {
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
        }

        // Reset recording state
        mStartRecording = true;
        if (recordButton != null) {
            recordButton.setText("Record");
        }

        // Initialize animated background
        View backgroundView = findViewById(R.id.animated_background);
        if (backgroundView != null) {
            animationDrawable = (AnimationDrawable) backgroundView.getBackground();
            animationDrawable.setEnterFadeDuration(2000);
            animationDrawable.setExitFadeDuration(4000);
            animationDrawable.start();
        }

        // Initialize shine effect
        View shineView = findViewById(R.id.shine_effect);
        if (shineView != null) {
            shineAnimator = ObjectAnimator.ofFloat(shineView, "translationX",
                    -200f, getResources().getDisplayMetrics().widthPixels + 200f);
            shineAnimator.setDuration(3000);
            shineAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            shineAnimator.setRepeatCount(ValueAnimator.INFINITE);
            shineAnimator.start();
        }

        // Listeners for navigation buttons
        left_navigation.setOnClickListener(v -> {
            scrollView.scrollTo(scrollView.getScrollX() - 30, scrollView.getScrollY());
        });

        right_navigation.setOnClickListener(v -> {
            scrollView.scrollTo(scrollView.getScrollX() + 30, scrollView.getScrollY());
        });

        // Get current recording number from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("FILENO", MODE_PRIVATE);
        recordingno = prefs.getInt("fileno", 1);

        // Validate recording number is in valid range
        if (recordingno < 1 || recordingno > 6) {
            recordingno = 1;
            prefs.edit().putInt("fileno", recordingno).apply();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Clears all previous recordings and resets the recording counter
     */
    private void clearOldRecordings() {
        File file1 = new File(mFilename1);
        File file2 = new File(mFilename2);
        File file3 = new File(mFilename3);
        File file4 = new File(mFilename4);
        File file5 = new File(mFilename5);
        File file6 = new File(mFilename6);

        // Delete existing files
        if (file1.exists()) file1.delete();
        if (file2.exists()) file2.delete();
        if (file3.exists()) file3.delete();
        if (file4.exists()) file4.delete();
        if (file5.exists()) file5.delete();
        if (file6.exists()) file6.delete();

        // Reset counter
        recordingno = 1;
        SharedPreferences.Editor editor = getSharedPreferences("FILENO", MODE_PRIVATE).edit();
        editor.putInt("fileno", recordingno);
        editor.apply();

        Log.d(TAG, "All previous recordings cleared");
    }

    private void initializeSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(6, android.media.AudioManager.STREAM_MUSIC, 0);
        }

        // Load sounds for all octaves
        loadSounds();
    }

    private void setupRecordingPaths() {
        // Create directory for recordings in internal storage
        File recordingsDir = new File(getFilesDir(), "MelodyRecordings");
        if (!recordingsDir.exists()) {
            boolean success = recordingsDir.mkdirs();
            Log.d(TAG, "Directory creation result: " + success);
        }

        // Use BASE_DIR constant for all paths
        mFilename1 = BASE_DIR + "audiorecordtest1.m4a";
        mFilename2 = BASE_DIR + "audiorecordtest2.m4a";
        mFilename3 = BASE_DIR + "audiorecordtest3.m4a";
        mFilename4 = BASE_DIR + "audiorecordtest4.m4a";
        mFilename5 = BASE_DIR + "audiorecordtest5.m4a";
        mFilename6 = BASE_DIR + "audiorecordtest6.m4a";

        Log.d(TAG, "Recording file paths initialized: " + mFilename1);
    }

    private void loadSounds() {
        if (soundPool == null) {
            Log.e(TAG, "Cannot load sounds, SoundPool is null");
            return;
        }

        try {
            // Octave 3
            c3 = soundPool.load(this, R.raw.c3, 1);
            c3black = soundPool.load(this, R.raw.c3black, 1);
            d3 = soundPool.load(this, R.raw.d3, 1);
            d3black = soundPool.load(this, R.raw.d3black, 1);
            e3 = soundPool.load(this, R.raw.e3, 1);
            f3 = soundPool.load(this, R.raw.f3, 1);
            f3black = soundPool.load(this, R.raw.f3black, 1);
            g3 = soundPool.load(this, R.raw.g3, 1);
            g3black = soundPool.load(this, R.raw.g3black, 1);
            a3 = soundPool.load(this, R.raw.a3, 1);
            a3black = soundPool.load(this, R.raw.a3black, 1);
            b3 = soundPool.load(this, R.raw.b3, 1);

            // Octave 4
            c4 = soundPool.load(this, R.raw.c4, 1);
            c4black = soundPool.load(this, R.raw.c4black, 1);
            d4 = soundPool.load(this, R.raw.d4, 1);
            d4black = soundPool.load(this, R.raw.d4black, 1);
            e4 = soundPool.load(this, R.raw.e4, 1);
            f4 = soundPool.load(this, R.raw.f4, 1);
            f4black = soundPool.load(this, R.raw.f4black, 1);
            g4 = soundPool.load(this, R.raw.g4, 1);
            g4black = soundPool.load(this, R.raw.g4black, 1);
            a4 = soundPool.load(this, R.raw.a4, 1);
            a4black = soundPool.load(this, R.raw.a4black, 1);
            b4 = soundPool.load(this, R.raw.b4, 1);

            // Octave 5
            c5 = soundPool.load(this, R.raw.c5, 1);
            c5black = soundPool.load(this, R.raw.c5black, 1);
            d5 = soundPool.load(this, R.raw.d5, 1);
            d5black = soundPool.load(this, R.raw.d5black, 1);
            e5 = soundPool.load(this, R.raw.e5, 1);
            f5 = soundPool.load(this, R.raw.f5, 1);
            f5black = soundPool.load(this, R.raw.f5black, 1);
            g5 = soundPool.load(this, R.raw.g5, 1);
            g5black = soundPool.load(this, R.raw.g5black, 1);
            a5 = soundPool.load(this, R.raw.a5, 1);
            a5black = soundPool.load(this, R.raw.a5black, 1);
            b5 = soundPool.load(this, R.raw.b5, 1);

            // Octave 6
            c6 = soundPool.load(this, R.raw.c6, 1);
            c6black = soundPool.load(this, R.raw.c6black, 1);
            d6 = soundPool.load(this, R.raw.d6, 1);
            d6black = soundPool.load(this, R.raw.d6black, 1);
            e6 = soundPool.load(this, R.raw.e6, 1);
            f6 = soundPool.load(this, R.raw.f6, 1);
            f6black = soundPool.load(this, R.raw.f6black, 1);
            g6 = soundPool.load(this, R.raw.g6, 1);
            g6black = soundPool.load(this, R.raw.g6black, 1);
            a6 = soundPool.load(this, R.raw.a6, 1);
            a6black = soundPool.load(this, R.raw.a6black, 1);
            b6 = soundPool.load(this, R.raw.b6, 1);

            // Octave 7
            c7 = soundPool.load(this, R.raw.c7, 1);
            c7black = soundPool.load(this, R.raw.c7black, 1);
            d7 = soundPool.load(this, R.raw.d7, 1);
            d7black = soundPool.load(this, R.raw.d7black, 1);
            e7 = soundPool.load(this, R.raw.e7, 1);
            f7 = soundPool.load(this, R.raw.f7, 1);
            f7black = soundPool.load(this, R.raw.f7black, 1);
            g7 = soundPool.load(this, R.raw.g7, 1);
            g7black = soundPool.load(this, R.raw.g7black, 1);
            a7 = soundPool.load(this, R.raw.a7, 1);
            a7black = soundPool.load(this, R.raw.a7black, 1);
            b7 = soundPool.load(this, R.raw.b7, 1);

            Log.d(TAG, "All sounds loaded successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error loading sounds: " + e.getMessage());
        }
    }

    private void initializeAllPianoKeys() {
        // White keys
        keyC3 = findViewById(R.id.keyC3); keyC3.setOnClickListener(this);
        keyD3 = findViewById(R.id.keyD3); keyD3.setOnClickListener(this);
        keyE3 = findViewById(R.id.keyE3); keyE3.setOnClickListener(this);
        keyF3 = findViewById(R.id.keyF3); keyF3.setOnClickListener(this);
        keyG3 = findViewById(R.id.keyG3); keyG3.setOnClickListener(this);
        keyA3 = findViewById(R.id.keyA3); keyA3.setOnClickListener(this);
        keyB3 = findViewById(R.id.keyB3); keyB3.setOnClickListener(this);

        keyC4 = findViewById(R.id.keyC4); keyC4.setOnClickListener(this);
        keyD4 = findViewById(R.id.keyD4); keyD4.setOnClickListener(this);
        keyE4 = findViewById(R.id.keyE4); keyE4.setOnClickListener(this);
        keyF4 = findViewById(R.id.keyF4); keyF4.setOnClickListener(this);
        keyG4 = findViewById(R.id.keyG4); keyG4.setOnClickListener(this);
        keyA4 = findViewById(R.id.keyA4); keyA4.setOnClickListener(this);
        keyB4 = findViewById(R.id.keyB4); keyB4.setOnClickListener(this);

        keyC5 = findViewById(R.id.keyC5); keyC5.setOnClickListener(this);
        keyD5 = findViewById(R.id.keyD5); keyD5.setOnClickListener(this);
        keyE5 = findViewById(R.id.keyE5); keyE5.setOnClickListener(this);
        keyF5 = findViewById(R.id.keyF5); keyF5.setOnClickListener(this);
        keyG5 = findViewById(R.id.keyG5); keyG5.setOnClickListener(this);
        keyA5 = findViewById(R.id.keyA5); keyA5.setOnClickListener(this);
        keyB5 = findViewById(R.id.keyB5); keyB5.setOnClickListener(this);

        keyC6 = findViewById(R.id.keyC6); keyC6.setOnClickListener(this);
        keyD6 = findViewById(R.id.keyD6); keyD6.setOnClickListener(this);
        keyE6 = findViewById(R.id.keyE6); keyE6.setOnClickListener(this);
        keyF6 = findViewById(R.id.keyF6); keyF6.setOnClickListener(this);
        keyG6 = findViewById(R.id.keyG6); keyG6.setOnClickListener(this);
        keyA6 = findViewById(R.id.keyA6); keyA6.setOnClickListener(this);
        keyB6 = findViewById(R.id.keyB6); keyB6.setOnClickListener(this);

        keyC7 = findViewById(R.id.keyC7); keyC7.setOnClickListener(this);
        keyD7 = findViewById(R.id.keyD7); keyD7.setOnClickListener(this);
        keyE7 = findViewById(R.id.keyE7); keyE7.setOnClickListener(this);
        keyF7 = findViewById(R.id.keyF7); keyF7.setOnClickListener(this);
        keyG7 = findViewById(R.id.keyG7); keyG7.setOnClickListener(this);
        keyA7 = findViewById(R.id.keyA7); keyA7.setOnClickListener(this);
        keyB7 = findViewById(R.id.keyB7); keyB7.setOnClickListener(this);

        // Black keys
        keyCs3 = findViewById(R.id.keyCs3); keyCs3.setOnClickListener(this);
        keyDs3 = findViewById(R.id.keyDs3); keyDs3.setOnClickListener(this);
        keyFs3 = findViewById(R.id.keyFs3); keyFs3.setOnClickListener(this);
        keyGs3 = findViewById(R.id.keyGs3); keyGs3.setOnClickListener(this);
        keyAs3 = findViewById(R.id.keyAs3); keyAs3.setOnClickListener(this);

        keyCs4 = findViewById(R.id.keyCs4); keyCs4.setOnClickListener(this);
        keyDs4 = findViewById(R.id.keyDs4); keyDs4.setOnClickListener(this);
        keyFs4 = findViewById(R.id.keyFs4); keyFs4.setOnClickListener(this);
        keyGs4 = findViewById(R.id.keyGs4); keyGs4.setOnClickListener(this);
        keyAs4 = findViewById(R.id.keyAs4); keyAs4.setOnClickListener(this);

        keyCs5 = findViewById(R.id.keyCs5); keyCs5.setOnClickListener(this);
        keyDs5 = findViewById(R.id.keyDs5); keyDs5.setOnClickListener(this);
        keyFs5 = findViewById(R.id.keyFs5); keyFs5.setOnClickListener(this);
        keyGs5 = findViewById(R.id.keyGs5); keyGs5.setOnClickListener(this);
        keyAs5 = findViewById(R.id.keyAs5); keyAs5.setOnClickListener(this);

        keyCs6 = findViewById(R.id.keyCs6); keyCs6.setOnClickListener(this);
        keyDs6 = findViewById(R.id.keyDs6); keyDs6.setOnClickListener(this);
        keyFs6 = findViewById(R.id.keyFs6); keyFs6.setOnClickListener(this);
        keyGs6 = findViewById(R.id.keyGs6); keyGs6.setOnClickListener(this);
        keyAs6 = findViewById(R.id.keyAs6); keyAs6.setOnClickListener(this);

        keyCs7 = findViewById(R.id.keyCs7); keyCs7.setOnClickListener(this);
        keyDs7 = findViewById(R.id.keyDs7); keyDs7.setOnClickListener(this);
        keyFs7 = findViewById(R.id.keyFs7); keyFs7.setOnClickListener(this);
        keyGs7 = findViewById(R.id.keyGs7); keyGs7.setOnClickListener(this);
        keyAs7 = findViewById(R.id.keyAs7); keyAs7.setOnClickListener(this);
    }

    private void initializeTextViewLabelsOnPianoKeys() {
        labelC3 = findViewById(R.id.labelC3); labelC3.setOnClickListener(this);
        labelD3 = findViewById(R.id.labelD3); labelD3.setOnClickListener(this);
        labelE3 = findViewById(R.id.labelE3); labelE3.setOnClickListener(this);
        labelF3 = findViewById(R.id.labelF3); labelF3.setOnClickListener(this);
        labelG3 = findViewById(R.id.labelG3); labelG3.setOnClickListener(this);
        labelA3 = findViewById(R.id.labelA3); labelA3.setOnClickListener(this);
        labelB3 = findViewById(R.id.labelB3); labelB3.setOnClickListener(this);

        labelC4 = findViewById(R.id.labelC4); labelC4.setOnClickListener(this);
        labelD4 = findViewById(R.id.labelD4); labelD4.setOnClickListener(this);
        labelE4 = findViewById(R.id.labelE4); labelE4.setOnClickListener(this);
        labelF4 = findViewById(R.id.labelF4); labelF4.setOnClickListener(this);
        labelG4 = findViewById(R.id.labelG4); labelG4.setOnClickListener(this);
        labelA4 = findViewById(R.id.labelA4); labelA4.setOnClickListener(this);
        labelB4 = findViewById(R.id.labelB4); labelB4.setOnClickListener(this);

        labelC5 = findViewById(R.id.labelC5); labelC5.setOnClickListener(this);
        labelD5 = findViewById(R.id.labelD5); labelD5.setOnClickListener(this);
        labelE5 = findViewById(R.id.labelE5); labelE5.setOnClickListener(this);
        labelF5 = findViewById(R.id.labelF5); labelF5.setOnClickListener(this);
        labelG5 = findViewById(R.id.labelG5); labelG5.setOnClickListener(this);
        labelA5 = findViewById(R.id.labelA5); labelA5.setOnClickListener(this);
        labelB5 = findViewById(R.id.labelB5); labelB5.setOnClickListener(this);

        labelC6 = findViewById(R.id.labelC6); labelC6.setOnClickListener(this);
        labelD6 = findViewById(R.id.labelD6); labelD6.setOnClickListener(this);
        labelE6 = findViewById(R.id.labelE6); labelE6.setOnClickListener(this);
        labelF6 = findViewById(R.id.labelF6); labelF6.setOnClickListener(this);
        labelG6 = findViewById(R.id.labelG6); labelG6.setOnClickListener(this);
        labelA6 = findViewById(R.id.labelA6); labelA6.setOnClickListener(this);
        labelB6 = findViewById(R.id.labelB6); labelB6.setOnClickListener(this);

        labelC7 = findViewById(R.id.labelC7); labelC7.setOnClickListener(this);
        labelD7 = findViewById(R.id.labelD7); labelD7.setOnClickListener(this);
        labelE7 = findViewById(R.id.labelE7); labelE7.setOnClickListener(this);
        labelF7 = findViewById(R.id.labelF7); labelF7.setOnClickListener(this);
        labelG7 = findViewById(R.id.labelG7); labelG7.setOnClickListener(this);
        labelA7 = findViewById(R.id.labelA7); labelA7.setOnClickListener(this);
        labelB7 = findViewById(R.id.labelB7); labelB7.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // Verify soundPool is not null before using it
        if (soundPool == null) {
            Log.e(TAG, "SoundPool is null in onClick");
            Toast.makeText(this, "Error: SoundPool not initialized", Toast.LENGTH_SHORT).show();
            // Try to reinitialize SoundPool
            initializeSoundPool();
            return;
        }

        int id = view.getId();

        try {
            // Octave 3
            if (id == R.id.keyC3 || id == R.id.labelC3) soundPool.play(c3, 1, 1, 0, 0, 1);
            else if (id == R.id.keyD3 || id == R.id.labelD3) soundPool.play(d3, 1, 1, 0, 0, 1);
            else if (id == R.id.keyE3 || id == R.id.labelE3) soundPool.play(e3, 1, 1, 0, 0, 1);
            else if (id == R.id.keyF3 || id == R.id.labelF3) soundPool.play(f3, 1, 1, 0, 0, 1);
            else if (id == R.id.keyG3 || id == R.id.labelG3) soundPool.play(g3, 1, 1, 0, 0, 1);
            else if (id == R.id.keyA3 || id == R.id.labelA3) soundPool.play(a3, 1, 1, 0, 0, 1);
            else if (id == R.id.keyB3 || id == R.id.labelB3) soundPool.play(b3, 1, 1, 0, 0, 1);
            else if (id == R.id.keyCs3) soundPool.play(c3black, 1, 1, 0, 0, 1);
            else if (id == R.id.keyDs3) soundPool.play(d3black, 1, 1, 0, 0, 1);
            else if (id == R.id.keyFs3) soundPool.play(f3black, 1, 1, 0, 0, 1);
            else if (id == R.id.keyGs3) soundPool.play(g3black, 1, 1, 0, 0, 1);
            else if (id == R.id.keyAs3) soundPool.play(a3black, 1, 1, 0, 0, 1);

                // Octave 4
            else if (id == R.id.keyC4 || id == R.id.labelC4) soundPool.play(c4, 1, 1, 0, 0, 1);
            else if (id == R.id.keyD4 || id == R.id.labelD4) soundPool.play(d4, 1, 1, 0, 0, 1);
            else if (id == R.id.keyE4 || id == R.id.labelE4) soundPool.play(e4, 1, 1, 0, 0, 1);
            else if (id == R.id.keyF4 || id == R.id.labelF4) soundPool.play(f4, 1, 1, 0, 0, 1);
            else if (id == R.id.keyG4 || id == R.id.labelG4) soundPool.play(g4, 1, 1, 0, 0, 1);
            else if (id == R.id.keyA4 || id == R.id.labelA4) soundPool.play(a4, 1, 1, 0, 0, 1);
            else if (id == R.id.keyB4 || id == R.id.labelB4) soundPool.play(b4, 1, 1, 0, 0, 1);
            else if (id == R.id.keyCs4) soundPool.play(c4black, 1, 1, 0, 0, 1);
            else if (id == R.id.keyDs4) soundPool.play(d4black, 1, 1, 0, 0, 1);
            else if (id == R.id.keyFs4) soundPool.play(f4black, 1, 1, 0, 0, 1);
            else if (id == R.id.keyGs4) soundPool.play(g4black, 1, 1, 0, 0, 1);
            else if (id == R.id.keyAs4) soundPool.play(a4black, 1, 1, 0, 0, 1);

                // Octave 5
            else if (id == R.id.keyC5 || id == R.id.labelC5) soundPool.play(c5, 1, 1, 0, 0, 1);
            else if (id == R.id.keyD5 || id == R.id.labelD5) soundPool.play(d5, 1, 1, 0, 0, 1);
            else if (id == R.id.keyE5 || id == R.id.labelE5) soundPool.play(e5, 1, 1, 0, 0, 1);
            else if (id == R.id.keyF5 || id == R.id.labelF5) soundPool.play(f5, 1, 1, 0, 0, 1);
            else if (id == R.id.keyG5 || id == R.id.labelG5) soundPool.play(g5, 1, 1, 0, 0, 1);
            else if (id == R.id.keyA5 || id == R.id.labelA5) soundPool.play(a5, 1, 1, 0, 0, 1);
            else if (id == R.id.keyB5 || id == R.id.labelB5) soundPool.play(b5, 1, 1, 0, 0, 1);
            else if (id == R.id.keyCs5) soundPool.play(c5black, 1, 1, 0, 0, 1);
            else if (id == R.id.keyDs5) soundPool.play(d5black, 1, 1, 0, 0, 1);
            else if (id == R.id.keyFs5) soundPool.play(f5black, 1, 1, 0, 0, 1);
            else if (id == R.id.keyGs5) soundPool.play(g5black, 1, 1, 0, 0, 1);
            else if (id == R.id.keyAs5) soundPool.play(a5black, 1, 1, 0, 0, 1);

                // Octave 6
            else if (id == R.id.keyC6 || id == R.id.labelC6) soundPool.play(c6, 1, 1, 0, 0, 1);
            else if (id == R.id.keyD6 || id == R.id.labelD6) soundPool.play(d6, 1, 1, 0, 0, 1);
            else if (id == R.id.keyE6 || id == R.id.labelE6) soundPool.play(e6, 1, 1, 0, 0, 1);
            else if (id == R.id.keyF6 || id == R.id.labelF6) soundPool.play(f6, 1, 1, 0, 0, 1);
            else if (id == R.id.keyG6 || id == R.id.labelG6) soundPool.play(g6, 1, 1, 0, 0, 1);
            else if (id == R.id.keyA6 || id == R.id.labelA6) soundPool.play(a6, 1, 1, 0, 0, 1);
            else if (id == R.id.keyB6 || id == R.id.labelB6) soundPool.play(b6, 1, 1, 0, 0, 1);
            else if (id == R.id.keyCs6) soundPool.play(c6black, 1, 1, 0, 0, 1);
            else if (id == R.id.keyDs6) soundPool.play(d6black, 1, 1, 0, 0, 1);
            else if (id == R.id.keyFs6) soundPool.play(f6black, 1, 1, 0, 0, 1);
            else if (id == R.id.keyGs6) soundPool.play(g6black, 1, 1, 0, 0, 1);
            else if (id == R.id.keyAs6) soundPool.play(a6black, 1, 1, 0, 0, 1);

                // Octave 7
            else if (id == R.id.keyC7 || id == R.id.labelC7) soundPool.play(c7, 1, 1, 0, 0, 1);
            else if (id == R.id.keyD7 || id == R.id.labelD7) soundPool.play(d7, 1, 1, 0, 0, 1);
            else if (id == R.id.keyE7 || id == R.id.labelE7) soundPool.play(e7, 1, 1, 0, 0, 1);
            else if (id == R.id.keyF7 || id == R.id.labelF7) soundPool.play(f7, 1, 1, 0, 0, 1);
            else if (id == R.id.keyG7 || id == R.id.labelG7) soundPool.play(g7, 1, 1, 0, 0, 1);
            else if (id == R.id.keyA7 || id == R.id.labelA7) soundPool.play(a7, 1, 1, 0, 0, 1);
            else if (id == R.id.keyB7 || id == R.id.labelB7) soundPool.play(b7, 1, 1, 0, 0, 1);
            else if (id == R.id.keyCs7) soundPool.play(c7black, 1, 1, 0, 0, 1);
            else if (id == R.id.keyDs7) soundPool.play(d7black, 1, 1, 0, 0, 1);
            else if (id == R.id.keyFs7) soundPool.play(f7black, 1, 1, 0, 0, 1);
            else if (id == R.id.keyGs7) soundPool.play(g7black, 1, 1, 0, 0, 1);
            else if (id == R.id.keyAs7) soundPool.play(a7black, 1, 1, 0, 0, 1);
        } catch (Exception e) {
            Log.e(TAG, "Error playing sound: " + e.getMessage());
        }
    }

    public void play(View view) {
        // Check if at least one recording is available
        boolean hasRecordings = false;

        // Check all files, not just the first one
        File file1 = new File(mFilename1);
        File file2 = new File(mFilename2);
        File file3 = new File(mFilename3);
        File file4 = new File(mFilename4);
        File file5 = new File(mFilename5);
        File file6 = new File(mFilename6);

        if ((file1.exists() && file1.length() > 0) ||
                (file2.exists() && file2.length() > 0) ||
                (file3.exists() && file3.length() > 0) ||
                (file4.exists() && file4.length() > 0) ||
                (file5.exists() && file5.length() > 0) ||
                (file6.exists() && file6.length() > 0)) {
            hasRecordings = true;
        }

        if (hasRecordings) {
            Intent intent = new Intent(MainActivity.this, PlayingActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "No recordings available yet", Toast.LENGTH_SHORT).show();
        }
    }

    public void record(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
            // Already has permissions, proceed with recording
            onRecord(mStartRecording);

            if (mStartRecording) {
                recordButton.setText("Finish");
            } else {
                recordButton.setText("Record");
            }

            mStartRecording = !mStartRecording;
        } else {
            // Request permissions and wait for response
            requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO);
        }
    }

    private void startRecording() {
        // Ensure directory exists
        File directory = new File(getFilesDir(), "MelodyRecordings");
        if (!directory.exists()) {
            boolean success = directory.mkdirs();
            Log.d(TAG, "Directory creation result: " + success);
        }

        try {
            if (mediaRecorder != null) {
                mediaRecorder.release();
                mediaRecorder = null;
            }

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mediaRecorder.setAudioSamplingRate(44100);
            mediaRecorder.setOutputFile(getCurrentFilename());

            mediaRecorder.prepare();
            mediaRecorder.start();

            Log.d(TAG, "Recording started to: " + getCurrentFilename());
        } catch (IOException e) {
            Log.e(TAG, "MediaRecorder prepare() failed: " + e.getMessage(), e);
            Toast.makeText(this, "Failed to start recording", Toast.LENGTH_SHORT).show();
            recordButton.setText("Record");
            mStartRecording = true;
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
                mediaRecorder.reset();
                mediaRecorder.release();

                // Verify file exists and has content
                File recordedFile = new File(getCurrentFilename());
                Log.d(TAG, "Recording saved: " + getCurrentFilename() + " Size: " + recordedFile.length() + " bytes");

                if (recordedFile.length() == 0) {
                    Log.w(TAG, "Warning: Recorded file is empty");
                }
            } catch (IllegalStateException e) {
                Log.e(TAG, "Error stopping recorder: " + e.getMessage());
                Toast.makeText(this, "Error saving recording", Toast.LENGTH_SHORT).show();
            }
            mediaRecorder = null;
        }
    }

    private String getCurrentFilename() {
        // Ensure recordingno is in valid range (1-6)
        if (recordingno < 1 || recordingno > 6) {
            recordingno = 1;
        }

        String currentFile;
        switch (recordingno) {
            case 1: currentFile = mFilename1; break;
            case 2: currentFile = mFilename2; break;
            case 3: currentFile = mFilename3; break;
            case 4: currentFile = mFilename4; break;
            case 5: currentFile = mFilename5; break;
            case 6: currentFile = mFilename6; break;
            default: currentFile = mFilename1; break;
        }

        // Increment for next recording with circular rotation
        recordingno = (recordingno % 6) + 1;

        // Save the new recording number
        SharedPreferences.Editor editor = getSharedPreferences("FILENO", MODE_PRIVATE).edit();
        editor.putInt("fileno", recordingno);
        editor.apply();

        return currentFile;
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();

            int songNumber;
            if (recordingno == 1) {
                songNumber = 6;
            } else {
                songNumber = recordingno - 1;
            }

            Toast.makeText(getApplicationContext(), "Song " + songNumber + " saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Reinitialize soundPool if null
        if (soundPool == null) {
            Log.d(TAG, "Reinitializing SoundPool in onResume");
            initializeSoundPool();
        }

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
        super.onPause();
        if (mediaRecorder != null && !mStartRecording) {
            stopRecording();
            mStartRecording = true;
            recordButton.setText("Record");
        }

        // Pause animations
        if (shineAnimator != null) {
            shineAnimator.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Better handling of soundPool
        if (soundPool != null) {
            try {
                soundPool.release();
            } catch (Exception e) {
                Log.e(TAG, "Error releasing SoundPool: " + e.getMessage());
            } finally {
                soundPool = null;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }

        // Clean up animations
        if (shineAnimator != null) {
            shineAnimator.cancel();
            shineAnimator = null;
        }
    }
}
