package es.ifp.melodykeys;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

public class Splash extends AppCompatActivity {

    // Variables for SplashActivity

    ImageView imageViewS;
    TextView textViewS;

    //Variables for permissions

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

        // Inizialize spash activity components on screen 

        imageViewS = (ImageView)findViewById(R.id.imageViewSplash);
        textViewS =(TextView) findViewById(R.id.textViewSpash);

        // Initialize requiredPermissions here
        requiredPermissions = getRequiredPermissions();
        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);

        if (areAllPermissionsGranted()) {
            proceedAfterPermission();
        } else {
            requestPermissionsWithRationaleCheck();
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
                .setTitle("Permisos requeridos")
                .setMessage("La aplicación necesita permisos de grabación de audio y acceso a archivos para funcionar correctamente")
                .setPositiveButton("Aceptar", (dialog, which) -> {
                    requestPermissionLauncher.launch(requiredPermissions);
                    dialog.dismiss();
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    Toast.makeText(this, "La funcionalidad estará limitada", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setCancelable(false)
                .show();
    }

    private void handleDeniedPermissions() {
        if (!areAllPermissionsGranted()) {
            new AlertDialog.Builder(this)
                    .setTitle("Permisos requeridos")
                    .setMessage("Algunos permisos esenciales fueron denegados. Por favor, active los permisos en configuración")
                    .setPositiveButton("Abrir configuración", (dialog, which) -> openAppSettings())
                    .setNegativeButton("Cancelar", (dialog, which) -> finish())
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
        startActivity(new Intent(this, MainActivity.class));
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
