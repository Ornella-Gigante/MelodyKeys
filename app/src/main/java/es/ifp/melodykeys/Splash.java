package es.ifp.melodykeys;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    ImageView imageViewS;
    TextView textViewS;

    // Variables for permissions
    private SharedPreferences permissionStatus;
    private String[] requiredPermissions;

    private final ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
                if (areAllPermissionsGranted()) {
                    proceedAfterPermission();
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

        // Initialize requiredPermissions here
        requiredPermissions = getRequiredPermissions();
        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);

        // Load and apply the fade-in animation
        Animation fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.transition);
        imageViewS.startAnimation(fadeAnimation);
        textViewS.startAnimation(fadeAnimation);

        // Add animation listener to proceed after animation completes
        fadeAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // No action needed
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Check permissions after animation ends
                new Handler().postDelayed(() -> {
                    if (areAllPermissionsGranted()) {
                        proceedAfterPermission();
                    } else {
                        requestPermissionsWithRationaleCheck();
                    }
                }, 500); // Small delay after animation for better UX
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // No action needed
            }
        });
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

    private void proceedAfterPermission() {
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
            proceedAfterPermission();
            permissionStatus.edit().putBoolean("pendingPermission", false).apply();
        }
    }
}
