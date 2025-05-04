package es.ifp.melodykeys;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Splash extends AppCompatActivity {

    // Variables for SplashActivity
    private ImageView imageViewS;
    private TextView textViewS;
    private Button btnStart;
    private MediaPlayer mediaPlayer;
    private AnimationDrawable animationDrawable;
    private ObjectAnimator shineAnimator;

    // Variables for permissions
    private SharedPreferences permissionStatus;
    private String[] requiredPermissions;

    private final ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
                if (areAllPermissionsGranted()) {
                    startMainActivity();
                } else {
                    handleDeniedPermissions();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        // Initialize splash activity components on screen
        imageViewS = findViewById(R.id.imageViewSplash);
        textViewS = findViewById(R.id.textViewSpash);
        btnStart = findViewById(R.id.btnStart);

        // Initialize requiredPermissions
        requiredPermissions = getRequiredPermissions();
        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);

        // Set up animated background
        View backgroundView = findViewById(R.id.animated_background);
        animationDrawable = (AnimationDrawable) backgroundView.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        // Set up shine effect animation
        View shineView = findViewById(R.id.shine_effect);
        shineAnimator = ObjectAnimator.ofFloat(shineView, "translationX",
                -200f, getResources().getDisplayMetrics().widthPixels + 200f);
        shineAnimator.setDuration(3000);
        shineAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        shineAnimator.setRepeatCount(ValueAnimator.INFINITE);
        shineAnimator.start();

        // Start background music
        playBackgroundMusic();

        // Set Start button click listener
        btnStart.setOnClickListener(v -> {
            if (areAllPermissionsGranted()) {
                startMainActivity();
            } else {
                requestPermissionsWithRationaleCheck();
            }
        });

        // Load and apply the fade-in animation for logo and text
        Animation fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.transition);
        imageViewS.startAnimation(fadeAnimation);
        textViewS.startAnimation(fadeAnimation);

        // Add animation listener to show Start button after animation completes
        fadeAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // No action needed
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Show the Start button with a fade-in animation
                showStartButton();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // No action needed
            }
        });
    }

    private void showStartButton() {
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(btnStart, "alpha", 0f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(btnStart, "scaleX", 0.7f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(btnStart, "scaleY", 0.7f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(fadeIn, scaleX, scaleY);
        animatorSet.setDuration(800);
        animatorSet.start();
    }

    private void playBackgroundMusic() {
        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.background_music);
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(0.5f, 0.5f);
            mediaPlayer.start();
        } catch (Exception e) {
            // Handle the exception if the music file is not found or cannot be played
            e.printStackTrace();
        }
    }

    private String[] getRequiredPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return new String[]{
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_MEDIA_AUDIO
            };
        } else {
            return new String[]{
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
        }
    }

    private boolean areAllPermissionsGranted() {
        for (String permission : requiredPermissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void requestPermissionsWithRationaleCheck() {
        boolean shouldShowRationale = false;
        for (String permission : requiredPermissions) {
            if (shouldShowRequestPermissionRationale(permission)) {
                shouldShowRationale = true;
                break;
            }
        }

        if (shouldShowRationale) {
            showPermissionExplanationDialog();
        } else {
            requestPermissionLauncher.launch(requiredPermissions);
        }
    }

    private void showPermissionExplanationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Permission required")
                .setMessage("The app needs permissions for audio recording and storage")
                .setPositiveButton("Accept", (dialog, which) -> {
                    requestPermissionLauncher.launch(requiredPermissions);
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    Toast.makeText(this, "Limited Functionality", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setCancelable(false)
                .show();
    }

    private void handleDeniedPermissions() {
        if (!areAllPermissionsGranted()) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission required")
                    .setMessage("Some permits were denied. Please activate them in settings")
                    .setPositiveButton("Open Settings", (dialog, which) -> openAppSettings())
                    .setNegativeButton("Cancel", (dialog, which) -> finish())
                    .setCancelable(false)
                    .show();
        }
    }

    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        // Use a smooth transition between activities
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (areAllPermissionsGranted() && permissionStatus.getBoolean("pendingPermission", false)) {
            startMainActivity();
            permissionStatus.edit().putBoolean("pendingPermission", false).apply();
        }

        // Resume animations and music if they were paused
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            animationDrawable.start();
        }
        if (shineAnimator != null && !shineAnimator.isStarted()) {
            shineAnimator.resume();
        }
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pause shine animation when the activity is paused
        if (shineAnimator != null && shineAnimator.isStarted()) {
            shineAnimator.pause();
        }
        // Pause the music when the activity is paused
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release resources when activity is destroyed
        if (shineAnimator != null) {
            shineAnimator.cancel();
            shineAnimator = null;
        }
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
