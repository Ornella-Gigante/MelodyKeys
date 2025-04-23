package es.ifp.melodykeys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.Manifest;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Splash extends AppCompatActivity {

    private static final int PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;

    String[] permissionsRequired = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };

    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        // Inicializa SharedPreferences para guardar el estado de los permisos
        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);

        // Comprobación de permisos
        if (ActivityCompat.checkSelfPermission(Splash.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(Splash.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED) {

            // Si se debe mostrar una explicación al usuario
            if (ActivityCompat.shouldShowRequestPermissionRationale(Splash.this, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(Splash.this, permissionsRequired[1])) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Splash.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Storage and Record Audio Permissions");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(Splash.this, permissionsRequired, PERMISSION_CONSTANT);
                    }
                });
                builder.show();

            } else {
                // Solicita los permisos directamente
                ActivityCompat.requestPermissions(Splash.this, permissionsRequired, PERMISSION_CONSTANT);
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        
    }

}